package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }
    private static final Connection connection = Util.getConnection();
    public void createUsersTable() {
        try (Statement statement = connection.createStatement()){

            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS `new_schema`.`user` (
                      `id` INT NOT NULL AUTO_INCREMENT,
                      `name` VARCHAR(45) NOT NULL,
                      `lastname` VARCHAR(45) NOT NULL,
                      `age` INT(3) NOT NULL,
                      PRIMARY KEY (`id`))
                    ENGINE = InnoDB
                    DEFAULT CHARACTER SET = utf8;""");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate("DROP TABLE IF EXISTS `new_schema`.`user`");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        final String form = "INSERT INTO `new_schema`.`user`(name, lastname, age) VALUES (?, ?, ?)";
//        final String sql = String.format(form, name, lastName, age);
        try (PreparedStatement preparedStatement = connection.prepareStatement(form)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `new_schema`.`user` where id")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT id, name, lastName, age from `new_schema`.`user`");

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate("TRUNCATE `new_schema`.`user`;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
