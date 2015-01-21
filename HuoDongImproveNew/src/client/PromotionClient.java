package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import server.entity.UserPhone;
/*
 * @author 谢峰
 * 客户端程序
 * */
public class PromotionClient extends JApplet implements Serializable{
    private static final long serialVersionUID = -3372145837685367103L;
    private JTextField jtfPhone = new JTextField(); //号码输入框
    private JTextField jtfPrice = new JTextField(); //价格输入框
    private JButton jbtSubmit = new JButton("Submit!"); //提交按钮
    private JTextArea jtaShow = new JTextArea(); //显示返回结果
    
    private boolean isStandAlone = false; //表明是否是以application运行
    
    String host = "127.0.0.1";
    
    public void init() { //初始化程序，显示出客户端界面
        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(2, 1));
        p1.add(new JLabel("phone number:"));
        p1.add(new JLabel("price:"));
        
        JPanel p2 = new JPanel();
        p2.setLayout(new GridLayout(2, 1));
        jtfPhone.setPreferredSize(new Dimension(100,10));
        jtfPrice.setPreferredSize(new Dimension(100,10));
        p2.add(jtfPhone);
        p2.add(jtfPrice);
        
        JPanel p3 = new JPanel();
        p3.setLayout(new BorderLayout());
        p3.add(p1, BorderLayout.WEST);
        p3.add(p2, BorderLayout.EAST);
        
        JPanel p4 = new JPanel();
        p4.setLayout(new BorderLayout());
        p4.add(p3, BorderLayout.NORTH);
        p4.add(jbtSubmit, BorderLayout.CENTER);
        
        jtaShow.setPreferredSize(new Dimension(100,40));
        add(p4, BorderLayout.CENTER);
        add(jtaShow, BorderLayout.SOUTH);
        
        jbtSubmit.addActionListener(new ButtonListener());
        
        if(!isStandAlone){
            host = getCodeBase().getHost();
        }
    }
    
    /*
     * 添加监听，每次发生action事件的时候就会触发执行
     * */
    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Socket socket = new Socket(host, 8001);
                
                ObjectOutputStream toServer = 
                    new ObjectOutputStream(socket.getOutputStream());
                
                long phone = Long.parseLong(jtfPhone.getText().trim());
                int price = Integer.parseInt(jtfPrice.getText().trim());
                
                UserPhone userPhone = new UserPhone(phone, price);
                
                toServer.writeObject(userPhone); //将对象发送给服务器
                toServer.flush();
                
                DataInputStream fromServer = 
                    new DataInputStream(socket.getInputStream());
                int state = fromServer.readInt();
                
                Handle handle = new Handle();
                String consequence = handle.dealWith(state, phone);
                jtaShow.append(consequence); //将结果在客户端显示
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }        
    }
    
    public static void main(String[] args){
        JFrame frame = new JFrame("Promotion Client");
        
        PromotionClient applet = new PromotionClient();
        applet.isStandAlone = true;
        
        if(args.length == 1) applet.host = args[0];
        
        frame.add(applet, BorderLayout.CENTER);
        
        applet.init();
        applet.start();
        
        frame.setBounds(200, 100, 200, 150);
        
        frame.setVisible(true);
    }
}
