package ru.sergeirodionov.shopee.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


//    Товары на складе

@Entity
@Table(name = "SEE_STORAGE")
public class Storage {

    @Id
    @GeneratedValue
    @Column(name = "STORAGE_ID")
    private int id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @OneToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @Column(name = "QUANTITY")
    private int quantity;

    @Column(name = "PRICE", columnDefinition = "Decimal(10,2)")
    private double price;


    public Storage(Product product, int quantity, double price) {
        this.product = product;
        this.category = product.getCategory();
        this.quantity = quantity;
        this.price = price;
    }

    public Storage() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"product\":" + product.toString() +
                ", \"quantity\":" + quantity +
                ", \"price\":\"" + price +
                "\"}";
    }
}
