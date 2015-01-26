package server.daoimpl;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import server.dao.UserPhoneDao;
import server.entity.UserPhone;

public class UserPhoneImpl implements UserPhoneDao {
    private static SqlSessionFactory sqlSessionFactory = null;
    
    static {   
        String resource = "mybatis-config.xml";   
        Reader reader = null;   
        try {   
            reader = Resources.getResourceAsReader(resource);   
        } catch (IOException e) {   
            System.out.println(e.getMessage());   
    
        }   
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader); 
     } 

    /*
     *（数据库表结构参照huodongimprove_new.sql文档注释）
     * 将手机号码和价格插入promotion表。
     * 数据总量count+1（触发器实现）
     * 读取数据总量count，如果总量越界，则回滚，否则提交（存储过程实现）
     * */
    @Override
    public int addPhone(UserPhone phone) {
        int flag = 0; //是否成功
        SqlSession session = sqlSessionFactory.openSession();
        flag = session.insert("addUserPhone",phone);
        int count = session.selectOne("selectCounts");
        if(count - phone.getPrice() * 10000 <= 0) { //看是否越界了。
            session.commit();
        } else{
            flag = 0;
            session.rollback();
        }
        session.close();
        return flag;
    }

    @Override
    public int findUser(UserPhone phone) {
        int flag = 0; //所查对象的下标，没有则为0
        SqlSession session = sqlSessionFactory.openSession();
        try {
            List<Integer> count = session.selectList("selectUserPhone", phone.getPhone());
            if(count.size() > 0){
                flag = count.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.commit();
            session.close();
        }
        return flag;
    }

    @Override
    public int countAll() {
        int flag = 0; //所有参与的用户的总量
        SqlSession session = sqlSessionFactory.openSession();
        flag = session.selectOne("selectCounts");
        session.commit();
        session.close();
        return flag;
    }

}
