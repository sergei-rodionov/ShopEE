package ru.sergeirodionov.shopee.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sergeirodionov.shopee.dao.CategoryDao;
import ru.sergeirodionov.shopee.dao.ProductDao;
import ru.sergeirodionov.shopee.model.Category;
import ru.sergeirodionov.shopee.model.Product;
import ru.sergeirodionov.shopee.model.Specific;
import ru.sergeirodionov.shopee.service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service("productService")
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private CategoryDao categoryDao;

    @Override
    public void saveProduct(Product product) {
        productDao.saveProduct(product);
    }

//    @Override
//    public void saveSpecific(Specific specific) {
//        productDao.saveSpecific(specific);
//    }

//    @Override
//    public void deleteAllProductByCategory(Integer categoryId) {
//        productDao.deleteAllProductByCategory(categoryId);
//    }
//
//    @Override
//    public void deleteProduct(Product product) {
//        productDao.deleteProduct(product);
//    }

    @Override
    public List<Product> findAllProductByCategory(Integer categoryId) {
        return productDao.findAllProductByCategory(categoryId);
    }

//    @Override
//    public Product updateProduct(Integer productId, Product productNew) {
//        return productDao.updateProduct(productId, productNew);
//    }

    @Override
    public Set<Specific> listSpecificsProduct(Integer productId) {
        return productDao.getProductById(productId).getSpecifics();
    }

    ///////////////////////////////////


    @Override
    public String listProductsByCategoryId_JSON(String categoryId) {
        List<String> list = new ArrayList<String>();
        if (categoryId.equals("all")) {
            for (Category c :  categoryDao.findAllCategories()) {
                List<Product> productList = productDao.findAllProductByCategory(c.getId());
                for (Product product : productList) {
                    String s = "{\"id\":" + product.getId() + ",\"name\":\"" + product.getName() + "\", \"category\":\"" + product.getCategory().getName() + "\"}";
                    list.add(s);
                }
            }

        } else {
            try {
                List<Product> productList = productDao.findAllProductByCategory(Integer.parseInt(categoryId));
                for (Product product : productList) {
                    String s = "{\"id\":" + product.getId() + ",\"name\":\"" + product.getName() +
                            "\", \"category\":\"" + product.getCategory().getName() + "\"}";
                    list.add(s);
                }
            } catch (NumberFormatException e) {
            }   //not number
        }
        return list.toString();
    }

    @Override
    public String productEdit_JSON(Integer productId) {
        Product product = productDao.getProductById(productId);
        StringBuilder sb = new StringBuilder();
        boolean isNext=false;
        for (Specific specific : product.getSpecifics()) {
            if (isNext) {
                sb.append(",");
            }
            isNext=true;
            sb.append("{ \"specificId\":"+specific.getId()+", \"specificName\":\""+specific.getSpecificName()+
                    "\", \"specificValue\":\""+specific.getSpecificValue()+"\"}");
        }

        return "{ \"id\":"+product.getId()+", \"name\":\""+product.getName()+"\", \"categoryId\":"+
                product.getCategory().getId()+", \"specifics\":["+sb.toString()+"]}";
    }


}
