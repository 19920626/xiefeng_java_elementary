package server.ser;

import server.daoimpl.UserPhoneImpl;
import server.entity.UserPhone;

public class Promotion {
    /*
     * doPurchase ���û���������ʵ�ֵķ���
     * */
    public static int doPurchase(long phone,int price) {        
        boolean isLegalPrice = false;
        
        if(price >= 1 && price <= 50) { //��1-50����Ϊ�Ϸ�ֵ
            isLegalPrice = true;
        }
        
        boolean isLegalPhone = false;
        long num1 = 100000000000l;
        long num2 = 10000000000l;
        if((phone / num1 == 0) && (phone / num2 > 0)) { //phone�ĳ���11����Ϊ�Ϸ�ֵ
            isLegalPhone = true;
        }
               
        UserPhoneImpl userPhone = new UserPhoneImpl();
        UserPhone user = new UserPhone();
        user.setPhone(phone);
        user.setPrice(price);
        int con = 0;
        con = userPhone.findUser(user); //������������ʾ�μӹ���
        int count = userPhone.countAll();
        
        if(!isLegalPhone) { //�������Ƿ�Ϸ�
            return 5;
        } else if(!isLegalPrice) { //���۸��Ƿ�Ϸ�
            return 6;
        } else if(con != 0){ //���ж��Ƿ��������
            return 1;
        } else if(count >= 500000) { //���ж����л�Ƿ��Ѿ�������Ԥ��λ�ã�Ϊ������ֹ���������ṩ�ӿڣ�
            return 2;
        } else if((count-(price-1)*10000) >= 10000){ //���жϻ�Ƿ��Ѿ�����
            return 3;
        } else if((count-(price-1)*10000) == 0 && price > 1 && (count-(price-2)*10000) < 10000){ //���жϻ�Ƿ��Ѿ���ʼ
            return 4;
        }
        
        int flag = userPhone.addPhone(user); //����Ҫ�󣬽��绰����¼�룬��ǰ���������+1
        if(flag <= 0) { //��������쳣������7
            return 7;
        }
        
        return 0;
    }
}
