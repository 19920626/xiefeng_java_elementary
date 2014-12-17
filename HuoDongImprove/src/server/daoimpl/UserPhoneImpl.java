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
            //确定“真”的count标志(由于多并发时的随机分配标志，单纯的count是插入的所有数据个数，而不是依据逻辑执行次数来看的)
            String sql = "select count from phoneCount where id = 1";
            Statement stat = conn.createStatement();  
            ResultSet rs = stat.executeQuery(sql);
            int count = 0; //count是标志的值。（顺序插入的次数）            
            while(rs.next()) {
                count = rs.getInt(1);
            }
            
            //减少后面随机产生的标志对顺序插入标志的影响
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
            
            sql = "insert into sign (phoneSign) values (" + count + ") "; //将标志插入sign表
            try {
                stat.executeUpdate(sql); 
                //如果插入失败，则为count赋在count～本级最大值之间随机一个count，再插入一次，
                //如果这次还失败，则说明现在是并发量高峰期，已无更多资源给该用户了
                sql = "update phoneCount set count = count + 1 where id = 1";
                stat.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
                int count2 = (int)(Math.random() * (phone.getPrice() * 10000 - count)) + count;
                sql = "insert into sign (phoneSign) values (" + count2 + ") ";
                stat.executeUpdate(sql);
            }
            
            sql = "insert into promotion (phone) values (?) "; //确认安全，在promotion表中插入号码
            pStat = conn.prepareStatement(sql);
            pStat.setLong(1, phone.getPhone()); 
            flag = pStat.executeUpdate(); 
            sql = "update phoneCount set count = count + 1 where id = 2"; 
            //插入成功后，phoneCount里的总数应该加一
            stat.executeUpdate(sql);
            sql = "select count from phoneCount where id = 2"; //重新获取已经插入的号码总数
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
        int flag = 0; //所查对象的下标，没有则为0
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
