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

    /*
     * 在插入号码之前，先给号码领取插入标志，（0～49999唯一的编号）
     * 以时间点为标志，每个时间点只有一个标志。
     * 在同一个时间点多个请求时，分配后面未分配的当前级别时间点（每一万一个级别）。
     * 因此产生两个变量：当前时间点的号码，总共已经给的号码。两数据存在phonecount表中
     * id=1时，当前时间点的号码。
     * id=2时，总共已经给的号码。
     * */
    @Override
    public int addPhone(UserPhone phone) {
        int flag = 0; //是否成功
        DataConnection dConn = new DataConnection();
        Connection conn = dConn.getConn();
        PreparedStatement pStat = null;
        
        try { 
            conn.setAutoCommit(false); //用事务处理的方式实现数据库安全
            String sql = "select count from phoneCount where id = 1"; //首先获取当前时间点的号码
            Statement stat = conn.createStatement();  
            ResultSet rs = stat.executeQuery(sql);
            int count = 0;  //用count存取出的值          
            while(rs.next()) {
                count = rs.getInt(1);
            }
            
            /*
             * 这个代码块是判断当前时间点的号码count是否已经被用过了，因为并发会用到后面未到的时间点的号码。
             * 如果已经用过了就将count+1，然后继续判断，直到count是个没有用过的时间点的号码。
             * */
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
            
            /*
             * 这个代码块是标志插入的算法。
             * 先将标志插入sign表，如果插入失败，则为count赋在count～本级最大值之间随机一个count。
             * 再插入一次如果这次还失败，则说明现在是并发量高峰期，已无更多资源给该用户了。
             * 如果插入成功，则当前号码（标志）已经用过，数据库中当前时间点号码+1。
             * */
            sql = "insert into sign (phoneSign) values (" + count + ") "; 
            try {
                stat.executeUpdate(sql); 
                sql = "update phoneCount set count = count + 1 where id = 1"; //插入成功，数据库中当前时间点号码+1.（id=1）
                stat.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
                int count2 = (int)(Math.random() * (phone.getPrice() * 10000 - count)) + count;
                sql = "insert into sign (phoneSign) values (" + count2 + ") "; //这句代码就是实现在count～本级最大值之间随机一个count的
                stat.executeUpdate(sql);
            }
            
            /*
             * 这个代码块是最后一个阶段，将号码和价格插入promotion表。
             * 如果成功，该用户基本已经购买成功了，到了这个阶段，肯定消耗了一个标志（不论是不是当前时间点的标志），因此消耗总数+1（id=2）。
             * 最后还会再读取消耗标志的总数，看是不是超过当前阶段的最大值，如果超过了，就回滚，否则就成功了，返回flag。
             * */
            sql = "insert into promotion (phone,price) values (?,?) "; //将号码和价格插入promotion表
            pStat = conn.prepareStatement(sql);
            pStat.setLong(1, phone.getPhone()); 
            pStat.setInt(2, phone.getPrice());
            flag = pStat.executeUpdate(); 
            sql = "update phoneCount set count = count + 1 where id = 2"; //消耗总数+1（id=2）
            stat.executeUpdate(sql);
            sql = "select count from phoneCount where id = 2"; //读取当前消耗标志的总数
            ResultSet res = stat.executeQuery(sql);
            
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
