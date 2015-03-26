package ru.sergeirodionov.shopee.service;


import ru.sergeirodionov.shopee.model.Product;
import ru.sergeirodionov.shopee.model.Specific;

import java.util.List;
import java.util.Set;

public interface ProductService {

    public void saveProduct(Product product);

   // public void saveSpecific(Specific specific);

    //public void deleteAllProductByCategory(Integer categoryId);

    //public void deleteProduct(Product product);

    public List<Product> findAllProductByCategory(Integer categoryId);

    //public Product updateProduct(Integer productId, Product productNew);

    public Set<Specific> listSpecificsProduct(Integer productId);

    //////////////////

    public String listProductsByCategoryId_JSON(String categoryId);

    public String productEdit_JSON(Integer productId);



}
