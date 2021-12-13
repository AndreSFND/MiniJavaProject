package com.java.andrehotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.java.andrehotel.database.ConnectionHandler;
import com.java.andrehotel.model.Cart;
import com.java.andrehotel.model.MenuItem;

public class CartDaoSqlImpl implements CartDao {
	
	private static HashMap<Long, Cart> userCarts;

	public CartDaoSqlImpl() {
	
		if( userCarts == null ) {
			
			userCarts = new HashMap<Long, Cart>();

			try {
				
				String sqlStatement = "SELECT * FROM `cart_menu_item` ORDER BY `cart_id` ASC";
				ConnectionHandler connectionHandler = new ConnectionHandler();
				
				ResultSet resultSet = connectionHandler.queryStatement(sqlStatement);
				
				while(resultSet.next())
				{
				
					long userId = resultSet.getLong("cart_id");
					long itemId = resultSet.getLong("menu_item_id");
					
					addCartItemCollection(userId, itemId);
					
				}
				
				connectionHandler.closeConnection();
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
	
	}
	
	private void addCartItemCollection(long userId, long menuItemId) {
		
		MenuItemDao menuItemDao = new MenuItemDaoCollectionImpl();
		MenuItem menuItem = menuItemDao.getMenuItem(menuItemId);
		
		if( userCarts.containsKey(userId) ) {
			
			Cart userCart = userCarts.get(userId);
			List<MenuItem> menuItemList = userCart.getMenuItemList();
			
			menuItemList.add(menuItem);
			
		} else {
			
			List<MenuItem> menuItemList = new ArrayList<MenuItem>();
			menuItemList.add(menuItem);
			
			Cart userCart = new Cart(menuItemList, 0f);
			
			userCarts.put(userId, userCart);
			
		}
		
	}

	@Override
	public void addCartItem(long userId, long menuItemId) {
		
		addCartItemCollection(userId, menuItemId);
		
		ConnectionHandler connectionHandler = new ConnectionHandler();
		Connection con = connectionHandler.connection;
		
		String cartSqlStatement = "INSERT IGNORE INTO `cart` (`id`) VALUES (?)";
		String cartItemSqlStatement = "INSERT INTO `cart_menu_item` (`cart_id`, `menu_item_id`) VALUES (?, ?)";
		
		try {
			
			PreparedStatement cartPreparedStatement = con.prepareStatement(cartSqlStatement);
			cartPreparedStatement.setLong(1, userId);
			
			PreparedStatement cartItemPreparedStatement = con.prepareStatement(cartItemSqlStatement);
			cartItemPreparedStatement.setLong(1, userId);
			cartItemPreparedStatement.setLong(2, menuItemId);
			
			cartPreparedStatement.execute();
			cartItemPreparedStatement.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		connectionHandler.closeConnection();
		
	}

	@Override
	public Cart getAllCartItems(long userId) throws CartEmptyException {
		
		if( userCarts.containsKey(userId) ) {
			
			Cart userCart = userCarts.get(userId);
			List<MenuItem> menuItemList = userCart.getMenuItemList();
			
			if( !menuItemList.isEmpty() ) {
				
				double priceSum = 0f;
				
				for( MenuItem menuItem : menuItemList ) {
				
					priceSum += menuItem.getPrice();
					
				}
				
				userCart.setTotal(priceSum);
				return userCart;
				
			} else {
				
				throw new CartEmptyException();
				
			}
			
		} else {
			
			throw new CartEmptyException();
			
		}
		
	}

	@Override
	public void removeCartItem(long userId, long menuItemId) {
		
		if( userCarts.containsKey(userId) ) {
			
			Cart userCart = userCarts.get(userId);
			List<MenuItem> menuItemList = userCart.getMenuItemList();
			
			menuItemList.removeIf(e -> e.getId() == menuItemId);
			
		}
		
		ConnectionHandler connectionHandler = new ConnectionHandler();
		Connection con = connectionHandler.connection;
		
		String sqlStatement = "DELETE FROM `cart_menu_item` WHERE `cart_id` = ? AND `menu_item_id` = ?";
		
		try {
			
			PreparedStatement preparedStatement = con.prepareStatement(sqlStatement);
			preparedStatement.setLong(1, userId);
			preparedStatement.setLong(2, menuItemId);
			
			preparedStatement.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		connectionHandler.closeConnection();
		
	}

}
