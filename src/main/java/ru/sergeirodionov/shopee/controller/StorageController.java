package ru.sergeirodionov.shopee.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sergeirodionov.shopee.model.Category;
import ru.sergeirodionov.shopee.model.Storage;
import ru.sergeirodionov.shopee.service.CategoryService;
import ru.sergeirodionov.shopee.service.StorageService;

import java.io.IOException;
import java.util.List;

@Controller
public class StorageController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private StorageService storageService;

//    @Autowired
//    @Qualifier(value = "userValidator")
//    private UserValidator userValidator;

    @ModelAttribute("categories_list")
    public List<Category> getAllCategories() {
        return categoryService.findAllCategories();
    }

    @RequestMapping(value = {"/categories.json"}, method = {RequestMethod.GET}, headers = {"accept=application/json", "accept=application/xml"})
    public
    @ResponseBody
    List<Category> listCategories(Model model) throws IOException {
        return getAllCategories();
    }



    @RequestMapping(value = {"/products.json"}, method = {RequestMethod.GET}, headers = {"accept=application/json", "accept=application/xml"})
    public
    @ResponseBody
    String listProducts(@RequestParam("category") String categoryId) throws IOException {
        return storageService.listStorageByCategoryId_JSON(categoryId);
    }

    @RequestMapping(value = {"/product_details.json"}, method = {RequestMethod.GET}, headers = {"accept=application/json", "accept=application/xml"})
    public
    @ResponseBody
    String productDetails(@RequestParam("productId") Integer productId) {
        Storage storage = storageService.findProduct(productId);
        return storage.toString();
    }

    @RequestMapping(value = {"/category_name.json"}, method = {RequestMethod.GET}, headers = {"accept=application/json", "accept=application/xml"})
    public
    @ResponseBody
    String listProducts(@RequestParam("categoryId") Integer categoryId) throws IOException {
        return categoryService.getCategoryById_JSON(categoryId);
    }

}
