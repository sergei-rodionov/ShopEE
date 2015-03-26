package ru.sergeirodionov.shopee.service;

import ru.sergeirodionov.shopee.model.Category;

import java.util.List;

public interface CategoryService {
    public void saveCategory(Category category);

    public List<Category> findAllCategories();

    public String listAllCategories_JSON();

    public void deleteCategory_JSON(Integer categoryId);

    public void addCategory_JSON(String name);

    public void changeCategoryName_JSON(Integer categoryId, String newName);

    public String getCategoryById_JSON(Integer id);
}
