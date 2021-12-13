package com.java.andrehotel.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.java.andrehotel.model.Cart;
import com.java.andrehotel.model.MenuItem;

public class CartDaoCollectionImpl implements CartDao {
	
	private static HashMap<Long, Cart> userCarts;

	public CartDaoCollectionImpl() {
	
		if( userCarts == null ) {
			
			userCarts = new HashMap<Long, Cart>();
			
		}
	
	}

	@Override
	public void addCartItem(long userId, long menuItemId) {
		
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
		
	}

}
