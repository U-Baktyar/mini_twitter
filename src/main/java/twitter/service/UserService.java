package twitter.service;

import twitter.entity.User;
import twitter.exception.UnknowUserTypeException;
import twitter.exception.UserNotFoundException;

import java.util.List;

public interface UserService {
    boolean isUserExist(String login);
    User creatUser(User user);
    User getUserByLogin(String login) throws UserNotFoundException;
    User getUserById(int id) throws UserNotFoundException;
    User[] getAllUsers();
    User[] getUsersByType(int userType) throws UnknowUserTypeException;
    User[] createSeveralUsers(List<User> users);
}
