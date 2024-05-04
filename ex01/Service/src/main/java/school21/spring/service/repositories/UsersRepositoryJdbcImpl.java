package school21.spring.service.repositories;

import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UsersRepository{
    private DataSource dataSource;
    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<User> user = Optional.empty();
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE email = ?");
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = Optional.of(new User(resultSet.getLong(1),resultSet.getString(2)));
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        Optional<User> user = Optional.empty();
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM models.users WHERE id = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                user = Optional.of(new User(resultSet.getLong(1), resultSet.getString(2)));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM models.users");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                User user = new User(resultSet.getLong(1), resultSet.getString(2));
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return users.isEmpty() ? null : users;
    }

    @Override
    public void save(User entity) {
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO models.users (email, password) VALUES (?, ?)");
            preparedStatement.setLong(1, entity.getId());
            preparedStatement.setString(2, entity.getEmail());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void update(User entity) {
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE models.users SET email = ? WHERE id = ?");
            preparedStatement.setString(1, entity.getEmail());
            preparedStatement.setLong(2, entity.getId());
            Integer res = preparedStatement.executeUpdate();
            if (res == 0) {
                System.err.println("User with id = " + entity.getId() + " not found");
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM models.users WHERE id = ?");
            preparedStatement.setLong(1, id);
            Integer res = preparedStatement.executeUpdate();
            if(res == 0) {
                System.err.println("User with id = " + id + " not found");
            }
        }catch(SQLException e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
