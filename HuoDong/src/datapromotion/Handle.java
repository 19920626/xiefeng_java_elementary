package datapromotion;

/*
 * @author 谢峰
 * 该类用于统一处理活动的反馈
 * */
public class Handle {
	
	/*
	 * @param state 状态码
	 * 0：购买成功！
	 * 1：已参与！
	 * 2：系列活动都结束了！
	 * 3：当前活动结束了！
	 * 4：当前活动还没开始！
	 * */
	public void dealWith(int state, String phone){
		if(state >= 0&&state <= 4){ //状态码有效
			switch(state){
				case 0 : 
					System.out.println("用户" + phone + "您好 ：恭喜您购买成功！");
					break;
				case 1 : 
					System.out.println("用户" + phone + "您好 ：您已经参与过活动了，此次活动每个用户只能参与一次。");
					break;
				case 2 : 
					System.out.println("用户" + phone + "您好 ：所有活动已经结束，敬请关注我们的后续活动。");
					break;
				case 3 : 
					System.out.println("用户" + phone + "您好 ：该活动已经已经结束，您可以参与其他活动。");
					break;
				case 4 : 
					System.out.println("用户" + phone + "您好 ：该活动还没开始，您可以参加其他活动。");
					break;
				default : 
					break;
			}
		} else{ //状态码无效
			System.out.println("系统错误，请重试！！");
		}	
	}
}
