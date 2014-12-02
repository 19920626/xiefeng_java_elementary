package server.ser;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

import server.entity.UserPhone;

/*
 * @author л��
 * �������˳���
 * */
@SuppressWarnings("serial")
public class PromotionServer implements Serializable {

    public static void main(String[] args) throws IOException {
        new PromotionServer();
    }  
    
    public PromotionServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8001);
        System.out.println("Server started!");
        
        while(true) {
            Socket socket = serverSocket.accept();
            
            HandleClient task = new HandleClient(socket);//ʵ�ֶ��߳�
            new Thread(task).start();
        }
    }
}

class HandleClient implements Runnable {
    private Socket socket;
    
    public HandleClient(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream inputFromClient = 
                new ObjectInputStream(socket.getInputStream());
            UserPhone user = new UserPhone();
            user = (UserPhone) inputFromClient.readObject(); //�ӿͻ��˻�ȡ����
            
            int state = Promotion.doPurchase(user.getPhone(), user.getPrice());
            DataOutputStream outputToClient = 
                new DataOutputStream(socket.getOutputStream());
            outputToClient.writeInt(state); //��״̬�뷵�ظ��ͻ���
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }  
    }
}
