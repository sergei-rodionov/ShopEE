package ru.sergeirodionov.shopee.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ru.sergeirodionov.shopee.dao.AbstractDao;
import ru.sergeirodionov.shopee.dao.CategoryDao;
import ru.sergeirodionov.shopee.model.Category;

import java.util.List;

@Repository("categoryDao")
public class CategoryDaoImpl extends AbstractDao implements CategoryDao {

    public void saveCategory(Category category) {
        persist(category);
    }

    @SuppressWarnings("unchecked")
    public List<Category> findAllCategories() {
        Criteria criteria = getSession().createCriteria(Category.class);
        return (List<Category>) criteria.list();
    }


    public void deleteCategory(Integer id) {
        Query query = getSession().createQuery("delete Category where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public Category getCategoryById(Integer id) {
        Criteria criteria = getSession().createCriteria(Category.class);
        Criterion idx = Restrictions.idEq(id);
        criteria.add(idx);
        criteria.setMaxResults(1);

        return (Category) criteria.uniqueResult();
    }
}
