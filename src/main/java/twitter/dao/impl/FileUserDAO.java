package twitter.dao.impl;

import twitter.configuration.Component;
import twitter.configuration.Injection;
import twitter.configuration.Profile;
import twitter.dao.UserDAO;
import twitter.entity.User;
import twitter.entity.UserType;
import twitter.exception.UnknowUserTypeException;
import twitter.exception.UserNotFoundException;
import twitter.mapper.UserMapper;

import java.io.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
@Profile(active = "test")
public class FileUserDAO implements UserDAO {

    private final String fileName = "UserData.txt";
    private final Set<User> users;
    private int id = 1;
    private final UserMapper userMapper;

    @Injection
    public FileUserDAO(UserMapper userMapper) {
        this.users = new HashSet<>();
        this.userMapper = userMapper;
        this.init();
    }

    private void init(){
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(this.fileName))) {
            List<String> lines = bufferedReader.lines().toList();
            for (String line : lines) {
                if(Objects.nonNull(line) && !line.isBlank()){
                    User user = this.userMapper.mapFileStringToUser(line);
                    this.users.add(user);
                }
            }
            this.id = this.users.size() + 1;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1
            );
        }
    }

    @Override
    public User getByLogin(String login) throws UserNotFoundException {
        if (this.users.isEmpty()) {
            throw new UserNotFoundException("Пользователь с id: " + id + " не найден");
        }
        return this.users
                .stream()
                .filter(user -> user.getLogin().equals(login))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("Пользователь с id: " + login + " не найден"));
    }

    @Override
    public User getById (int id) throws UserNotFoundException {
        if(this.users.isEmpty()){
            throw new UserNotFoundException("Пользователь с id: " + id + " не найден");
        }
        return this.users.
                stream().
                filter(user -> user.getId() == id).
                findFirst().
                orElseThrow(() -> new UserNotFoundException("Пользователь с id: " + id + " не найден"));
    }


    @Override
    public User saveNewUser(User user) {
        user.setId(id);
        user.setCurrentDate(LocalDateTime.now());
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.fileName, true))) {
            bufferedWriter.newLine();
            bufferedWriter.append(user.toFileString());
            id++;
            this.users.add(user);
        } catch (IOException e){
            System.out.println("не получилось создать пользователя: ");
            System.out.println(e.getMessage());
        }
        return user;
    }

    @Override
    public User[] getAllUsersByType(int userType) throws UnknowUserTypeException {
        if(users.isEmpty()){
            return new User[0];
        }
        UserType type = UserType.getValue(userType);
        return this.users
                .stream()
                .filter(user -> type.equals(user.getTypeUser()))
                .toList()
                .toArray(new User[0]);
    }

    @Override
    public User[] getAllUsers() {
        if(users.isEmpty()){
            return new User[0];
        }
        return this.users.toArray(new User[0]);
    }
}
