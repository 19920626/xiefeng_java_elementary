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
 * @author 谢峰
 * @version 2.0
 * 单元测试，测试promotion类
 * */
@RunWith(Parameterized.class)
public class PromotionTest {
    int price;
    long phone;
    int result;
    
    public PromotionTest(int result, long phone, int price) {
        this.price = price;
        this.phone = phone;
        this.result = result;
    }
    
    @Parameters
    public static Collection<?> doPurchaseValues() {
        return Arrays.asList(new Object[][]{
            {0, 12345, 1}, //正确格式
            {1, 12345, 1}, //参与过了
            {0, 11345, 1},
            {0, 12145, 1},
            {0, 12315, 1},
            {0, 12341, 1},
            {0, 13345, 1},
            {0, 12335, 1},
            {4, 23456, 2}, //还没开始
            {0, 12343, 1},
            {0, 32345, 1},
            {0, 32343, 1},
            {5, 323431, 1}, //号码不合法
            {6, 11111, -1}, //价格不合法
            {0, 34567, 2} //晋级此轮活动
        });
    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        System.out.println("测试开始！！");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        System.out.println("测试结束！！");
    }

    @Test
    public void testDoPurchase() {
        int state = Promotion.doPurchase(phone, price);
        
        assertEquals(result, state);
    }

}
