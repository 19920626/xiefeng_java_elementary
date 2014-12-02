package server.ser;

import server.daoImpl.UserPhoneImpl;
import server.entity.UserPhone;

public class Promotion {
    /*
     * doPurchase ���û���������ʵ�ֵķ���
     * */
    public static synchronized int doPurchase(long phone,int price) {        
        boolean isLegalPrice = false;
        
        if(price >= 1 && price <= 50) { //��1-50����Ϊ�Ϸ�ֵ
            isLegalPrice = true;
        }
        
        boolean isLegalPhone = false;
        
        if((phone / 100000 == 0) && (phone / 10000 > 0)) { //phone�ĳ���Ϊ5����Ϊ�Ϸ�ֵ��ʵ��ӦΪ11��
            isLegalPhone = true;
        }
               
        UserPhoneImpl userPhone = new UserPhoneImpl();
        UserPhone user = new UserPhone();
        user.setPhone(phone);
        int con = 0;
        con = userPhone.findUser(user); //������������ʾ�μӹ���
        int count = userPhone.countAll();
        
        if(!isLegalPhone) { //�������Ƿ�Ϸ�
            return 5;
        } else if(!isLegalPrice) { //���۸��Ƿ�Ϸ�
            return 6;
        } else if(con != 0){ //���ж��Ƿ��������
            return 1;
        } else if(count >= 500) { //���ж����л�Ƿ��Ѿ�������Ԥ��λ�ã�Ϊ������ֹ���������ṩ�ӿڣ�
            return 2;
        } else if((count-(price-1)*10) >= 10){ //���жϻ�Ƿ��Ѿ�����
            return 3;
        } else if((count-(price-1)*10) == 0 && price > 1 && (count-(price-2)*10) < 10){ //���жϻ�Ƿ��Ѿ���ʼ
            return 4;
        }
        
        userPhone.addPhone(user); //����Ҫ�󣬽��绰����¼�룬��ǰ���������+1

        return 0;
    }
}
