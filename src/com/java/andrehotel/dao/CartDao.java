package com.java.andrehotel.dao;

import com.java.andrehotel.model.Cart;

public interface CartDao {

	public void addCartItem(long userId, long menuItemId);
	public Cart getAllCartItems(long userId) throws CartEmptyException;
	public void removeCartItem(long userId, long menuItemId);
	
}
