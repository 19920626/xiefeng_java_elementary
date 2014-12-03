package server.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import server.dao.UserPhoneDao;
import server.db.DataConnection;
import server.entity.UserPhone;

public class UserPhoneImpl implements UserPhoneDao {

    @Override
    public long addPhone(UserPhone phone) {
        int flag = 0; //是否成功
        DataConnection dConn = new DataConnection();
        Connection conn = dConn.getConn();
        PreparedStatement pStat = null;
        
        try { 
            dConn.lockDataBase(conn);
            String sql = "insert into promotion (phone) values (?) ";
            pStat = conn.prepareStatement(sql);
            pStat.setLong(1, phone.getPhone()); 
            flag = pStat.executeUpdate();
            dConn.unLockDataBase(conn);
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                pStat.close();
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
            dConn.lockDataBase(conn);
            String sql = "select id from promotion where phone = " + phone.getPhone() + "";
            pStat = conn.prepareStatement(sql);
            ResultSet rs = pStat.executeQuery();
            
            while(rs.next()){
                flag = rs.getInt(1);
            }
            dConn.unLockDataBase(conn);
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                pStat.close();
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
            dConn.lockDataBase(conn);
            String sql = "select count(*) from promotion";
            pStat = conn.prepareStatement(sql);
            ResultSet rs = pStat.executeQuery();
            
            while(rs.next()){
                flag = rs.getInt(1);
            }
                
            dConn.unLockDataBase(conn);
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                pStat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

}
