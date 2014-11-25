import java.util.Iterator;
import java.util.Vector;

/*
 * 这是个简单的促销小程序，旨在模拟多线程下线程安全。
 * 为测试方便，梯度设置为10,号码为5位数
 * 
 * */
public class Promotion {
	private static Vector<String> tel = new Vector<String>();
	private static int[] people_count = new int[50];
	public static synchronized Vector<String> getTel() {
		return tel;
	}
	public static synchronized void setTel(Vector<String> tel) {
		Promotion.tel = tel;
	}
	public static synchronized int[] getPeople_count() {
		return people_count;
	}
	public static synchronized void setPeople_count(int[] peopleCount) {
		people_count = peopleCount;
	}
	public static void main(String[] args)throws Exception{
		
		String phone="";
		int price=1;
		phone="12345";
		 //有延迟地重复多次，在提交前测试时都出现了两次购买成功的案例。
		 //因此个人认为已经完成并发多线程的实验。
		for(int i=0;i<2000;i++){
			Thread t = new Buy(phone,price);
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


class Buy extends Thread{
	//电话号码用vector存，确保唯一性。
	static Promotion p = new Promotion();
	static Vector<String> tel = p.getTel();
	static int[] people_count = p.getPeople_count();
	private String phone;
	private int price;
	

	public Buy(String phone_number,int price_detail){
		this.phone=phone_number;
		this.price=price_detail;
	}
	
	public void run(){		
		operate(phone, price);
	}
	//浏览了几位热心网友的博客，发现要让该类所有实例都有约束
	//需要使用静态类。
	static synchronized void operate(String phone,int price){
		Boolean con = false;
		Iterator<String> t = tel.iterator();
		while(t.hasNext()){
			if(t.next().equals(phone)){
				con=true;
			}
		}
		//先判断是否参与过活动了
		if(con){
			System.out.println("您已经参与过活动了，此次活动每个用户只能参与一次。");
		}
		//再判断活动是否已经结束
		else if(people_count[price-1]>=10){
			System.out.println("该活动已经已经结束，您可以参与其他活动。");
		}
		//再判断活动是否已经开始
		else if(people_count[price-1]==0&&price>1&&people_count[price-2]<10){
			System.out.println("该活动还没开始，您可以参加其他活动。");
		}
		//符合要求，将电话号码录入，当前活动参与人数+1
		else{
			tel.addElement(phone);
			people_count[price-1]++;
			System.out.println(phone+" 用户成功购买！！");
		}
	try {
			sleep((int) Math.random() * 10); 
		//延迟是为了模拟多线程同时发生（不知道这个方式对不对）	
		} catch (Exception e) { 
			e.printStackTrace();
			System.out.println("sdafasd");
		}
		
	}
}
