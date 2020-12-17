package com.bawa.babyneedsapp.model;

public class Item {
    private int id;
    private String productName;
    private String productColor;
    private int productQuantity;
    private String productSize;
    private String dateAdded;


    public Item() {
    }

    public Item(int id, String productName, String productColor, int productQuantity, String productSize, String dateAdded) {
        this.id = id;
        this.productName = productName;
        this.productColor = productColor;
        this.productQuantity = productQuantity;
        this.productSize = productSize;
        this.dateAdded = dateAdded;
    }

    public Item(String productName, String productColor, int productQuantity, String productSize, String dateAdded) {
        this.productName = productName;
        this.productColor = productColor;
        this.productQuantity = productQuantity;
        this.productSize = productSize;
        this.dateAdded = dateAdded;
    }

    public Item(String productName, String productColor, int productQuantity, String productSize) {
        this.productName = productName;
        this.productColor = productColor;
        this.productQuantity = productQuantity;
        this.productSize = productSize;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductColor() {
        return productColor;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }
}
