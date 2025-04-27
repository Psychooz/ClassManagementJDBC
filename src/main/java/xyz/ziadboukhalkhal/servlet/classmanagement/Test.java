package xyz.ziadboukhalkhal.servlet.classmanagement;

import xyz.ziadboukhalkhal.servlet.classmanagement.dao.DatabaseManager;
import xyz.ziadboukhalkhal.servlet.classmanagement.service.model.User;
import xyz.ziadboukhalkhal.servlet.classmanagement.service.model.Class;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.*;

public class Test {
    public static void main(String[] args) {
        initTableUsers(Arrays.asList(
                new User(1L, "admin", "admin", "ADMIN"),
                new User(2L, "teacher1", "teacher1", "TEACHER"),
                new User(3L, "teacher2", "teacher2", "TEACHER")
        ));

        initTableClasses(asList(
                new Class(1L, "Mathematics", "Basic algebra and geometry", "Mon/Wed 10:00-11:30", "Dr. Smith", "Room 101"),
                new Class(2L, "Physics", "Introduction to mechanics", "Tue/Thu 14:00-15:30", "Dr. Johnson", "Room 202"),
                new Class(3L, "Chemistry", "Organic chemistry basics", "Fri 09:00-12:00", "Dr. Williams", "Lab 3")
        ));
    }
    private static void initTableUsers(List<User> users) {
        try {
            Connection connection=DatabaseManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            Statement statment = connection.createStatement();
            users.forEach(user -> {
                try {
                    statment.addBatch("insert into user(id,username,password) values('" + user.getId() + "','"
                            + user.getUsername() + "','" + user.getPassword() + "')");
                } catch (SQLException e) {
                    handleSQLException(e);
                }
            });
            statment.executeBatch();
            connection.commit();
            connection.close();
            System.out.println("la liste des utilisateurs a été créee avec succés");
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }   private static void initTableClasses(List<Class> classes) {
        try (Connection connection = DatabaseManager.getInstance().getConnection()) {
            connection.setAutoCommit(false);

            // Clear existing data
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate("DELETE FROM class");
            }

            // Insert new data
            try (Statement stmt = connection.createStatement()) {
                for (Class cls : classes) {
                    String sql = String.format(
                            "INSERT INTO class(id, name, description, schedule, teacher, room) " +
                                    "VALUES(%d, '%s', '%s', '%s', '%s', '%s')",
                            cls.getId(),
                            escapeSql(cls.getName()),
                            escapeSql(cls.getDescription()),
                            escapeSql(cls.getSchedule()),
                            escapeSql(cls.getTeacher()),
                            escapeSql(cls.getRoom())
                    );
                    stmt.addBatch(sql);
                }
                stmt.executeBatch();
            }

            connection.commit();
            System.out.println("Class table initialized successfully with " + classes.size() + " records.");
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }
    private static String escapeSql(String input) {
        if (input == null) {
            return "";
        }
        return input.replace("'", "''");
    }

    private static void handleSQLException(SQLException e) {
        System.err.println("SQL Error:");
        System.err.println("Message: " + e.getMessage());
        System.err.println("SQL State: " + e.getSQLState());
        System.err.println("Error Code: " + e.getErrorCode());
        e.printStackTrace();
    }

}
