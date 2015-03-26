package ru.sergeirodionov.shopee.dao;

import ru.sergeirodionov.shopee.model.Product;
import ru.sergeirodionov.shopee.model.Specific;

import java.util.List;
import java.util.Set;

public interface ProductDao {

    public void saveProduct(Product product);

    //public void saveSpecific(Specific specific);

    public void deleteAllProductByCategory(Integer categoryId);

    public void deleteProduct(Product product);

    public List<Product> findAllProductByCategory(Integer categoryId);

    public Product updateProduct(Integer productId, Product productNew);

  //  public Set<Specific> listSpecificsProduct(Integer productId);

    public Product getProductById(Integer productId);


}
