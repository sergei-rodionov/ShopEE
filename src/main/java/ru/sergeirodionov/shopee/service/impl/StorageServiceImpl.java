package ru.sergeirodionov.shopee.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sergeirodionov.shopee.dao.CategoryDao;
import ru.sergeirodionov.shopee.dao.StorageDao;
import ru.sergeirodionov.shopee.model.Category;
import ru.sergeirodionov.shopee.model.Product;
import ru.sergeirodionov.shopee.model.Storage;
import ru.sergeirodionov.shopee.service.StorageService;

import java.util.ArrayList;
import java.util.List;

@Service("storageService")
@Transactional
public class StorageServiceImpl implements StorageService {

    @Autowired
    private StorageDao storageDao;
    @Autowired
    private CategoryDao categoryDao;

    @Override
    public void saveStorage(Storage storage) {
        storageDao.saveStorage(storage);
    }

    @Override
    public Storage addProductToStorage(Product product, Integer quantity, Double price) {
        return storageDao.addProductToStorage(product, quantity, price);
    }

    @Override
    public Storage changeProductInStorage(Storage storage, Product product, Integer quantity, Double price) {
        return storageDao.changeProductInStorage(storage, product, quantity, price);
    }

    @Override
    public Storage findProduct(Integer productId) {
        return storageDao.findProduct(productId);
    }

    @Override
    public List<Storage> findAllProductsByCategory(Integer categoryId) {
        return storageDao.findAllProductsByCategory(categoryId);
    }

    @Override
    public void deleteStorage(Storage storage) {
        storageDao.deleteStorage(storage);
    }

    @Override
    public String listStorageByCategoryId_JSON(String categoryId) {
        List<String> list = new ArrayList<String>();
        if (categoryId.equals("all")) {
            for (Category c :  categoryDao.findAllCategories()) {
                List<Storage> storageList = storageDao.findAllProductsByCategory(c.getId());
                for (Storage stor : storageList) {
                    String s = "{\"id\":" + stor.getProduct().getId() + ",\"name\":\"" + stor.getProduct().getName() + "\", \"price\":\"" + stor.getPrice() + "\"}";
                    list.add(s);
                }
            }

        } else {
            try {
                List<Storage> storageList = storageDao.findAllProductsByCategory(Integer.parseInt(categoryId));
                for (Storage stor : storageList) {
                    String s = "{\"id\":" + stor.getProduct().getId() + ",\"name\":\"" + stor.getProduct().getName() + "\", \"price\":\"" + stor.getPrice()
                            + "\",\"category\":\"" + stor.getCategory().getName() + "\"}";
                    list.add(s);
                }
            } catch (NumberFormatException e) {
            }   //not number
        }
        return list.toString();
    }
}
