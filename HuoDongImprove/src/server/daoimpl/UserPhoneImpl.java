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
     * �ڲ����ֻ�����֮ǰ���ȸ��ֻ�������ȡ�����־����0��49999Ψһ�ı�ţ�
     * ��ʱ���Ϊ��־��ÿ��ʱ���ֻ��һ����־��
     * ��ͬһ��ʱ����յ��������ʱ���������δ����ĵ�ǰ�����ʱ��㣨ÿһ��һ�����𣩡�
     * ��˲���������������ǰʱ���ı�־���Ѿ����ı�־������
     * �����ݴ���phonecount���У�
     * id=1ʱ����ǰʱ���ı�־��
     * id=2ʱ���ѷ���ı�־������
     * */
    @Override
    public int addPhone(UserPhone phone) {
        int flag = 0; //�Ƿ�ɹ�
        DataConnection dConn = new DataConnection();
        Connection conn = dConn.getConn();
        PreparedStatement pStat = null;
        
        try { 
            conn.setAutoCommit(false); //��������ķ�ʽʵ�����ݿⰲȫ
            String sql = "select count from phoneCount where id = 1"; //���Ȼ�ȡ��ǰʱ���ı�־
            Statement stat = conn.createStatement();  
            ResultSet rs = stat.executeQuery(sql);
            int count = 0;  //��count��ȡ����ֵ��˵����������Ƴ���������ʱ�������ü�������������ѡ��count��Ϊ��������         
            while(rs.next()) {
                count = rs.getInt(1);
            }
            
            /*
             * �����������жϵ�ǰʱ���ı�־�Ƿ��Ѿ����ù��ˣ���Ϊ����������������δ����ʱ���ı�־��
             * ����Ѿ��ù��˾ͽ�count+1��Ȼ������жϣ�ֱ��count�Ǹ�û���ù��ı�־��
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
             * ���������Ǳ�־������㷨��
             * �Ƚ���־����sign���������ʧ�ܣ���Ϊcount����count���������ֵ֮�����һ��count��
             * �ٲ���һ�������λ�ʧ�ܣ���˵�������ǲ������߷��ڣ����޸�����Դ�����û��ˡ�
             * �������ɹ�����ǰ��־�Ѿ��ù������ݿ��е�ǰʱ����־+1��id=1����
             * */
            sql = "insert into sign (phoneSign) values (" + count + ") "; 
            try {
                stat.executeUpdate(sql); 
                sql = "update phoneCount set count = count + 1 where id = 1"; //����ɹ������ݿ��е�ǰʱ����־+1.��id=1��
                stat.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
                int count2 = (int)(Math.random() * (phone.getPrice() * 10000 - count)) + count;
                sql = "insert into sign (phoneSign) values (" + count2 + ") "; //���������(count���������ֵ��x9999��]�������һ��count
                stat.executeUpdate(sql);
            }
            
            /*
             * �������������һ���׶Σ����ֻ�����ͼ۸����promotion��
             * ����ɹ������û������Ѿ�����ɹ��ˣ���������׶Σ��϶�������һ����־�������ǲ��ǵ�ǰʱ���ı�־���������������+1��id=2����
             * ��󻹻��ٶ�ȡ�ѷ����־��������id=2�������ǲ��ǳ�����ǰ�׶ε����ֵ����������ˣ��ͻع�������ͳɹ���.��󷵻�flag��
             * */
            sql = "insert into promotion (phone,price) values (?,?) "; //������ͼ۸����promotion��
            pStat = conn.prepareStatement(sql);
            pStat.setLong(1, phone.getPhone()); 
            pStat.setInt(2, phone.getPrice());
            flag = pStat.executeUpdate(); 
            sql = "update phoneCount set count = count + 1 where id = 2"; //��������+1��id=2��
            stat.executeUpdate(sql);
            sql = "select count from phoneCount where id = 2"; //��ȡ��ǰ���ı�־������
            ResultSet res = stat.executeQuery(sql);
            
            while(res.next()) {
                count = res.getInt(1);
            }
            if(stat != null){
                stat.close();
            }            
            if(count - phone.getPrice() * 10000 <= 0) { //���Ƿ�Խ���ˡ�
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
