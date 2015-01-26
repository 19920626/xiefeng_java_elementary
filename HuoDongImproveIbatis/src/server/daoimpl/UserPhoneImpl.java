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
     *�����ݿ��ṹ����huodongimprove_new.sql�ĵ�ע�ͣ�
     * ���ֻ�����ͼ۸����promotion��
     * ��������count+1��������ʵ�֣�
     * ��ȡ��������count���������Խ�磬��ع��������ύ���洢����ʵ�֣�
     * */
    @Override
    public int addPhone(UserPhone phone) {
        int flag = 0; //�Ƿ�ɹ�
        SqlSession session = sqlSessionFactory.openSession();
        flag = session.insert("addUserPhone",phone);
        int count = session.selectOne("selectCounts");
        if(count - phone.getPrice() * 10000 <= 0) { //���Ƿ�Խ���ˡ�
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
        int flag = 0; //���������±꣬û����Ϊ0
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
        int flag = 0; //���в�����û�������
        SqlSession session = sqlSessionFactory.openSession();
        flag = session.selectOne("selectCounts");
        session.commit();
        session.close();
        return flag;
    }

}
