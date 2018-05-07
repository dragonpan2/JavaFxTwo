/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import javafx.scene.media.AudioClip;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;


/**
 *
 * @author panbe
 */
public class Main {

    private static String previousType = "null"; //Those static variable are used in readOperation
    private static int previousUserIndice = -1;  //to understund the type of scan
    private static int previousProductIndice = -1;
    
    public static ReadReturn readOperation(String barCode, int userIndice, int productIndice) {
        ReadReturn readReturn = new ReadReturn();
        ProductList productListMaster = new ProductList();
        UserList userListMaster = new UserList();

        boolean whipeUserReturn = false;
        boolean whipeProductReturn = false;

        userListMaster.userList = new ArrayList<User>();
        productListMaster.productList = new ArrayList<Product>();

        productListMaster = readProductDataFile(productListMaster);
        userListMaster = readUserDataFile(userListMaster);

        userIndice = userListMaster.lookUpUser(barCode);
        productIndice = productListMaster.lookUpProduct(barCode);

        readReturn.productIndice = productIndice;
        readReturn.userIndice = userIndice;

        System.out.println("/////////////////////");
        System.out.println("ReadOperation Debut:");
        System.out.println("/////////////////////");
        System.out.println("PreviousType: " + previousType);
        System.out.println("PreviousProductIndice: " + previousProductIndice);
        System.out.println("PreviousUserIndice: " + previousUserIndice);
        System.out.println("ProductIndice: " + productIndice);
        System.out.println("UserIndice: " + userIndice);
        System.out.println("---------------------");
        
        if (userIndice == 0) {
            previousProductIndice = -1;
            previousUserIndice = -1;
            previousType = "null";
            readReturn.productIndice =-1;
            readReturn.userIndice    =-1;
        }
        if (userIndice != -1 && productIndice != -1) {
            previousType = "both";
            //major problem, barcode is in both database
            previousProductIndice = -1;
            previousUserIndice = -1;

        } else if (productIndice != -1 && !previousType.equals("user")) {
            //just a product scan
            previousType = "product";
            previousProductIndice = productIndice;
            previousUserIndice = -1;
            
            //
            readReturn.productIndice = productIndice;
            readReturn.userIndice  = -1;

           // JavaFXApplication1.operationObject.isTimeOut = true;
            // timer.schedule(timerTask, 5000l); ///
        } else if (productIndice != -1 && previousType.equals("user")) {
            //buying product
            //JavaFXApplication1.operationObject.isTimeOut = false;

            System.out.println("Buying product");
            double userBalance = userListMaster.userList.get(previousUserIndice).getBalance();
            int quantity = productListMaster.productList.get(productIndice).getQuantityLeft();
            double itemPrice = productListMaster.productList.get(productIndice).getPrice();

            userListMaster.userList.get(previousUserIndice).setBalance(userBalance - itemPrice);
            productListMaster.productList.get(productIndice).setQuantityLeft(quantity - 1);

            previousType = "null";
            previousProductIndice = -1;
            previousUserIndice = -1;
            //tempo
            
            readReturn.productIndice = -1;
            readReturn.userIndice = -1;
            //
            JavaFXApplication1.newBalanceData.setText(Double.toString(userBalance - itemPrice)+"$");
            JavaFXApplication1.oldBalanceData.setText(Double.toString(userBalance)+"$");
            ///
            
            
            playSound("checkout.wav");
           JavaFXApplication1.opPane.setStyle("-fx-background-color:#33FF57;");
           //for failed #FF5733
            
             Timer timer = new Timer();
             TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(
                            () -> {
                                 JavaFXApplication1.opPane.setStyle("-fx-background-color:#2c3e50;");
                                 JavaFXApplication1.newBalanceData.setText("");
                                 JavaFXApplication1.oldBalanceData.setText("");
                            }
                    );
                }
            };
            timer.schedule(timerTask, 4500); ///
            
        } else if (userIndice != -1 && !previousType.equals("product")) {
            //
            previousType = "user";
            previousProductIndice = -1;
            previousUserIndice = userIndice;
            System.out.println("Inside if: previousUserIndice: " + previousUserIndice);
            System.out.println("userIndice != -1 && !previousType.equals(\"product\")");
        } else if (userIndice != -1 && previousType.equals("product")) {
            //then it's a user lookup
            ///next is wait for product then do buy
            previousType = "user";
            previousProductIndice = -1;
            previousUserIndice = userIndice;

        } //        else if (userIndice != -1 && previousType.equals("user")) {
        //            previousType = "user";
        //            previousProductIndice = -1;
        //            previousUserIndice = userIndice;
        //        }
        else {
            previousProductIndice = -1;
            previousUserIndice = -1;
            previousType = "null";
            // barcode is not in any database
            /// next is ready for new operation
        }

        ///
        if (whipeProductReturn == true) {
            whipeProductReturn = false;

        }
        if (whipeUserReturn == true) {
            whipeUserReturn = false;
        }

        ///saved all database
        writeUserDataFile(userListMaster);
        writeProdctDataFile(productListMaster);
        readReturn.productListMaster = productListMaster;
        readReturn.userListMaster = userListMaster;

        return readReturn;
    }

    //give a UserList and write it as the new userListMaster in data file
    public static void writeUserDataFile(UserList userListMaster) {
        System.out.println("Writing UserData");
        try {
            FileOutputStream fileOut = new FileOutputStream("userData.txt");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(userListMaster);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
            System.out.println("Exception");
        }
        System.out.println("UserData Written");
    }
    
    //give a ProductList, write it as the new productListMaster
    public static void writeProdctDataFile(ProductList productListMaster) {
        System.out.println("Writing ProductData");
        try {
            FileOutputStream fileOut = new FileOutputStream("productData.txt");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(productListMaster);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
            System.out.println("Exception");
        }
        System.out.println("ProductData Written");
    }
    
    //return the masterProductList from data file
    public static ProductList readProductDataFile(ProductList productListMaster) {
        System.out.println("Reading ProductList");
        try {
            FileInputStream fileIn = new FileInputStream("productData.txt");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            productListMaster = (ProductList) in.readObject();
            in.close();
            fileIn.close();
        } catch (ClassNotFoundException c) {
            System.out.println("Exception");
        } catch (IOException i) {
            i.printStackTrace();
            System.out.println("Exception");
        }
        System.out.println("ProductList Read");
        return productListMaster;
    }

    //return the MasterUserDataFile from the data file 
    public static UserList readUserDataFile(UserList userListMaster) {
        System.out.println("Reading UserList");
        try {
            FileInputStream fileIn = new FileInputStream("userData.txt");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            userListMaster = (UserList) in.readObject();
            in.close();
            fileIn.close();
        } catch (ClassNotFoundException c) {
            System.out.println("Exception");
        } catch (IOException i) {
            System.out.println("Exception");
        }
        System.out.println("UserList Read");
        return userListMaster;
    }

    //Add a new Product with the follow attribut to the loaded list
    public static void addNewProduct(ProductList productListMaster, String productCode, String productName, double price, int quantityToAdd) {
        Product newProduct = new Product();

        newProduct.setProductCode(productCode);
        newProduct.setProductName(productName);
        newProduct.setPrice(price);
        newProduct.setQuantityLeft(quantityToAdd);

        productListMaster.productList.add(newProduct);
    }

    public static void addNewProduct(ProductList productListMaster, String productCode, String productName, double price) {
        Product newProduct = new Product();
        newProduct.setProductCode(productCode);
        newProduct.setProductName(productName);
        newProduct.setPrice(price);
        newProduct.setQuantityLeft(0);

        productListMaster.productList.add(newProduct);
    }

    //add a new user with the following attributs to the loaded list
    public static void addNewUser(UserList userListMaster, String idBarcode, String name, double balance) {
        User newUser = new User();
        newUser.setCardId(idBarcode);
        newUser.setUsername(name);
        newUser.setBalance(balance);

        userListMaster.userList.add(newUser);
    }

    
    public void userBalanceAdjust(User user, double adjustAmount) {
        user.setBalance(user.getBalance() + adjustAmount);
    }

    public void productPriceAdjust(Product product, double newPrice) {
        product.setPrice(newPrice);
    }

    public void productAdjustQuantity(Product product, int quantityAdjust) {
        product.setQuantityLeft(product.getQuantityLeft() + quantityAdjust);
    }

    public static void firstTimeProtocol() {
        System.out.println("Executing First Time Protocol");
        try {
            FileOutputStream fileOut = new FileOutputStream("productData.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(new ProductList());
            out.close();
            fileOut.close();
        } catch (IOException i) {

            System.out.println("Exception");
        }
        System.out.println("Done");
    }
    
    public static void playSound(String file) {
        
        //
        if (file.equals("checkout.wav")) {
        
            URL resource = Main.class.getResource("checkout.wav");
            AudioClip note = new AudioClip(resource.toString());
            note.play();
        }
    }
}
