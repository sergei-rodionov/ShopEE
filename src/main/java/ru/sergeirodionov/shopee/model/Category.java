package ru.sergeirodionov.shopee.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


//  Категории товара
//  Товар может входить в разные категории

@Entity
@Table(name = "SEE_CATEGORY")
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "CATEGORY_ID")
    private int id;

    @NotNull
    @Column(name = "NAME")
    private String name;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

