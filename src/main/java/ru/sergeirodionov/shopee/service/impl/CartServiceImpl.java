package ru.sergeirodionov.shopee.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sergeirodionov.shopee.dao.CartDao;
import ru.sergeirodionov.shopee.dao.StorageDao;
import ru.sergeirodionov.shopee.dao.UserDao;
import ru.sergeirodionov.shopee.model.Cart;
import ru.sergeirodionov.shopee.model.CartItem;
import ru.sergeirodionov.shopee.model.Storage;
import ru.sergeirodionov.shopee.service.CartService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Iterator;
import java.util.Set;

@Service("orderService")
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
    private CartDao cartDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private StorageDao storageDao;


    @Override
    public String addProductToCart_JSON(Integer productId, Principal principal, HttpServletRequest request) {
        if (principal != null) {
            int quantityItems = 0;
            // create cart
            int cartId = 0;
//            if (request.getParameter("cartId") == null || request.getParameter("cartId").equals("")) {
//
//                Date dateOrder = new Date();
//                Cart cart = new Cart(userDao.findByUserName(principal.getName()), dateOrder, "Cart: " + dateOrder.toString() + " - " + principal.getName(),false);
//                Storage productInStorage = storageDao.findProduct(productId);
//                CartItem item = new CartItem(productInStorage.getProduct(), 1, productInStorage.getPrice());
//                Set<CartItem> items = new HashSet();
//                items.add(item);
//                cart.setCartItems(items);
//                cart.setQuantity(1);
//                cart.setSum(1 * productInStorage.getPrice());
//                cartDao.saveCart(cart);
//                cartId = cart.getId();
//                quantityItems = 1;
//            } else {
                // add product to cart
                cartId = Integer.parseInt(request.getParameter("cartId"));
                Cart cart = cartDao.getCartById(cartId);
                Storage productInStorage = storageDao.findProduct(productId);
                CartItem item = new CartItem(productInStorage.getProduct(), 1, productInStorage.getPrice());
                Set<CartItem> cartItems = cart.getCartItems();
                // check dublicate
                boolean isAddedItem = false;
                for (CartItem i : cartItems) {
                    if (i.getProduct()==item.getProduct()) {
                        isAddedItem = true;
                        i.setQuantity(i.getQuantity() + item.getQuantity());
                        i.setPrice((i.getPrice() + item.getPrice()) / 2);
                        break;
                    }
                }
                if (!isAddedItem) {
                    cartItems.add(item);
                }
                cart.setCartItems(cartItems);
                quantityItems = cart.getQuantityItems();
                cart.setQuantity( quantityItems );
                cart.setSum( cart.getSumItems() );
                cartDao.saveCart(cart);
//            }
            return "{\"user\":\"" + principal.getName() + "\",\"cartId\":" + cartId + ", \"cartQty\":"+quantityItems+"}";
        }
        // need login for create order
        return "{\"user\":\"Need login!\"}";
    }


    @Override
    public String getCartDetails_JSON(Integer cartId) {
        Cart cart = cartDao.getCartById(cartId);
        StringBuilder result = new StringBuilder();
        result.append("{\"cartId\":"+ cart.getId()+
                ", \"cartDate\":\""+ cart.getCartDate().toString()+
                "\", \"cartComment\":\""+ cart.getComment()+
                "\", \"cartQty\":"+ cart.getQuantity()+
                ", \"cartSum\":\""+ cart.getSum() +
                "\", \"cartItems\": [");
        boolean isNext=false;
        for(CartItem item : cart.getCartItems()) {
            if (isNext) { result.append(","); }
            isNext=true;
            result.append("{\"itemProdId\":"+item.getProduct().getId()+" ,\"itemProd\":\""+item.getProduct().getName()+"\", \"itemQty\":"+item.getQuantity()+", \"itemPrice\":\""+item.getPrice()+"\"}");
        }
        result.append("]}");
        return result.toString();
    }

    @Override
    public String changeItemCart_JSON(Integer cartId, Integer productId, Integer newQuantity) {
        Cart cart = cartDao.getCartById(cartId);
        Set<CartItem> items = cart.getCartItems();
        Iterator<CartItem> iter = items.iterator();
        while(iter.hasNext()) {
            CartItem cartItem = iter.next();
            if (cartItem.getProduct().getId()==productId && newQuantity==0) {
                iter.remove();
            } else if (cartItem.getProduct().getId()==productId){
                cartItem.setQuantity(newQuantity);
            }
        }
        cart.setCartItems(items);
        cart.setQuantity(cart.getQuantityItems());
        cart.setSum(cart.getSumItems());

        cartDao.updateCart(cartId, cart);

        return getCartDetails_JSON(cartId);
    }


    @Override
    public String lastUnpaidCartId_JSON(Principal principal) {
        Cart cart = cartDao.lastUnpaid(userDao.findByUserName(principal.getName()));
        return "{\"cartId\":"+cart.getId()+", \"cartQty\":"+cart.getQuantity()+"}";
    }
}
