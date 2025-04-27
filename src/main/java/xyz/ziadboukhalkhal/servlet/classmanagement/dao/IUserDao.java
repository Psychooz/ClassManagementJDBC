package xyz.ziadboukhalkhal.servlet.classmanagement.dao;

import xyz.ziadboukhalkhal.servlet.classmanagement.service.model.User;

import java.util.List;

public interface IUserDao {
    List<User> findAllUsers();
    User getUserByUsername(String username);
}
