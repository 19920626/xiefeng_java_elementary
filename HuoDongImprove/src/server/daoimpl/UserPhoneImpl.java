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
        int flag = 0; //是否成功
        DataConnection dConn = new DataConnection();
        Connection conn = dConn.getConn();
        PreparedStatement pStat = null;
        
        try { 
            conn.setAutoCommit(false); //用事务处理的方式实现数据库安全
            String sql = "select count(*) from promotion";
            Statement stat = conn.createStatement();
            
            ResultSet rs = stat.executeQuery(sql);
            int count = 0;
            
            while(rs.next()) {
                count = rs.getInt(1);
            }
            sql = "select count(*) from sign where phoneSign < " + count + ""; //确定“真”的count
            ResultSet re = stat.executeQuery(sql);
            while(re.next()) {
                count = re.getInt(1);
            }
            
            sql = "insert into sign (phoneSign) values (" + count + ") ";
            try {
                stat.executeUpdate(sql); 
                //如果插入失败，则为count赋在count～本级最大值之间随机一个count，再插入一次，如果这次还失败，
                //则说明现在是并发量高峰期，已无更多资源给该用户了
            } catch (SQLException e) {
                e.printStackTrace();
                int count2 = (int)(Math.random() * (phone.getPrice() * 10000 - count)) + count;
                sql = "insert into sign (phoneSign) values (" + count2 + ") ";
                stat.executeUpdate(sql);
            }
            
            sql = "insert into promotion (phone) values (?) ";
            pStat = conn.prepareStatement(sql);
            pStat.setLong(1, phone.getPhone()); 
            flag = pStat.executeUpdate();         
            conn.commit();  
            
            if(stat != null){
                stat.close();
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
        int flag = 0; //所查对象的下标，没有则为-1
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
        int flag = 0; //所有参与的用户的总量
        DataConnection dConn = new DataConnection();
        Connection conn = dConn.getConn();
        PreparedStatement pStat = null;
        
        try { 
            conn.setAutoCommit(false);
            String sql = "select count(*) from promotion";
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
