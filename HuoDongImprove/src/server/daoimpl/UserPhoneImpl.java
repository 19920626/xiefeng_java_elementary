package server.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import server.dao.UserPhoneDao;
import server.db.DataConnection;
import server.entity.UserPhone;

public class UserPhoneImpl implements UserPhoneDao {

    @Override
    public int addPhone(UserPhone phone) {
        int flag = 0; //�Ƿ�ɹ�
        DataConnection dConn = new DataConnection();
        Connection conn = dConn.getConn();
        PreparedStatement pStat = null;
        
        try { 
            conn.setAutoCommit(false); //��������ķ�ʽʵ�����ݿⰲȫ
            //ȷ�����桱��count��־(���ڶಢ��ʱ����������־��������count�ǲ�����������ݸ����������������߼�ִ�д���������)
            String sql = "select count from phoneCount where id = 1";
            Statement stat = conn.createStatement();  
            ResultSet rs = stat.executeQuery(sql);
            int count = 0; //count�Ǳ�־��ֵ����˳�����Ĵ�����            
            while(rs.next()) {
                count = rs.getInt(1);
            }
            
            //���ٺ�����������ı�־��˳������־��Ӱ��
            sql = "select id from sign where phoneSign = " + count + "";
            ResultSet re = stat.executeQuery(sql);
            int contained = 0;
            while(re.next()) {
                contained = re.getInt(1);
            }
            while(contained > 0) { 
                count ++;
                contained = 0;
                sql = "select id from sign where phoneSign = " + count + "";
                re = stat.executeQuery(sql);
                while(re.next()) {
                    contained = re.getInt(1);
                }
            }
            
            sql = "insert into sign (phoneSign) values (" + count + ") "; //����־����sign��
            try {
                stat.executeUpdate(sql); 
                //�������ʧ�ܣ���Ϊcount����count���������ֵ֮�����һ��count���ٲ���һ�Σ�
                //�����λ�ʧ�ܣ���˵�������ǲ������߷��ڣ����޸�����Դ�����û���
                sql = "update phoneCount set count = count + 1 where id = 1";
                stat.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
                int count2 = (int)(Math.random() * (phone.getPrice() * 10000 - count)) + count;
                sql = "insert into sign (phoneSign) values (" + count2 + ") ";
                stat.executeUpdate(sql);
            }
            
            sql = "insert into promotion (phone) values (?) "; //ȷ�ϰ�ȫ����promotion���в������
            pStat = conn.prepareStatement(sql);
            pStat.setLong(1, phone.getPhone()); 
            flag = pStat.executeUpdate(); 
            sql = "update phoneCount set count = count + 1 where id = 2"; 
            //����ɹ���phoneCount�������Ӧ�ü�һ
            stat.executeUpdate(sql);
            sql = "select count from phoneCount where id = 2"; //���»�ȡ�Ѿ�����ĺ�������
            ResultSet res = stat.executeQuery(sql);
            
            while(res.next()) {
                count = res.getInt(1);
            }
            if(stat != null){
                stat.close();
            }
            
            if(count - phone.getPrice() * 10000 <= 0) {
                conn.commit();
            } else {
                flag = 0;
                conn.rollback();
            }  
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally{
            try {
                pStat.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    @Override
    public int findUser(UserPhone phone) {
        int flag = 0; //���������±꣬û����Ϊ0
        DataConnection dConn = new DataConnection();
        Connection conn = dConn.getConn();
        PreparedStatement pStat = null;
        
        try {
            conn.setAutoCommit(false);
            String sql = "select id from promotion where phone = " + phone.getPhone() + "";
            pStat = conn.prepareStatement(sql);
            ResultSet rs = pStat.executeQuery();
            
            while(rs.next()) {
                flag = rs.getInt(1);
            }
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally{
            try {
                pStat.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    @Override
    public int countAll() {
        int flag = 0; //���в�����û�������
        DataConnection dConn = new DataConnection();
        Connection conn = dConn.getConn();
        PreparedStatement pStat = null;
        
        try { 
            conn.setAutoCommit(false);
            String sql = "select count from phoneCount where id = 2";
            pStat = conn.prepareStatement(sql);
            ResultSet rs = pStat.executeQuery();
            
            while(rs.next()) {
                flag = rs.getInt(1);
            }
                
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally{
            try {
                pStat.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

}
