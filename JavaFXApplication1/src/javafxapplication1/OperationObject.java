/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import javafx.scene.control.TextField;

/**
 *
 * @author panbe
 */
public class OperationObject {
    ///custom object that allow operation label to be reset
        TextField TFProductCode;
        TextField TFProductName;
        TextField TFProductPrice;
        TextField TFID;
        TextField TFName;
        TextField TFBalance;
        boolean isTimeOut;

    public OperationObject() {
        TFProductCode = new TextField("-");
         TFProductName = new TextField("-");
         TFProductPrice = new TextField("0");
         TFID = new TextField("-");
         TFName = new TextField("-");
         TFBalance = new TextField("0");
         isTimeOut = false;
    }
        
}
