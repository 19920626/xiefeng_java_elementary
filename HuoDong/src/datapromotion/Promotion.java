package datapromotion;

import java.util.ArrayList;

/*
 * @author л��
 * ���Ǹ��򵥵Ĵ���С����ּ��ģ����߳����̰߳�ȫ��
 * Ϊ���Է��㣬�ݶ�����Ϊ10,����Ϊ5λ��
 * */
public class Promotion {
    private static ArrayList<Long> tel = new ArrayList<Long>(); //�绰����ļ���
    private static int[] count = new int[50]; //�ֻ����ÿ�������Ѿ������˵ĸ��� 
	
    /*
     * doPurchase ���û���������ʵ�ֵķ���
     * */
    public static synchronized int doPurchase(long phone,int price) {
        boolean con = false;
        boolean isLegalPhone = false;
        boolean isLegalPrice = false;
        
        if(price >= 1 && price <= 50) { //��1-50����Ϊ�Ϸ�ֵ
            isLegalPrice = true;
        }
       
        if((phone / 100000 == 0) && (phone / 10000 > 0)) { //phone�ĳ���Ϊ5����Ϊ�Ϸ�ֵ��ʵ��ӦΪ11��
            isLegalPhone = true;
        }
		
        con = tel.contains(phone); //������������ʾ�μӹ���
		
        if(!isLegalPhone) { //�������Ƿ�Ϸ�
            return 5;
        } else if(!isLegalPrice) { //���۸��Ƿ�Ϸ�
            return 6;
        } else if(con){ //���ж��Ƿ��������
            return 1;
        } else if(count[49]>=10) { //���ж����л�Ƿ��Ѿ�������Ԥ��λ�ã�Ϊ������ֹ���������ṩ�ӿڣ�
            return 2;
        } else if(count[price-1]>=10){ //���жϻ�Ƿ��Ѿ�����
            return 3;
        } else if(count[price-1]==0&&price>1&&count[price-2]<10){ //���жϻ�Ƿ��Ѿ���ʼ
            return 4;
        }
		
        tel.add(phone); //����Ҫ�󣬽��绰����¼�룬��ǰ���������+1
        count[price-1]++;

        return 0;
    }
    
}