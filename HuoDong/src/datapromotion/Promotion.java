package datapromotion;

import java.util.ArrayList;

/*
 * @author л��
 * ���Ǹ��򵥵Ĵ���С����ּ��ģ����߳����̰߳�ȫ��
 * Ϊ���Է��㣬�ݶ�����Ϊ10,����Ϊ5λ��
 * */
public class Promotion {
    private static ArrayList<String> tel = new ArrayList<String>(); //�绰����ļ���
    private static int[] count = new int[50]; //�ֻ����ÿ�������Ѿ������˵ĸ��� 
	
    /*
     * doPurchase ���û���������ʵ�ֵķ���
     * @param con �û��Ƿ��Ѳμӻ���μ�Ϊtrue��δ�μ�Ϊfalse
     * */
    public static synchronized int doPurchase(String phone,int price){
        Boolean con = false;
		
        if(tel.contains(phone)){//������������ʾ�μӹ���
            con=true;
        }
		
        if(con){ //���ж��Ƿ��������
            return 1;
        } else if(count[49]>=10){ //���ж����л�Ƿ��Ѿ�������Ԥ��λ�ã�Ϊ������ֹ���������ṩ�ӿڣ�
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