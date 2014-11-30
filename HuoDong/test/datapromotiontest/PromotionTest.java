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
 * @author –ª∑Â
 * @version 1.0
 * µ•‘™≤‚ ‘£¨≤‚ ‘promotion¿‡
 * */
@RunWith(Parameterized.class)
public class PromotionTest {
    int price;
    String phone;
    int result;
    
    public PromotionTest(int result, String phone, int price) {
        this.price = price;
        this.phone = phone;
        this.result = result;
    }
    
    @Parameters
    public static Collection<?> doPurchaseValues() {
        return Arrays.asList(new Object[][]{
            {0,"12345",1},
            {1,"12345",1},
            {0,"11345",1},
            {0,"12145",1},
            {0,"12315",1},
            {0,"12341",1},
            {0,"13345",1},
            {0,"12335",1},
            {4,"23456",2},
            {0,"12343",1},
            {0,"32345",1},
            {0,"32343",1},
            {0,"34567",2}
        });
    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        System.out.println("≤‚ ‘ø™ º£°£°");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        System.out.println("≤‚ ‘Ω· ¯£°£°");
    }

    @Test
    public void testDoPurchase() {
<<<<<<< HEAD
        int state = Promotion.doPurchase(phone, price);
        
        assertEquals(result, state);
=======
		
        for(int i=0;i<10;i++){
            new Thread(){
                String phone="12345";
                int price=1;
                public void run(){
                    int state = Promotion.doPurchase(phone, price);
                    Handle doHandle = new Handle();
				   
                    doHandle.dealWith(state, phone);
				   
                    try {
                        sleep((int) Math.random() * 10); 	
                    } catch (Exception e) { 
                        e.printStackTrace();
                    }
                }
            }.start();
        }	
>>>>>>> parent of 17c4897... Revert "add project HuoDong"
    }

}
