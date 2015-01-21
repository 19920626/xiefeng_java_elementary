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

            /*
             *（数据库表结构参照huodongimprove_new.sql文档注释）
             * 将手机号码和价格插入promotion表。
             * 将数据总量count+1，
             * 读取数据总量count，如果总量越界，则回滚，否则提交
             * */
            String sql = "insert into promotion (phone,price) values (?,?) "; //将号码和价格插入promotion表
            pStat = conn.prepareStatement(sql);
            pStat.setLong(1, phone.getPhone()); 
            pStat.setInt(2, phone.getPrice());
            flag = pStat.executeUpdate(); 
            
            sql = "update summation set count = count + 1"; //数据总量+1
            Statement stat = conn.createStatement();
            stat.executeUpdate(sql);
            sql = "select count from summation"; //读取数据总量
            ResultSet res = stat.executeQuery(sql);
            
            int count = 0;
            while(res.next()) {
                count = res.getInt(1);
            }
            if(stat != null){
                stat.close();
            }            
            if(count - phone.getPrice() * 10000 <= 0) { //看是否越界了。
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
            String sql = "select count from summation";
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
