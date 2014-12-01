package datapromotiontest;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import datapromotion.Promotion;
/*
 * @author л��
 * @version 2.0
 * ��Ԫ���ԣ�����promotion��
 * */
@RunWith(Parameterized.class)
public class PromotionTest {
    int price;
<<<<<<< HEAD
    long phone;
    int result;
    
    public PromotionTest(int result, long phone, int price) {
=======
    StringBuffer phone = new StringBuffer();
    int result;
    
    public PromotionTest(int result, StringBuffer phone, int price) {
>>>>>>> parent of 0dbcbf6... Revert "add project HuoDong"
        this.price = price;
        this.phone.append(phone);
        this.result = result;
    }
    
    @Parameters
    public static Collection<?> doPurchaseValues() {
        return Arrays.asList(new Object[][]{
<<<<<<< HEAD
            {0, 12345, 1}, //��ȷ��ʽ
            {1, 12345, 1}, //�������
            {0, 11345, 1},
            {0, 12145, 1},
            {0, 12315, 1},
            {0, 12341, 1},
            {0, 13345, 1},
            {0, 12335, 1},
            {4, 23456, 2}, //��û��ʼ
            {0, 12343, 1},
            {0, 32345, 1},
            {0, 32343, 1},
            {5, 323431, 1}, //���벻�Ϸ�
            {6, 11111, -1}, //�۸񲻺Ϸ�
            {0, 34567, 2} //�������ֻ
=======
            {0, new StringBuffer("12345"), 1}, //��ȷ��ʽ
            {1, new StringBuffer("12345"), 1}, //�������
            {0, new StringBuffer("11345"), 1},
            {0, new StringBuffer("12145"), 1},
            {0, new StringBuffer("12315"), 1},
            {0, new StringBuffer("12341"), 1},
            {0, new StringBuffer("13345"), 1},
            {0, new StringBuffer("12335"), 1},
            {4, new StringBuffer("23456"), 2}, //��û��ʼ
            {0, new StringBuffer("12343"), 1},
            {0, new StringBuffer("32345"), 1},
            {0, new StringBuffer("32343"), 1},
            {5, new StringBuffer("323431"), 1}, //���벻�Ϸ�
            {6, new StringBuffer("11111"), -1}, //�۸񲻺Ϸ�
            {0, new StringBuffer("34567"), 2} //�������ֻ
>>>>>>> parent of 0dbcbf6... Revert "add project HuoDong"
        });
    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        System.out.println("���Կ�ʼ����");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        System.out.println("���Խ�������");
    }

    @Test
    public void testDoPurchase() {
        int state = Promotion.doPurchase(phone, price);
        
        assertEquals(result, state);
    }

}
