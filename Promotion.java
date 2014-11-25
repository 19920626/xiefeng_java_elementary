package dataPromotion;

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
	public static Vector<String> getTel() {
		return tel;
	}
	public static void setTel(Vector<String> tel) {
		Promotion.tel = tel;
	}
	public static int[] getCount() {
		return count;
	}
	public static void setCount(int[] count) {
		Promotion.count = count;
	}
    
}
