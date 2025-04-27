package xyz.ziadboukhalkhal.servlet.classmanagement.service;

import xyz.ziadboukhalkhal.servlet.classmanagement.service.model.Class;

import java.util.List;

public interface IService {
    Boolean checkAccount(String username, String password);
    List<xyz.ziadboukhalkhal.servlet.classmanagement.service.model.Class> getAllClasses();
    xyz.ziadboukhalkhal.servlet.classmanagement.service.model.Class getClassById(Long id);
    void createClass(xyz.ziadboukhalkhal.servlet.classmanagement.service.model.Class cls);
    void updateClass(xyz.ziadboukhalkhal.servlet.classmanagement.service.model.Class cls);
    void deleteClass(Long id);
    List<Class> searchClasses(String keyword);
}