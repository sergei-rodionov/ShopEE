package ru.sergeirodionov.shopee.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//        Товар (продукт)
//        Может находится в нескольких категориях
//        Имеет список разных свойств

@Entity
@Table(name = "SEE_PRODUCT")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID", nullable = false)
    private int id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private Category category;

    //    создаем дополнительную таблицу для линковки свойст/характеристик
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, targetEntity = Specific.class)
    @JoinTable(name = "SEE_PRODUCT_SPECIFIC",
            joinColumns = {@JoinColumn(name = "PRODUCT_ID", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "SPECIFIC_ID", nullable = false)})
    private Set<Specific> specifics = new HashSet<Specific>(0);

    public Product() {
    }

    public Product(String name, Category category) {
        this.name = name;
        this.category = category;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    public Set<Specific> getSpecifics() {
        return specifics;
    }

    public void setSpecifics(Set<Specific> specifics) {
        this.specifics = specifics;
    }

    public String toString() {
        return "{\"id\":" + id + ",\"name\":\"" + name + "\"" + ",\"cat\":\"" + category.getName() +
                "\",\"specifics\":" + specifics.toString() +
                "}";
    }
}
