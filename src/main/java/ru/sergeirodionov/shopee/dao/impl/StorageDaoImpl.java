package ru.sergeirodionov.shopee.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.sergeirodionov.shopee.dao.AbstractDao;
import ru.sergeirodionov.shopee.dao.StorageDao;
import ru.sergeirodionov.shopee.model.Product;
import ru.sergeirodionov.shopee.model.Storage;

import java.util.List;

@Repository("storageDao")
public class StorageDaoImpl extends AbstractDao implements StorageDao {

    @Override
    public void saveStorage(Storage storage) {
        persist(storage);
    }

    @Override
    public Storage addProductToStorage(Product product, Integer quantity, Double price) {
        Storage storage = new Storage(product, quantity, price);
        persist(storage);
        return storage;
    }

    @Override
    public Storage changeProductInStorage(Storage storage, Product product, Integer quantity, Double price) {
        Session session = getSession();
        Storage stor = (Storage) session.load(Storage.class, storage.getId());
        stor.setProduct(product);
        stor.setCategory(product.getCategory());
        stor.setQuantity(quantity);
        stor.setPrice(price);
        session.flush();
        return stor;
    }

    @Override
    public Storage findProduct(Integer productId) {
        Query query = getSession().createQuery("from Storage where product.id = :id");
        query.setParameter("id", productId);
        return (Storage) query.uniqueResult();
    }

    @Override
    public List<Storage> findAllProductsByCategory(Integer categoryId) {
        Query query = getSession().createQuery("from Storage where category.id = :id");
        query.setParameter("id", categoryId);
        return (List<Storage>) query.list();
    }

    @Override
    public void deleteStorage(Storage storage) {
        delete(storage);
    }
}
