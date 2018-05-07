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
public class UserList implements Serializable {
    ArrayList<User> userList;

    public UserList() {
    }

    public UserList(ArrayList<User> userList) {
        this.userList = userList;
    }
    public int lookUpUser(String userCode) {
        for (int i = 0;i<this.userList.size();i++) {
            if (this.userList.get(i).getCardId().equals(userCode)) {
                return i;
            }
        }
        return -1;
    }
}
