package ru.sergeirodionov.shopee.dao;


import ru.sergeirodionov.shopee.model.Cart;
import ru.sergeirodionov.shopee.model.User;

public interface CartDao {

    public void saveCart(Cart cart);

    public Cart updateCart(Integer cartId, Cart cartNew);

    public void deleteCart(Cart cart);

    public Cart getCartById(Integer id);

    public Cart lastUnpaid(User user);

}
