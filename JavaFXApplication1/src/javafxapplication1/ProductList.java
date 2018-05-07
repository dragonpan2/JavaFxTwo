/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author panbe
 */
public class ProductList implements Serializable {
    ArrayList<Product> productList;

    public ProductList() {
    }

    public ProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }
    public int lookUpProduct(String productCode) {
        for (int i = 0; i < this.productList.size(); i++) {
            if (this.productList.get(i).getProductCode().equals(productCode)) {
                return i;
            }
        }
        return -1;
    }
}
