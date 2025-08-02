package twitter.dao;

import twitter.entity.User;
import twitter.exception.UnknowUserTypeException;
import twitter.exception.UserNotFoundException;

public interface UserDAO {
    User getByLogin(String login) throws UserNotFoundException;
    User getById(int id) throws UserNotFoundException;
    User saveNewUser (User user);
    User[] getAllUsers();
    User[] getAllUsersByType(int userType) throws UnknowUserTypeException;
}
