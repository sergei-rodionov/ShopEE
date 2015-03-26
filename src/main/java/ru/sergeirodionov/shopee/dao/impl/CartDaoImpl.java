package ru.sergeirodionov.shopee.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ru.sergeirodionov.shopee.dao.AbstractDao;
import ru.sergeirodionov.shopee.dao.CartDao;
import ru.sergeirodionov.shopee.model.Cart;
import ru.sergeirodionov.shopee.model.User;

import java.util.Date;

@Repository("orderDao")
public class CartDaoImpl extends AbstractDao implements CartDao {

    @Override
    public void saveCart(Cart cart) {
        persist(cart);
    }

    @Override
    public Cart updateCart(Integer cartId, Cart cartNew) {
        Session session = getSession();
        Cart cart = (Cart) session.load(Cart.class, cartId);
        cart.setComment(cartNew.getComment());
        cart.setCartDate(cartNew.getCartDate());
        cart.setCartItems(cart.getCartItems());
        cart.setSum(cartNew.getSum());
        cart.setQuantity(cartNew.getQuantity());
        session.flush();
        return cart;
    }

    @Override
    public void deleteCart(Cart cart) {
        delete(cart);
    }

    @Override
    public Cart getCartById(Integer id) {
        Criteria criteria = getSession().createCriteria(Cart.class);
        Criterion idx = Restrictions.idEq(id);
        criteria.add(idx);
        // not use for get Set<CartItem> items!!!
        // criteria.setMaxResults(1);
        return (Cart) criteria.uniqueResult();
    }

    @Override
    public Cart lastUnpaid(User user) {
        Query query = getSession().createQuery("from Cart where user = :user and paid = false");
        query.setParameter("user",user);
        Cart cart = (Cart) query.uniqueResult();
        if (cart==null) {
            Date dateCart = new Date();
            cart = new Cart(user, dateCart, "Cart: " + dateCart.toString() + " - " + user.getUsername(),false);
            saveCart(cart);
        }
        return cart;
    }
}
