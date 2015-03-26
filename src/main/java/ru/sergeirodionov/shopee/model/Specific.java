package ru.sergeirodionov.shopee.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

//    Класс хранит свойства/характеристики товара/продукта

@Entity
@Table(name = "SEE_SPECIFIC")
public class Specific {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SPECIFIC_ID", nullable = false)
    private int id;

    @NotNull
    @Column(name = "NAME", nullable = false)
    private String specificName;

    @Column(name = "VALUE", length = 3000)
    private String specificValue;

    public Specific() {
    }

    public Specific(String specificName, String specificValue) {
        this.specificName = specificName;
        this.specificValue = specificValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpecificName() {
        return specificName;
    }

    public void setSpecificName(String specificName) {
        this.specificName = specificName;
    }

    public String getSpecificValue() {
        return specificValue;
    }

    public void setSpecificValue(String specificValue) {
        this.specificValue = specificValue;
    }

    public String toString() {
        return "{\"id\":" + id + ",\"name\":\"" + specificName + "\",\"value\":\"" + specificValue + "\"}";
    }
}
