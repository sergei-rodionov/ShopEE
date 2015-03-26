package ru.sergeirodionov.shopee.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "SEE_CARTITEM")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CARTITEM_ID", nullable = false)
    private int id;

    @NotNull
    @OneToOne
    private Product product;

    @NotNull
    @Column(name = "QUANTITY", nullable = false)
    private int quantity;

    @NotNull
    @Column(name = "PRICE", nullable = false)
    private double price;

    public CartItem() {
    }

    public CartItem(Product product, int quantity, double price) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
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
}
