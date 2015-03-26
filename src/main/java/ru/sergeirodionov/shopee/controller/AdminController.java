package ru.sergeirodionov.shopee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.sergeirodionov.shopee.model.Product;
import ru.sergeirodionov.shopee.service.CategoryService;
import ru.sergeirodionov.shopee.service.ProductService;
import ru.sergeirodionov.shopee.service.UserService;

import javax.validation.Valid;

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    @RequestMapping(value = {"/admin_user_list.json"}, method = {RequestMethod.GET}, headers = {"accept=application/json", "accept=application/xml"})
    public
    @ResponseBody
    String listUsers() {
        return userService.listAllUsers_JSON();
    }

    @RequestMapping(value = "/admin_user_add.json", method = RequestMethod.POST, headers = {"accept=application/json", "accept=application/xml"})
    @ResponseBody
    public void addNewUser(@RequestParam("newusername") String newUserName,
                             @RequestParam("newpassword") String newUserPassword,
                             @RequestParam("newroles") String newUserRoles) {

        userService.addNewUser_JSON(newUserName, encoder.encode(newUserPassword) , newUserRoles);
    }

    @RequestMapping(value = "/admin_user_delete.json", method = RequestMethod.GET, headers = {"accept=application/json", "accept=application/xml"})
    @ResponseBody
    public void deleteUser(@RequestParam("username") String username) {
        userService.deleteUser_JSON(username);
    }

    @RequestMapping(value = "/admin_user_changepass.json", method = RequestMethod.POST, headers = {"accept=application/json", "accept=application/xml"})
    @ResponseBody
    public void changeUserPassword(@RequestParam("username") String username,
                                   @RequestParam("userpass") String newUserPass) {
        userService.changeUserPassword_JSON(username, encoder.encode(newUserPass));
    }

    @RequestMapping(value = "/admin_user_switchstatus.json", method = RequestMethod.GET, headers = {"accept=application/json", "accept=application/xml"})
    @ResponseBody
    public void switchUserStatus(@RequestParam("username") String username) {
        userService.switchUserStatus_JSON(username);
    }

    @RequestMapping(value = {"/admin_categories_list.json"}, method = {RequestMethod.GET}, headers = {"accept=application/json", "accept=application/xml"})
    public
    @ResponseBody
    String listCategories() {
        return categoryService.listAllCategories_JSON();
    }

    @RequestMapping(value = {"/admin_category_delete.json"}, method = {RequestMethod.GET}, headers = {"accept=application/json", "accept=application/xml"})
    public
    @ResponseBody
    void deleteCategory(@RequestParam("categoryId") Integer categoryId) {
        categoryService.deleteCategory_JSON(categoryId);
    }

    @RequestMapping(value = "/admin_category_add.json", method = RequestMethod.POST, headers = {"accept=application/json", "accept=application/xml"})
    @ResponseBody
    public void addNewUser(@RequestParam("newcategory") String newCategory) {
        categoryService.addCategory_JSON(newCategory);
    }

    @RequestMapping(value = "/admin_category_change.json", method = RequestMethod.POST, headers = {"accept=application/json", "accept=application/xml"})
    @ResponseBody
    public void changeCategoryName(@RequestParam("categoryId") Integer categoryId,
                                   @RequestParam("newName") String newName) {
        categoryService.changeCategoryName_JSON(categoryId, newName);
    }

    @RequestMapping(value = {"/admin_products_list.json"}, method = {RequestMethod.GET}, headers = {"accept=application/json", "accept=application/xml"})
    public
    @ResponseBody
    String listProducts(@RequestParam("categoryId") String categoryId) {
        return productService.listProductsByCategoryId_JSON(categoryId); // "all" or digit
    }

    @RequestMapping(value = {"/admin_products_edit.json"}, method = {RequestMethod.GET}, headers = {"accept=application/json", "accept=application/xml"})
    public
    @ResponseBody
    String listProducts(@RequestParam("productId") Integer productId) {
        return productService.productEdit_JSON(productId);
    }

    @RequestMapping(value = "/admin_product_save.json", method = RequestMethod.POST, headers = {"accept=application/json", "accept=application/xml"})
    @ResponseBody
    public void productEditSave(@Valid @RequestBody Product product) {
        productService.saveProduct(product);
    }

}
