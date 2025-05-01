package xyz.ziadboukhalkhal.servlet.classmanagement.dao;

import java.util.List;
import xyz.ziadboukhalkhal.servlet.classmanagement.service.model.Class;
public interface IClassDao {
    List<Class> findAll();
    Class findById(Long id);
    void create(Class cls);
    void update(Class cls);
    void delete(Long id);
    List<Class> search(String keyword,String teacher, String room);
}