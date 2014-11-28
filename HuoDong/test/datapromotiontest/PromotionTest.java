package datapromotiontest;

import org.junit.Test;

import datapromotion.Handle;
import datapromotion.Promotion;

public class PromotionTest {

	@Test
	public void testDoPurchase() {
		
		for(int i=0;i<10;i++){
			new Thread(){
				String phone="12345";
				int price=1;
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
			}.start();
		}	
	}

}
