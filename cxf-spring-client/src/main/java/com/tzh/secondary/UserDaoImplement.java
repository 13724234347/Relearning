package com.tzh.secondary;

import com.tzh.entity.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImplement implements UserDao {
    @Override
    public List<User> queryUser() {

        User user = new User();
        user.setId(1);
        user.setName("刘聪");
        user.setSex("男");
        user.setAge(20);
        user.setAddr("衡阳");
        List<User> userList = new ArrayList<>();
        userList.add(user);
        return userList;
    }
}
