package ru.sergeirodionov.shopee.service;


import ru.sergeirodionov.shopee.model.Product;
import ru.sergeirodionov.shopee.model.Storage;

import java.util.List;

public interface StorageService {

    public void saveStorage(Storage storage);

    public Storage addProductToStorage(Product product, Integer quantity, Double price);

    public Storage changeProductInStorage(Storage storage, Product product, Integer quantity, Double price);

    public Storage findProduct(Integer productId);

    public List<Storage> findAllProductsByCategory(Integer categoryId);

    public void deleteStorage(Storage storage);

    public String listStorageByCategoryId_JSON(String categoryId);

}
