package server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataConnection {
    static Connection conn = null;
    
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
}
