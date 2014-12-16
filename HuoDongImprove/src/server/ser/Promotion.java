package server.ser;

import server.daoimpl.UserPhoneImpl;
import server.entity.UserPhone;

public class Promotion {
    /*
     * doPurchase 是用户参与活动功能实现的方法
     * */
    public static int doPurchase(long phone,int price) {        
        boolean isLegalPrice = false;
        
        if(price >= 1 && price <= 50) { //在1-50内则为合法值
            isLegalPrice = true;
        }
        
        boolean isLegalPhone = false;
        long num1 = 100000000000l;
        long num2 = 10000000000l;
        if((phone / num1 == 0) && (phone / num2 > 0)) { //phone的长度11，则为合法值
            isLegalPhone = true;
        }
               
        UserPhoneImpl userPhone = new UserPhoneImpl();
        UserPhone user = new UserPhone();
        user.setPhone(phone);
        user.setPrice(price);
        int con = 0;
        con = userPhone.findUser(user); //如果包含，则表示参加过了
        int count = userPhone.countAll();
        
        if(!isLegalPhone) { //看号码是否合法
            return 5;
        } else if(!isLegalPrice) { //看价格是否合法
            return 6;
        } else if(con != 0){ //先判断是否参与过活动了
            return 1;
        } else if(count >= 500000) { //再判断所有活动是否已经结束（预留位置，为后续阻止程序运行提供接口）
            return 2;
        } else if((count-(price-1)*10000) >= 10000){ //再判断活动是否已经结束
            return 3;
        } else if((count-(price-1)*10000) == 0 && price > 1 && (count-(price-2)*10000) < 10000){ //再判断活动是否已经开始
            return 4;
        }
        
        int flag = userPhone.addPhone(user); //符合要求，将电话号码录入，当前活动参与人数+1
        if(flag <= 0) { //如果插入异常，返回7
            return 7;
        }
        
        return 0;
    }
}
