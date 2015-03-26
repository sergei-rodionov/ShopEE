package ru.sergeirodionov.shopee.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sergeirodionov.shopee.dao.CategoryDao;
import ru.sergeirodionov.shopee.model.Category;
import ru.sergeirodionov.shopee.service.CategoryService;

import java.util.List;

@Service("categoryService")
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao dao;

    public void saveCategory(Category category) {
        dao.saveCategory(category);
    }

    public List<Category> findAllCategories() {
        return dao.findAllCategories();
    }

    @Override
    public String getCategoryById_JSON(Integer id) {
        return "{\"name\":\""+dao.getCategoryById(id).getName()+"\"}";
    }

    @Override
    public String listAllCategories_JSON() {
        StringBuilder sb = new StringBuilder();
        boolean isNext=false;
        for(Category category : dao.findAllCategories()) {
            if (isNext) {
                sb.append(",");
            }
            isNext=true;
            sb.append("{\"categoryId\":"+category.getId()+",\"categoryName\":\""+category.getName()+"\"}");
        }
        return "["+sb.toString()+"]";
    }

    @Override
    public void deleteCategory_JSON(Integer categoryId) {

        dao.deleteCategory(categoryId);
    }

    @Override
    public void addCategory_JSON(String name) {
        Category category = new Category(name);
        dao.saveCategory(category);
    }

    @Override
    public void changeCategoryName_JSON(Integer categoryId, String newName) {
        Category category = dao.getCategoryById(categoryId);
        category.setName(newName);
        dao.saveCategory(category);
    }
}
