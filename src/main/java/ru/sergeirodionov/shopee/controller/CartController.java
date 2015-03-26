package ru.sergeirodionov.shopee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.sergeirodionov.shopee.service.CartService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@PreAuthorize("isAuthenticated()")
public class CartController {

    @Autowired
    private CartService cartService;

    @RequestMapping(value = {"/addtocart.json"}, method = {RequestMethod.GET}, headers = {"accept=application/json", "accept=application/xml"})
    public
    @ResponseBody
    String addProductToCart(@RequestParam("productId") Integer productId,
                            Principal principal,
                            HttpServletRequest request) {
        return cartService.addProductToCart_JSON(productId, principal, request);
    }

    @RequestMapping(value = {"/cart_details.json"}, method = {RequestMethod.GET}, headers = {"accept=application/json", "accept=application/xml"})
    public
    @ResponseBody
    String cartDetails(@RequestParam("cartId") Integer cartId) {
        return cartService.getCartDetails_JSON(cartId);
    }

    @RequestMapping(value = {"/change_cart.json"}, method = {RequestMethod.GET}, headers = {"accept=application/json", "accept=application/xml"})
    public
    @ResponseBody
    String changeItemCart(@RequestParam("cartId") Integer cartId,
                          @RequestParam("productId") Integer productId,
                          @RequestParam("quantity") Integer newQuantity) {
        return cartService.changeItemCart_JSON(cartId, productId, newQuantity);
    }

    @RequestMapping(value = {"/restore_cart.json"}, method = {RequestMethod.GET}, headers = {"accept=application/json", "accept=application/xml"})
    public
    @ResponseBody
    String restoreCart(Principal principal) {
        return cartService.lastUnpaidCartId_JSON(principal);
    }



}
