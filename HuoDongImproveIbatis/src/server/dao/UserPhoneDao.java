package server.dao;

import server.entity.UserPhone;

public interface UserPhoneDao {
    public int addPhone(UserPhone phone); //����һ���û��ĺ���
    public int findUser(UserPhone phone);  //�ҵ���Ӧ���û�
    public int countAll(); //ͳ�����в����û�
}
