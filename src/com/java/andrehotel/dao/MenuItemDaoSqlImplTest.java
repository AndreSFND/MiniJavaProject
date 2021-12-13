package com.java.andrehotel.dao;

import java.text.ParseException;
import java.util.List;
import java.util.ListIterator;

import com.java.andrehotel.model.MenuItem;
import com.java.andrehotel.util.DateUtil;

public class MenuItemDaoSqlImplTest {
	
	public static void main(String[] args) {
		
		testGetMenuItemListAdmin();
		testGetMenuItemListCustomer();
		testModifyMenuItem();
		
	}
	
	static void testGetMenuItemListAdmin() {
		
		System.out.println("---Admin item list---");
		
		MenuItemDao menuItemDao = new MenuItemDaoSqlImpl();
		
		List<MenuItem> menuItemList = menuItemDao.getMenuItemListAdmin();
		
		ListIterator<MenuItem> listIterator = menuItemList.listIterator();
		
		while( listIterator.hasNext() ) {
			
			System.out.println( listIterator.next() );
			
		}
		
	}
	
	static void testGetMenuItemListCustomer() {
		
		System.out.println("---Customer item list---");
		
		MenuItemDao menuItemDao = new MenuItemDaoSqlImpl();
		
		List<MenuItem> menuItemList = menuItemDao.getMenuItemListCustomer();
		
		ListIterator<MenuItem> listIterator = menuItemList.listIterator();
		
		while( listIterator.hasNext() ) {
			
			System.out.println( listIterator.next() );
			
		}
		
	}
	
	static void testModifyMenuItem() {
		
		System.out.println("---Modify test---");
		
		MenuItemDao menuItemDao = new MenuItemDaoSqlImpl();
		
		try {
			
			final long itemId = 2;
			final MenuItem menuItem = new MenuItem( itemId, "Pastel", 11.99f, true, DateUtil.convertToDate("12/12/2021"), "Main Course", true );
		
			menuItemDao.modifyMenuItem( menuItem );
			final MenuItem modifiedItem = menuItemDao.getMenuItem( itemId );
			
			System.out.println( modifiedItem );
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
