/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.io.Serializable;

/**
 *
 * @author panbe
 */
public class Product implements Serializable {
    private String productCode;
    private double price;
    private String productName;
    private int quantityLeft;

    public Product(String productCode, double price, String productName, int quantityLeft) {
        this.productCode = productCode;
        this.price = price;
        this.productName = productName;
        this.quantityLeft = quantityLeft;
    }

    public Product() {
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantityLeft() {
        return quantityLeft;
    }

    public void setQuantityLeft(int quantityLeft) {
        this.quantityLeft = quantityLeft;
    }
    
    
    
    
}
