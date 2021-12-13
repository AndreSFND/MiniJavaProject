package com.java.andrehotel.dao;

import java.util.List;

import com.java.andrehotel.model.Cart;
import com.java.andrehotel.model.MenuItem;

public class CartDaoCollectionImplTest {
	
	public static void main(String[] args) {
		
		testAddCartItem();
		testGetAllCartItems();
		testRemoveCartItem();
		
	}
	
	static void testAddCartItem() {
		
		final long itemId = 1;
		final long userId = 1;
		
		CartDao cartDao = new CartDaoCollectionImpl();
		cartDao.addCartItem(userId, itemId);
		
		try {
			
			Cart userCart = cartDao.getAllCartItems(userId);
			System.out.println(userCart);
			
			List<MenuItem> menuItemList = userCart.getMenuItemList();
			for( MenuItem menuItem : menuItemList ) {
				
				if( menuItem.getId() == itemId ) {
					
					System.out.println("Added item is present");
					
					break;
					
				}
				
			}
			
			
		} catch (CartEmptyException e) {
		
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
		
	}
	
	static void testGetAllCartItems() {
		
		final long userId = 1;
		
		CartDao cartDao = new CartDaoCollectionImpl();
		
		try {
			
			Cart userCart = cartDao.getAllCartItems(userId);
			System.out.println(userCart);
			
		} catch (CartEmptyException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
		
	}
	
	static void testRemoveCartItem() {
		
		final long itemId = 1;
		final long userId = 1;
		
		CartDao cartDao = new CartDaoCollectionImpl();
		cartDao.removeCartItem(userId, itemId);
		
		try {
			
			Cart userCart = cartDao.getAllCartItems(userId);
			System.out.println(userCart);
			
		} catch (CartEmptyException e) {
		
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
		
	}

}
