package client;

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
     * 5：不是合法号码！
     * 6：不是合法价格！
     * */
    public String dealWith(int state, long phone){
        if(state>=0&&state<=6){ //状态码有效
            switch(state){
                case 0 : 
                    return ("用户" + phone + "您好 ：恭喜您购买成功！");
                case 1 : 
                    return ("用户" + phone + "您好 ：您已经参与过活动了，此次活动每个用户只能参与一次。");
                case 2 : 
                    return ("用户" + phone + "您好 ：所有活动已经结束，敬请关注我们的后续活动。");
                case 3 : 
                    return ("用户" + phone + "您好 ：该活动已经已经结束，您可以参与其他活动。");
                case 4 : 
                    return ("用户" + phone + "您好 ：该活动还没开始，您可以参加其他活动。");
                case 5 :
                    return ("对不起，您输入的号码不正确！请输入正确的号码！");
                case 6 :
                    return ("用户" + phone + "您好 ：公司未开展此次活动，或者该活动已经结束！");
                default : 
                    break;
            }
        }  
        
        return ("系统错误，请重试！！"); //状态码无效 
    }
}
