package datapromotion;

/*
 * @author л��
 * ��������ͳһ�����ķ���
 * */
public class Handle {
	
    /*
     * @param state ״̬��
     * 0������ɹ���
     * 1���Ѳ��룡
     * 2��ϵ�л�������ˣ�
     * 3����ǰ������ˣ�
     * 4����ǰ���û��ʼ��
     * 5�����ǺϷ����룡
     * 6�����ǺϷ��۸�
     * */
    public void dealWith(int state, String phone){
        if(state>=0&&state<=6){ //״̬����Ч
            switch(state){
                case 0 : 
                    System.out.println("�û�" + phone + "���� ����ϲ������ɹ���");
                    break;
                case 1 : 
                    System.out.println("�û�" + phone + "���� �����Ѿ��������ˣ��˴λÿ���û�ֻ�ܲ���һ�Ρ�");
                    break;
                case 2 : 
                    System.out.println("�û�" + phone + "���� �����л�Ѿ������������ע���ǵĺ������");
                    break;
                case 3 : 
                    System.out.println("�û�" + phone + "���� ���û�Ѿ��Ѿ������������Բ����������");
                    break;
                case 4 : 
                    System.out.println("�û�" + phone + "���� ���û��û��ʼ�������Բμ��������");
                    break;
                case 5 :
                    System.out.println("�Բ���������ĺ��벻��ȷ����������ȷ�ĺ��룡");
                case 6 :
                    System.out.println("�û�" + phone + "���� ����˾δ��չ�˴λ�����߸û�Ѿ�������");
                default : 
                    break;
            }
        } else{ //״̬����Ч
            System.out.println("ϵͳ���������ԣ���");
        }	
    }
}
