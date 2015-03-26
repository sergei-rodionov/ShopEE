package ru.sergeirodionov.shopee.dao.impl;


import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ru.sergeirodionov.shopee.dao.AbstractDao;
import ru.sergeirodionov.shopee.dao.ProductDao;
import ru.sergeirodionov.shopee.model.Product;
import ru.sergeirodionov.shopee.model.Specific;

import java.util.List;
import java.util.Set;


@Repository("productDao")
public class ProductDaoImpl extends AbstractDao implements ProductDao {
    @Override
    public void saveProduct(Product product) {
        persist(product);
    }

    @Override
    public void deleteAllProductByCategory(Integer categoryId) {
        Query query = getSession().createQuery("delete from Product where category.id = :id");
        query.setParameter("id", categoryId);
        query.executeUpdate();
    }

    @Override
    public void deleteProduct(Product product) {
        delete(product);
    }

    @Override
    public List<Product> findAllProductByCategory(Integer categoryId) {
        Query query = getSession().createQuery("from Product where category.id = :id");
        query.setParameter("id", categoryId);
        return (List<Product>) query.list();
    }

//    @Override
//    public Set<Specific> listSpecificsProduct(Integer productId) {
//        Query query = getSession().createQuery("from Product where id = :id");
//        query.setParameter("id", productId);
//        Product product = (Product) query.uniqueResult();
//        return product.getSpecifics();
//    }

    @Override
    public Product updateProduct(Integer productId, Product productNew) {
        Session session = getSession();
        Product product = (Product) session.load(Product.class, new Long(productId));
        product.setName(productNew.getName());
        product.setCategory(productNew.getCategory());
        product.setSpecifics(productNew.getSpecifics());
        session.flush();
        return product;
    }

    @Override
    public Product getProductById(Integer productId) {
        Criteria criteria = getSession().createCriteria(Product.class);
        Criterion idx = Restrictions.idEq(productId);
        criteria.add(idx);
        // not use for Set<Specific>
        // criteria.setMaxResults(1);

        return (Product) criteria.uniqueResult();
    }
}
