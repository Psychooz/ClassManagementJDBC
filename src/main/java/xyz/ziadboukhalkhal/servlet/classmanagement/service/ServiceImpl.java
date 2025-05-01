package xyz.ziadboukhalkhal.servlet.classmanagement.service;

import xyz.ziadboukhalkhal.servlet.classmanagement.dao.ClassDaoJDBC;
import xyz.ziadboukhalkhal.servlet.classmanagement.dao.IClassDao;
import xyz.ziadboukhalkhal.servlet.classmanagement.dao.IUserDao;
import xyz.ziadboukhalkhal.servlet.classmanagement.dao.UserDaoJDBC;
import xyz.ziadboukhalkhal.servlet.classmanagement.service.model.Class;
import xyz.ziadboukhalkhal.servlet.classmanagement.service.model.User;

import java.util.List;

public class ServiceImpl implements IService {
    private IUserDao dao = new UserDaoJDBC();
    private IClassDao classDao = new ClassDaoJDBC();
    @Override
    public Boolean checkAccount(String username, String password) {
        User u = dao.getUserByUsername(username);
        if (u == null) return false;
        return password.equals(u.getPassword());
    }

    @Override
    public List<Class> getAllClasses() {
        return classDao.findAll();
    }

    @Override
    public Class getClassById(Long id) {
        return classDao.findById(id);
    }

    @Override
    public void createClass(Class cls) {
        classDao.create(cls);
    }

    @Override
    public void updateClass(Class cls) {
        classDao.update(cls);
    }

    @Override
    public void deleteClass(Long id) {
        classDao.delete(id);
    }

    @Override
    public List<Class> searchClasses(String keyword, String teacher, String room) {
        // Implement multi-criteria search
        if ((keyword == null || keyword.isEmpty()) &&
                (teacher == null || teacher.isEmpty()) &&
                (room == null || room.isEmpty())) {
            return classDao.findAll();
        }

        return classDao.search(keyword, teacher, room);
    }
}
