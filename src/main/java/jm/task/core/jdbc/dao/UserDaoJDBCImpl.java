package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
    private static final String DB_NAME = "test_jdbc";

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Connection connection = connect(DB_NAME);
             Statement statement = connection.createStatement()) {

            String SQL = "CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT not NULL AUTO_INCREMENT, " +
                    " name VARCHAR (20) not NULL, " +
                    " lastname VARCHAR (30) not NULL, " +
                    " age TINYINT not NULL, " +
                    " PRIMARY KEY (id))";

            statement.execute(SQL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = connect(DB_NAME);
             Statement statement = connection.createStatement()) {

            String SQL = "DROP TABLE IF EXISTS users ";
            statement.execute(SQL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String SQL = "INSERT INTO users (name, lastname, age) VALUES (?, ?, ?)";

        try (Connection connection = connect(DB_NAME);
             PreparedStatement ps = connection.prepareStatement(SQL)) {

            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String SQL = "DELETE FROM users WHERE id = ? ";

        try (Connection connection = connect(DB_NAME);
             PreparedStatement ps = connection.prepareStatement(SQL)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Connection connection = connect(DB_NAME);
             Statement statement = connection.createStatement()) {

            String SQL = "SELECT * FROM users";
            statement.executeQuery(SQL);

            try (ResultSet rs = statement.getResultSet()) {
                while (rs.next()) {
                    users.add(new User(rs.getString("name"), rs.getString("lastname"), rs.getByte(4)));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return users;
    }

    public void cleanUsersTable() {
        try (Connection connection = connect(DB_NAME);
             Statement statement = connection.createStatement()) {

            String SQL = "TRUNCATE TABLE users ";
            statement.execute(SQL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
