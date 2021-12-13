package com.java.andrehotel;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

import com.java.andrehotel.dao.CartDao;
import com.java.andrehotel.dao.CartDaoCollectionImpl;
import com.java.andrehotel.dao.CartDaoSqlImpl;
import com.java.andrehotel.dao.CartEmptyException;
import com.java.andrehotel.dao.MenuItemDao;
import com.java.andrehotel.dao.MenuItemDaoCollectionImpl;
import com.java.andrehotel.dao.MenuItemDaoSqlImpl;
import com.java.andrehotel.model.Cart;
import com.java.andrehotel.model.MenuItem;
import com.java.andrehotel.util.DateUtil;

public class Main {
	
	static long userId = 1;
	final static Scanner scanner = new Scanner(System.in);
	
//	final static MenuItemDao menuItemDao = new MenuItemDaoSqlImpl();
//	final static CartDao cartDao = new CartDaoSqlImpl();
	final static MenuItemDao menuItemDao = new MenuItemDaoCollectionImpl();
	final static CartDao cartDao = new CartDaoCollectionImpl();

	public static void main(String[] args) {
		
		while( true ) {
			
			userInterface();
			
		}

	}
	
	static void userInterface() {
		
		int menuType = mainMenu();
		
		switch( menuType ) {
		
			case 1:
				adminMenu();
				break;
				
			case 2:
				
				System.out.println("Enter your identifier number:");
				userId = scanner.nextLong();
				
				while( customerMenu() != -1 ) { ; }
				
				break;
				
			case 3:
				System.exit(0);
				break;
		
		}
		
	}
	
	static int mainMenu() {
		
		System.out.println();
		System.out.println("Choose an option:");
		System.out.println("1 - Administrator menu");
		System.out.println("2 - Customer menu");
		System.out.println("3 - Exit");
		
		return scanner.nextInt();
		
	}
	
	static void adminMenu() {
		
		System.out.println("\nEnter the item ID to edit it, or 0 to return");
		System.out.println();
		printMenu(menuItemDao.getMenuItemListAdmin());
		
		int option = scanner.nextInt();
		
		if( option == 0 ) return;
		else editItemMenu(option);
		
	}
	
	static void editItemMenu(int itemId) {
		
		System.out.println("Enter the data for the new item following the format:");
		System.out.println("\tName, Price, Active, Date of launch, Category, Free delivery");
		System.out.println("\tExample: Sandwich,99.0,true,12/12/2021,Main Course,true");

		String data;
		while( (data = scanner.nextLine()).equals("") ) {;}

		MenuItem menuItem = parseMenuItem(itemId, data);
	
		menuItemDao.modifyMenuItem( menuItem );
		final MenuItem modifiedItem = menuItemDao.getMenuItem( itemId );
		
		System.out.println();
		System.out.println("Item edited successfuly!");
		System.out.println( modifiedItem );
	
	}
	
	static MenuItem parseMenuItem(long itemId, String input) {
		
		String[] splittedData = input.split(",");
		MenuItem menuItem = null;
		
		try {
		
			String name = splittedData[0];
			float price = Float.parseFloat(splittedData[1]);
			boolean active = Boolean.parseBoolean(splittedData[2]);
			Date dateOfLaunch;
			dateOfLaunch = DateUtil.convertToDate(splittedData[3]);
			String category = splittedData[4];
			boolean freeDelivery = Boolean.parseBoolean(splittedData[5]);
			
			menuItem = new MenuItem( itemId, name, price, active, dateOfLaunch, category, freeDelivery );
		
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	
		return menuItem;
		
	}
	
	static int customerMenu() {
		
		System.out.println("\nMENU");
		printMenu(menuItemDao.getMenuItemListCustomer());
		
		System.out.println("\nMY CART");
		printMyCart();
		
		System.out.println("\nType 'Remove ID' to remove, 'Add ID' to add an item or 0 to return");
		
		String data;
		while( (data = scanner.nextLine()).equals("") ) {;}
		
		if( data.equals("0") ) return -1;
		else customerAction(data);
		
		return 0;
		
	}
	
	static void printMenu(List<MenuItem> menuItemList) {
		
		ListIterator<MenuItem> menuIterator = menuItemList.listIterator();
		
		while( menuIterator.hasNext() ) System.out.println( menuIterator.next() );
		
	}
	
	static void printMyCart() {
		
		try {
			
			Cart userCart = cartDao.getAllCartItems(userId);
			printMenu(userCart.getMenuItemList());
			System.out.println("Total: " + userCart.getTotal());
			
		} catch (CartEmptyException e) {
			
			System.out.println("Your cart is still empty! D:");
			
		}
	}
	
	static void customerAction(String input) {
		
		String[] splittedData = input.split(" ");
		
		String command = splittedData[0];
		long itemId = Long.parseLong(splittedData[1]);
		
		switch( command ) {
		
			case "Add":
				cartDao.addCartItem(userId, itemId);
				break;
				
			case "Remove":
				cartDao.removeCartItem(userId, itemId);
				break;
		
		}
		
	}

}
