package server.ser;

import server.daoImpl.UserPhoneImpl;
import server.entity.UserPhone;

public class Promotion {
    /*
     * doPurchase 是用户参与活动功能实现的方法
     * */
    public static synchronized int doPurchase(long phone,int price) {        
        boolean isLegalPrice = false;
        
        if(price >= 1 && price <= 50) { //在1-50内则为合法值
            isLegalPrice = true;
        }
        
        boolean isLegalPhone = false;
        
        if((phone / 100000 == 0) && (phone / 10000 > 0)) { //phone的长度为5，则为合法值（实际应为11）
            isLegalPhone = true;
        }
               
        UserPhoneImpl userPhone = new UserPhoneImpl();
        UserPhone user = new UserPhone();
        user.setPhone(phone);
        int con = 0;
        con = userPhone.findUser(user); //如果包含，则表示参加过了
        int count = userPhone.countAll();
        
        if(!isLegalPhone) { //看号码是否合法
            return 5;
        } else if(!isLegalPrice) { //看价格是否合法
            return 6;
        } else if(con != 0){ //先判断是否参与过活动了
            return 1;
        } else if(count >= 500) { //再判断所有活动是否已经结束（预留位置，为后续阻止程序运行提供接口）
            return 2;
        } else if((count-(price-1)*10) >= 10){ //再判断活动是否已经结束
            return 3;
        } else if((count-(price-1)*10) == 0 && price > 1 && (count-(price-2)*10) < 10){ //再判断活动是否已经开始
            return 4;
        }
        
        userPhone.addPhone(user); //符合要求，将电话号码录入，当前活动参与人数+1

        return 0;
    }
}
