package dataPromotion;
import java.util.Iterator;
import java.util.Vector;
public class BuyPlan extends Thread{
static Promotion p = new Promotion();
static Vector<String> tel = p.getTel();
static int[] count = p.getCount();
private String phone;
private int price;
public BuyPlan(String phone_number,int price_detail){
this.phone=phone_number;
this.price=price_detail;
}
public void run(){
doPurchase(phone, price);
}
//浏览了几位热心网友的博客，发现要让该类所有实例都有约束
//需要使用静态类。
static synchronized void doPurchase(String phone,int price){
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
//再判断所有活动是否已经结束（预留位置，为后续阻止程序运行提供接口）
else if(count[49]>=10){
System.out.println("所有活动已经结束，敬请关注我们的后续活动。");
}
//再判断活动是否已经结束
else if(count[price-1]>=10){
System.out.println("该活动已经已经结束，您可以参与其他活动。");
}
//再判断活动是否已经开始
else if(count[price-1]==0&&price>1&&count[price-2]<10){
System.out.println("该活动还没开始，您可以参加其他活动。");
}
//符合要求，将电话号码录入，当前活动参与人数+1
else{
tel.addElement(phone);
count[price-1]++;
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
