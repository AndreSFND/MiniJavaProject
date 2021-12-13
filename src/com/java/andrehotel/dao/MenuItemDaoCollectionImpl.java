package com.java.andrehotel.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import com.java.andrehotel.model.MenuItem;
import com.java.andrehotel.util.DateUtil;

public class MenuItemDaoCollectionImpl implements MenuItemDao {
	
	private static List<MenuItem> menuItemList;

	public MenuItemDaoCollectionImpl() {
		
		if( menuItemList == null ) {
		
			List<MenuItem> temporaryMenuList = new ArrayList<MenuItem>();
			
			try{
				
				temporaryMenuList.add( new MenuItem( 1, "Sandwich", 99f, true, DateUtil.convertToDate("15/03/2017"), "Main Course", true ) );
				temporaryMenuList.add( new MenuItem( 2, "Burger", 129f, true, DateUtil.convertToDate("23/12/2017"), "Main Course", false ) );
				temporaryMenuList.add( new MenuItem( 3, "Pizza", 149f, true, DateUtil.convertToDate("21/08/2018"), "Main Course", false ) );
				temporaryMenuList.add( new MenuItem( 4, "French Fries", 57f, false, DateUtil.convertToDate("02/07/2017"), "Starters", true ) );
				temporaryMenuList.add( new MenuItem( 5, "Chocolate Brownie", 32f, true, DateUtil.convertToDate("02/11/2022"), "Dessert", true ) );
				
				menuItemList = temporaryMenuList;
				
			} catch(Exception e) {
				
				System.out.println(e);
			
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
