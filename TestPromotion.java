package TestDataPromotion;

import dataPromotion.BuyPlan;;

public class TestPromotion {
   public static void main(String[] args)throws Exception{
		//phone是某个用户，price是该用户所参与的活动价格范围是（1-50）
		String phone="";
		int price=1;
		phone="12345";
		 //有延迟地重复多次，在提交前测试时都出现了两次购买成功的案例。
		 //因此个人认为已经完成并发多线程的实验。
		for(int i=0;i<2000;i++){
			Thread t = new BuyPlan(phone,price);
		    t.start();
		}
		/*for(int i=0;i<8;i++){//随机产生号码
			  //phone="";
			  //保证前8次的号码不同
			  for (int j = 0; j < 5; j++) {
				phone=phone+(int)(Math.random()*10);
			}
		      Thread t = new Buy(phone,price);
		      t.start();
		}
		
		phone ="12345";
		Thread t = new Buy(phone,price);
	    t.start();
	    //测试重复参加的用户
	    phone="12345";
	    t = new Buy(phone,price);
	    t.start();
	    phone="23456";
	    t = new Buy(phone,price);
	    t.start();
	    //测试已经结束了的活动
	    phone="2345632";
	    t = new Buy(phone,price);
	    t.start();*/
	}

}
