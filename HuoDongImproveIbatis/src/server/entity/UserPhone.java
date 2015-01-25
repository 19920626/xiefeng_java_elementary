package server.entity;

import java.io.Serializable;
/*
 * @author –ª∑Â
 *  µÃÂ¿‡
 * */
@SuppressWarnings("serial")
public class UserPhone implements Serializable{
    private long phone;
    private int price;

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    
    public UserPhone() {
        
    }
    
    public UserPhone(long phone, int price) {
        this.phone = phone;
        this.price = price;
    }
}
