package datapromotion;

import java.util.ArrayList;

/*
 * @author 谢峰
 * 这是个简单的促销小程序，旨在模拟多线程下线程安全。
 * 为测试方便，梯度设置为10,号码为5位数
 * */
public class Promotion {
<<<<<<< HEAD
    private static ArrayList<Long> tel = new ArrayList<Long>(); //电话号码的集合
=======
    private static ArrayList<StringBuffer> tel = new ArrayList<StringBuffer>(); //电话号码的集合
>>>>>>> parent of 0dbcbf6... Revert "add project HuoDong"
    private static int[] count = new int[50]; //手机活动的每个级别已经参与人的个数 
	
    /*
     * doPurchase 是用户参与活动功能实现的方法
     * */
<<<<<<< HEAD
    public static synchronized int doPurchase(long phone,int price) {
=======
    public static synchronized int doPurchase(StringBuffer phone,int price) {
>>>>>>> parent of 0dbcbf6... Revert "add project HuoDong"
        boolean con = false;
        boolean isLegalPhone = false;
        boolean isLegalPrice = false;
        
        if(price >= 1 && price <= 50) { //在1-50内则为合法值
            isLegalPrice = true;
        }
       
<<<<<<< HEAD
        if((phone / 100000 == 0) && (phone / 10000 > 0)) { //phone的长度为5，则为合法值（实际应为11）
            isLegalPhone = true;
        }
		
        con = tel.contains(phone); //如果包含，则表示参加过了
		
=======
        if(phone.length() == 5) { //phone的长度为5，则为合法值（实际应为11）
            isLegalPhone = true;
        }
		
        if(tel.toString().contains(phone.toString())) { //如果包含，则表示参加过了（会不会影响效率？）
            con=true;
        }
		
>>>>>>> parent of 0dbcbf6... Revert "add project HuoDong"
        if(!isLegalPhone) { //看号码是否合法
            return 5;
        } else if(!isLegalPrice) { //看价格是否合法
            return 6;
        } else if(con){ //先判断是否参与过活动了
            return 1;
        } else if(count[49]>=10) { //再判断所有活动是否已经结束（预留位置，为后续阻止程序运行提供接口）
            return 2;
        } else if(count[price-1]>=10){ //再判断活动是否已经结束
            return 3;
        } else if(count[price-1]==0&&price>1&&count[price-2]<10){ //再判断活动是否已经开始
            return 4;
        }
		
        tel.add(phone); //符合要求，将电话号码录入，当前活动参与人数+1
        count[price-1]++;

        return 0;
    }
    
}