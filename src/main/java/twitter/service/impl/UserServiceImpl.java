package twitter.service.impl;

import twitter.configuration.Component;
import twitter.configuration.Injection;
import twitter.dao.UserDAO;
import twitter.entity.User;
import twitter.exception.UnknowUserTypeException;
import twitter.exception.UserNotFoundException;
import twitter.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Injection
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public boolean isUserExist(String login) {
        try {
            User user = this.getUserByLogin(login);
            return Objects.nonNull(user);  // Проверка на null
        } catch (UserNotFoundException e) {
            return false;  // Если пользователь не найден, возвращаем false
        }
    }

    @Override
    public User creatUser(User user) {
        if (isUserExist(user.getLogin())) {
            throw new IllegalArgumentException("User with this login already exists.");
        }
        return userDAO.saveNewUser(user);
    }

    @Override
    public User getUserByLogin(String login) throws UserNotFoundException {
        return userDAO.getByLogin(login);
    }

    @Override
    public User getUserById(int id) throws UserNotFoundException {
        return userDAO.getById(id);
    }

    @Override
    public User[] getAllUsers() {
        return userDAO.getAllUsers();  // Возвращаем массив пользователей
    }

    @Override
    public User[] getUsersByType(int userType) throws UnknowUserTypeException {
        return userDAO.getAllUsersByType(userType);  // Возвращаем массив пользователей по типу
    }

    @Override
    public User[] createSeveralUsers(List<User> users) {
        if (Objects.isNull(users) || users.isEmpty()) {
            return new User[0];  // Возвращаем пустой массив, если список пуст
        }

        List<User> createdUsers = new ArrayList<>();
        for (User user : users) {
            if (!isUserExist(user.getLogin())) {
                User newUser = userDAO.saveNewUser(user);
                createdUsers.add(newUser);
            }
        }

        return createdUsers.toArray(new User[0]);  // Преобразуем список в массив и возвращаем
    }
}
