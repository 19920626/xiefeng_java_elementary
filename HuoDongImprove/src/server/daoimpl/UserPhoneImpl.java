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
            conn.setAutoCommit(false); //���������ķ�ʽʵ�����ݿⰲȫ
            String sql = "select id from promotion where phone = " + phone.getPhone() + "";
            Statement stat = conn.createStatement();
            ResultSet re = stat.executeQuery(sql);
            int idNum = 0;
            
            while(re.next()) {
                idNum = re.getInt(1);
            }
            
            sql = "insert into promotion (phone) values (?) ";
            pStat = conn.prepareStatement(sql);
            pStat.setLong(1, phone.getPhone()); 
            flag = pStat.executeUpdate();
            
            sql = "select count(*) from promotion";
            
            ResultSet rs = pStat.executeQuery(sql);
            int count = 0;
            
            while(rs.next()) {
                count = rs.getInt(1);
            }

            if(count - phone.getPrice() * 10 < 0 && idNum <= 0 ) { 
                //�ٴμ�������ͺ��롣���С�ڵ��λ��������û�в���������ύ
                conn.commit();
            } else { //�������ع� 
                conn.rollback();
                flag = 0;
            }   
            
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
        int flag = 0; //���������±꣬û����Ϊ-1
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