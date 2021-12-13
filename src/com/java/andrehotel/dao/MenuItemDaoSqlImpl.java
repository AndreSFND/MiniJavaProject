package com.java.andrehotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import com.java.andrehotel.database.ConnectionHandler;
import com.java.andrehotel.model.MenuItem;

public class MenuItemDaoSqlImpl implements MenuItemDao {
	
	private static List<MenuItem> menuItemList;

	public MenuItemDaoSqlImpl() {
		
		if( menuItemList == null ) {
		
			List<MenuItem> temporaryMenuList = new ArrayList<MenuItem>();
			
			try {
				
				String sqlStatement = "SELECT * FROM `menu_item`";
				ConnectionHandler connectionHandler = new ConnectionHandler();
				
				ResultSet resultSet = connectionHandler.queryStatement(sqlStatement);
				
				while(resultSet.next())
				{
				
					long id = resultSet.getLong("id");
					String name = resultSet.getString("name");
					float price = resultSet.getFloat("price");
					boolean active = resultSet.getBoolean("active");
					Date dateOfLaunch = resultSet.getDate("dateOfLaunch");
					String category = resultSet.getString("category");
					boolean freeDelivery = resultSet.getBoolean("freeDelivery");
					
				    temporaryMenuList.add( new MenuItem( id, name, price, active, dateOfLaunch, category, freeDelivery ) );
				
				}
				
				connectionHandler.closeConnection();

				menuItemList = temporaryMenuList;
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		}
			
	}

	@Override
	public List<MenuItem> getMenuItemListAdmin() {
		
		return menuItemList;
	
	}

	@Override
	public List<MenuItem> getMenuItemListCustomer() {
		
		List<MenuItem> menuItemListCustomer = new ArrayList<MenuItem>();
		
		ListIterator<MenuItem> listIterator = menuItemList.listIterator();
		
		while( listIterator.hasNext() ) {
			
			MenuItem menuItem = listIterator.next();
			
			if( menuItem.isActive() && menuItem.getDateOfLaunch().before(new Date()) ) {
				
				menuItemListCustomer.add(menuItem);
				
			}
			
		}
		
		return menuItemListCustomer;
		
	}

	@Override
	public void modifyMenuItem(MenuItem menuItem) {
		
		ConnectionHandler connectionHandler = new ConnectionHandler();
		Connection con = connectionHandler.connection;
		
		String sqlStatement = "UPDATE `menu_item` SET "
				+ "`name` = ?, "
				+ "`price` = ?, "
				+ "`active` = ?, "
				+ "`dateOfLaunch` = ?, "
				+ "`category` = ?, "
				+ "`freeDelivery` = ? "
				+ "WHERE `id` = ?";
		
		PreparedStatement preparedStatement;
		try {
			
			preparedStatement = con.prepareStatement(sqlStatement);
			preparedStatement.setString(1, menuItem.getName());
			preparedStatement.setFloat(2, menuItem.getPrice());
			preparedStatement.setBoolean(3, menuItem.isActive());
			preparedStatement.setDate(4, new java.sql.Date( menuItem.getDateOfLaunch().getTime() ) );
			preparedStatement.setString(5, menuItem.getCategory());
			preparedStatement.setBoolean(6, menuItem.isFreeDelivery());
			preparedStatement.setLong(7, menuItem.getId());
			preparedStatement.executeUpdate();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		connectionHandler.closeConnection();
		
		int itemIndex = 0;
		
		for( MenuItem currentItem : menuItemList ) {
			
			if( currentItem.getId() == menuItem.getId() ) {
				
				menuItemList.set(itemIndex, menuItem);
				return;
				
			}
			
			itemIndex++;
			
		}
		
	}

	@Override
	public MenuItem getMenuItem(long menuItemId) {
		
		for( MenuItem currentItem : menuItemList ) {
			
			if( currentItem.getId() == menuItemId ) {
				
				return currentItem;
				
			}
			
		}
		
		return null;
	
	}
	
	

}
