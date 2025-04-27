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

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    cls.setId(generatedKeys.getLong(1));
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
    public List<Class> search(String keyword) {
        List<Class> classes = new ArrayList<>();
        try (Connection connection = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(
                     "SELECT * FROM class WHERE name LIKE ? OR description LIKE ? OR teacher LIKE ?")) {

            String searchPattern = "%" + keyword + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);

            try (ResultSet rs = stmt.executeQuery()) {
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