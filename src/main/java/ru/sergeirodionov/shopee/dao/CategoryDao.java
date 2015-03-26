package ru.sergeirodionov.shopee.dao;


import ru.sergeirodionov.shopee.model.Category;

import java.util.List;

public interface CategoryDao {

    public void saveCategory(Category category);

    public List<Category> findAllCategories();

    public void deleteCategory(Integer id);

    public Category getCategoryById(Integer id);

}
