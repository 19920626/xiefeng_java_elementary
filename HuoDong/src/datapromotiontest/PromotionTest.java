package datapromotiontest;

import datapromotion.Handle;
import datapromotion.Promotion;

/*
 * @author 谢峰
 * */
public class PromotionTest {
	
    public static void main(String[] args){
		String phone="12345";
		int price=1;
		
		for(int i = 0; i < 10; i ++){
			Thread doBuyPlan = new doBuyPlan(phone, price);
			
			doBuyPlan.start();
		}	
	}
    
    /*
     * 此方法用于实现线程操作
     * */
    public static class doBuyPlan extends Thread{
	    String phone = "";
	    int price = 1;
	    
	    /*
	     * 此构造函数用来传参
	     * */
	    public doBuyPlan(String phone, int price){
		    this.phone = phone;
		    this.price = price;
	    }
	   
	    /*
	     * @param state 反馈用的状态码
	     * */
	    public void run(){
		    int state = Promotion.doPurchase(phone, price);
		    Handle doHandle = new Handle();
		   
		    doHandle.dealWith(state, phone);
		   
		    try {
			    sleep((int) Math.random() * 10); 	
		    } catch (Exception e) { 
			 	e.printStackTrace();
		    }
		}
    }
}
