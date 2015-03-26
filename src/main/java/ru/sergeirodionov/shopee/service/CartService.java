package ru.sergeirodionov.shopee.service;


import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

public interface CartService {

    public String addProductToCart_JSON(Integer productId, Principal principal, HttpServletRequest request);

    public String getCartDetails_JSON(Integer cartId);

    public String changeItemCart_JSON(Integer cartId, Integer productId, Integer newQuantity);

    public String lastUnpaidCartId_JSON(Principal principal);



}
