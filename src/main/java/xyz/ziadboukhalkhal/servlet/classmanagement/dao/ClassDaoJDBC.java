package xyz.ziadboukhalkhal.servlet.classmanagement.dao;
import xyz.ziadboukhalkhal.servlet.classmanagement.service.model.Class;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClassDaoJDBC implements IClassDao {

    @Override
    public List<Class> findAll() {
        List<Class> classes = new ArrayList<>();
        try (Connection connection = DatabaseManager.getInstance().getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM class")) {

            while (rs.next()) {
                classes.add(new Class(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("schedule"),
                        rs.getString("teacher"),
                        rs.getString("room")
                ));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return classes;
    }

    @Override
    public Class findById(Long id) {
        Class cls = null;
        try (Connection connection = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM class WHERE id=?")) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cls = new Class(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getString("schedule"),
                            rs.getString("teacher"),
                            rs.getString("room")
                    );
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return cls;
    }

    @Override
    public void create(Class cls) {
        try (Connection connection = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(
                     "INSERT INTO class(name, description, schedule, teacher, room) VALUES(?,?,?,?,?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cls.getName());
            stmt.setString(2, cls.getDescription());
            stmt.setString(3, cls.getSchedule());
            stmt.setString(4, cls.getTeacher());
            stmt.setString(5, cls.getRoom());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        cls.setId(generatedKeys.getLong(1));
                    }
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    @Override
    public void update(Class cls) {
        try (Connection connection = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(
                     "UPDATE class SET name=?, description=?, schedule=?, teacher=?, room=? WHERE id=?")) {

            stmt.setString(1, cls.getName());
            stmt.setString(2, cls.getDescription());
            stmt.setString(3, cls.getSchedule());
            stmt.setString(4, cls.getTeacher());
            stmt.setString(5, cls.getRoom());
            stmt.setLong(6, cls.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
            throw new RuntimeException("Error updating class with ID: " + cls.getId(), e);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement("DELETE FROM class WHERE id=?")) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    @Override
    public List<Class> search(String keyword, String teacher, String room) {
        List<Class> classes = new ArrayList<>();
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            StringBuilder sql = new StringBuilder("SELECT * FROM class WHERE 1=1");
            List<Object> params = new ArrayList<>();

            if (keyword != null && !keyword.isEmpty()) {
                sql.append(" AND (name LIKE ? OR description LIKE ?)");
                params.add("%" + keyword + "%");
                params.add("%" + keyword + "%");
            }
            if (teacher != null && !teacher.isEmpty()) {
                sql.append(" AND teacher LIKE ?");
                params.add("%" + teacher + "%");
            }
            if (room != null && !room.isEmpty()) {
                sql.append(" AND room LIKE ?");
                params.add("%" + room + "%");
            }

            PreparedStatement stmt = connection.prepareStatement(sql.toString());
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                classes.add(new Class(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("schedule"),
                        rs.getString("teacher"),
                        rs.getString("room")
                ));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return classes;
    }
    private void handleSQLException(SQLException e) {
        System.out.println("SQLException: " + e.getMessage());
        System.out.println("SQLState: " + e.getSQLState());
        System.out.println("VendorError: " + e.getErrorCode());
    }
}