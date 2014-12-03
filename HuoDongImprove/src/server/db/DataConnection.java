package server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataConnection {
    static Connection conn = null;
    PreparedStatement pStat = null;
    
    public Connection getConn() { //�������ݿ�
        try{
            String url = "jdbc:mysql://localhost/huodong";
            String user = "root";
            String pwd = "a12345";
            
            Class.forName("com.mysql.jdbc.Driver");
            
            try {
                conn = DriverManager.getConnection(url, user, pwd);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        catch(ClassNotFoundException e){
            System.out.println("����δ����");
        }
        return conn;
    }
    
    public void lockDataBase(Connection conn) { //�����ݿ����
        try {
            String sql = "LOCK TABLES promotion WRITE";
            
            pStat = conn.prepareStatement(sql);
            pStat.execute(sql);
        } catch (SQLException e) {
           e.printStackTrace();
        } finally{
            try {
                pStat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void unLockDataBase(Connection conn) {
        try {
            String sql = "UNLOCK TABLES";
            
            pStat = getConn().prepareStatement(sql);
            pStat.execute(sql);
        } catch (SQLException e) {
           e.printStackTrace();
        } finally{
            try {
                pStat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
