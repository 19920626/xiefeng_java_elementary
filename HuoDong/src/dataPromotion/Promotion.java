package dataPromotion;

//import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

/*
 * 这是个简单的促销小程序，旨在模拟多线程下线程安全。
 * 为测试方便，梯度设置为10,号码为5位数
 * 
 * */
public class Promotion {
	//tel是电话号码的集合，count是手机活动的每个级别已经参与人的个数
	private static Vector<String> tel = new Vector<String>();
	private static int[] count = new int[50];
	//预留接口，为了测试最大TPS
	//private static long startTime=new Date().getTime();
	
	//浏览了几位热心网友的博客，发现要让该类所有实例都有约束
	//需要使用静态类。
	public static synchronized int doPurchase(String phone,int price){
		Boolean con = false;
		Iterator<String> t = tel.iterator();
		while(t.hasNext()){
			if(t.next().equals(phone)){
				con=true;
			}
		}
		//先判断是否参与过活动了
		if(con){
			//System.out.println("您已经参与过活动了，此次活动每个用户只能参与一次。");
			return 1;
		}
		//再判断所有活动是否已经结束（预留位置，为后续阻止程序运行提供接口）
		else if(count[49]>=10){
			//System.out.println("所有活动已经结束，敬请关注我们的后续活动。");
			return 2;
		}
		//再判断活动是否已经结束
		else if(count[price-1]>=10){
			//System.out.println("该活动已经已经结束，您可以参与其他活动。");
			return 3;
		}
		//再判断活动是否已经开始
		else if(count[price-1]==0&&price>1&&count[price-2]<10){
			//System.out.println("该活动还没开始，您可以参加其他活动。");
			return 4;
		}
		//符合要求，将电话号码录入，当前活动参与人数+1
		tel.addElement(phone);
		count[price-1]++;
		//System.out.println(phone+" 用户成功购买！！");	
		return 0;
	}
    
}
