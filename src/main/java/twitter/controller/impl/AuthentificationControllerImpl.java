package twitter.controller.impl;
import twitter.controller.AuthentificationController;
import twitter.entity.User;
import twitter.exception.ClientDisconnectedException;
import twitter.exception.TwitterCommandException;
import twitter.exception.UserNotFoundException;
import twitter.security.SecurityComponent;
import twitter.service.UserService;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;


public class AuthentificationControllerImpl implements AuthentificationController {

    private final UserService userService;
    private final SecurityComponent securityComponent;
    private final BufferedReader in;
    private final BufferedWriter out;
    private final String userIp;


    public AuthentificationControllerImpl(
            UserService userService,
            SecurityComponent securityComponent,
            BufferedReader in,
            BufferedWriter out,
            String userIp
    ) {
        this.userService = userService;
        this.securityComponent = securityComponent;
        this.in = in;
        this.out = out;
        this.userIp = userIp;
    }

    @Override
    public void exit_command() throws IOException, ClientDisconnectedException {
        out.append("<<<<<<Спасибо что используете Mini Twitter!>>>>>").append("\n");
        out.flush();
        throw new ClientDisconnectedException();
    }



    @Override
    public void login_command() throws IOException{

        if(this.securityComponent.getAuthentication(userIp) != null){
                out.append("Для выполнения данной команды необходимо выйти из системы.").append("\n");
                out.flush();
                return;
        }
        out.append("<<< Вход в систему >>>").append("\n");
        out.append("Введите логин. ");
        out.flush();


        String login = in.readLine();
        if (login == null || login.trim().isEmpty()) {
            out.append("Логин не может быть пустым").append("\n");
            out.flush();
            return;
        }
        login = login.trim();
        if (login.contains(" ")) {
            out.append("Логин не может содержать пробелы").append("\n");
            out.flush();
            return;
        }

        try {
            User user = userService.getUserByLogin(login);

            out.append("Введите пароль: ");
            out.flush();
            String password = in.readLine();
            if (!user.getPassword().equals(password)) {
                System.out.printf(user.getLogin());
                System.out.println(password);
                out.append("Пароль введен неверно").append("\n");
                out.flush();
                return;
            }

            securityComponent.setAuthentication(userIp, user);

            out.append("Добро пожаловать, " + user.whatIsYourName() + "!").append("\n");
            out.append("<<<<<<  Вход в систему прошел успешно >>>>>").append("\n");
            out.flush();
        } catch (UserNotFoundException | TwitterCommandException ex) {
            out.append(ex.getMessage()).append("\n");
        }

    }

    @Override
    public void logout_command() throws IOException{
        if (this.securityComponent.getAuthentication(userIp) == null) {
            out.append("Для выполнения данной команды необходимо войти в систему.").append("\n");
            out.flush();
            return;
        }

         
        securityComponent.removeAuthentication(userIp);
    }


}
