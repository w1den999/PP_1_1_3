package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    PreparedStatement statement;
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection()) {
            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS users" +
                    "(id BIGINT NOT NULL AUTO_INCREMENT, " +
                    "name VARCHAR(50) NOT NULL, " +
                    "lastname VARCHAR(50) NOT NULL, " +
                    "age TINYINT NOT NULL, " +
                    "PRIMARY KEY (id))");
            statement.executeUpdate();
            System.out.println("������� �������.");
        } catch (SQLException e) {
            System.out.println("������� �� ������� ��� ��� ����������.");
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection()) {
            statement = connection.prepareStatement("DROP TABLE IF EXISTS `dbtest`.`users`;");
            statement.executeUpdate();
            System.out.println("������� �������.");
        } catch (SQLException e) {
            System.out.println("������� �� ������� ��� ����� ������� �� ����������.");
        }
    }

    public void saveUser(String name, String lastname, byte age) {
        try (Connection connection = Util.getConnection()) {
            statement = connection.prepareStatement("INSERT INTO users (name, lastname, age) VALUES (?, ?, ?);");
            statement.setString(1, name);
            statement.setString(2, lastname);
            statement.setByte(3, age);
            statement.executeUpdate();
            System.out.println("User � ������ - " + name + " �������� � ���� ������.");
        } catch (SQLException e) {
            System.out.println("������������ �� �������� � �������.");
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection()) {
            statement = connection.prepareStatement("DELETE FROM users WHERE id = ?");
            statement.setLong(1, id);
            statement.executeUpdate();
            System.out.println("������������ �� id = " + id + " ������ �� �������.");
        } catch (SQLException e) {
            System.out.println("������ ��� �������� ������������ ��� ������������ � ����� id �� ����������.");
        }
    }
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Connection connection = Util.getConnection()) {
            statement = connection.prepareStatement("SELECT * FROM users");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                User user = new User();
                user.setId(result.getLong("id"));
                user.setName(result.getString("name"));
                user.setLastName(result.getString("lastname"));
                user.setAge(result.getByte("age"));
                list.add(user);
            }
        } catch (SQLException ignored) {}
        System.out.println(list);
        return list;
    }


    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection()) {
            statement = connection.prepareStatement("TRUNCATE users");
            statement.executeUpdate();
            System.out.println("������� ������� �������.");
        } catch (SQLException e) {
            System.out.println("������� �� �������.");
        }
    }
}
