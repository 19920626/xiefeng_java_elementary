package server.dao;

import server.entity.UserPhone;

public interface UserPhoneDao {
    public long addPhone(UserPhone phone); //插入一个用户的号码
    public int findUser(UserPhone phone);  //找到相应的用户
    public int countAll(); //统计所有参与用户
}
