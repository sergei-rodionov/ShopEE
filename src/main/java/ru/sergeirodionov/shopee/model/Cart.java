package ru.sergeirodionov.shopee.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "SEE_CART")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CART_ID", nullable = false)
    private int id;

    @NotNull
    @ManyToOne
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CART_DATE")
    private Date cartDate;

    @Column(name = "CART_COMMENT")
    private String comment;

    @Column(name = "QUANTITY")
    private int quantity;

    @Column(name = "PAID")
    private boolean paid;

    @Column(name = "SUM", columnDefinition = "Decimal(10,2)")
    private double sum;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER,  targetEntity = CartItem.class)
    @JoinTable(name = "SEE_CART_CARTITEM",
            joinColumns = {@JoinColumn(name = "CART_ID", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "CARTITEM_ID", nullable = false)})
    private Set<CartItem> cartItems = new HashSet<CartItem>(0);

    public Cart() {
    }

    public Cart(User user, Date cartDate, String comment, boolean paid) {
        this.user = user;
        this.cartDate = cartDate;
        this.comment = comment;
        this.paid = paid;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCartDate() {
        return cartDate;
    }

    public void setCartDate(Date cartDate) {
        this.cartDate = cartDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItem> cartItemsSet) {
        this.cartItems = cartItemsSet;
    }

    public double getSumItems() {
        double result = 0;
        for (CartItem item : getCartItems()) {
            result += (item.getQuantity() * item.getPrice());
        }
        return result;
    }

    public int getQuantityItems() {
        int result = 0;
        for(CartItem item : getCartItems()) {
            result += item.getQuantity();
        }
        return result;
    }



}
