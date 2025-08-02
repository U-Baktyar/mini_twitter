package twitter.factory.impl;

import twitter.controller.AuthentificationController;
import twitter.controller.InfoController;
import twitter.controller.PostController;
import twitter.controller.RegistrationController;
import twitter.exception.UnknowCommandException;
import twitter.factory.CommandFactory;
import twitter.factory.CommandHandler;
import twitter.runner.TwitterCommandEnum;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CommandFactoryImpl implements CommandFactory {

    private final Map<TwitterCommandEnum, CommandHandler> factory;

    private final AuthentificationController authentificationController;
    private final RegistrationController registrationController;
    private final InfoController infoController;
    private final PostController postController;


    public CommandFactoryImpl(
            AuthentificationController authentificationController,
            RegistrationController registrationController,
            InfoController infoController,
            PostController postController
    ) {
        this.factory = new HashMap<>();
        this.authentificationController = authentificationController;
        this.registrationController = registrationController;
        this.infoController = infoController;
        this.postController = postController;
        this.init();
    }

    private void init() {
        this.factory.put(TwitterCommandEnum.EXIT_COMMAND, () -> {
            try {
                authentificationController.exit_command();
            } catch (IOException e) {
                System.out.println("Ошибка при выполнении команды выхода: " + e.getMessage());
            }
        });
        this.factory.put(TwitterCommandEnum.HELP_COMMAND, () -> {
            try {
                infoController.help_command();
            } catch (IOException e){
                System.out.println("Ошибка при выполнении команды помощи: " + e.getMessage());
            }
        });
        this.factory.put(TwitterCommandEnum.REGISTER_USER_COMMAND, () -> {
            try {
                registrationController.register_command();
            } catch (IOException e) {
                System.out.println("Ошибка при выполнении регистрации: " + e.getMessage());
            }
        });
        this.factory.put(TwitterCommandEnum.LOGIN_COMMAND, () -> {
            try {
                authentificationController.login_command();
            } catch (IOException e) {
                System.out.println("Ошибка при выполнении входа: " + e.getMessage());
            }
        });
        this.factory.put(TwitterCommandEnum.LOGOUT_COMMAND, () -> {
            try {
                authentificationController.logout_command();
            } catch (IOException e) {
                System.out.println("Ошибка при выполнении выхода: " + e.getMessage());
            }
        });
        this.factory.put(TwitterCommandEnum.USER_INFO_COMMAND, () -> {
            try {
                infoController.info_command();
            }catch (IOException e) {
                System.out.println("Ошибка при получении информации: " + e.getMessage());
            }
        });
        this.factory.put(TwitterCommandEnum.INFO_BY_LOGIN_COMMAND, () -> {
            try {
                infoController.ingoByLogin_command();
            }catch (IOException e) {
                System.out.println("Ошибка при получении информации по логину: " + e.getMessage());
            }
        });
        this.factory.put(TwitterCommandEnum.INFO_ALL_COMMAND, () ->{
            try {
                infoController.infoAll_command();
            } catch (IOException e) {
                System.out.println("Ошибка при получении общей информации: " + e.getMessage());
            }
        });
        this.factory.put(TwitterCommandEnum.ADD_POST_COMMAND, () -> {
            try {
                postController.addPost();
            } catch (IOException e) {
                System.out.println("Ошибка при добавлении поста: " + e.getMessage());
            }
        });
        this.factory.put(TwitterCommandEnum.MY_POSTS_COMMAND, () -> {
            try {
                postController.myPost();
            } catch (IOException e) {
                System.out.println("Ошибка при получении ваших постов: " + e.getMessage());
            }
        });
        this.factory.put(TwitterCommandEnum.POST_ALL_COMMAND, () -> {
            try {
                postController.allPost();
            }catch (IOException e) {
                System.out.println("Ошибка при получении всех постов: " + e.getMessage());
            }
        });
        this.factory.put(TwitterCommandEnum.POST_BY_TAG_COMMAND, () -> {
            try {
                postController.allPostTag();
            } catch (IOException e) {
                System.out.println("Ошибка при поиске постов по тегу: " + e.getMessage());
            }
        });
        this.factory.put(TwitterCommandEnum.POST_BY_LOGIN_COMMAND, () -> {
            try {
                postController.allPostLogin();
            } catch (IOException e) {
                System.out.println("Ошибка при поиске постов по логину: " + e.getMessage());
            }
        });
        this.factory.put(TwitterCommandEnum.POST_BY_USER_TYPE_COMMAND, () -> {
            try {
                postController.allPostTypeUser();
            } catch (IOException e) {
            System.out.println("Ошибка при поиске постов по типу пользователя: " + e.getMessage());
            }
        });
        this.factory.put(TwitterCommandEnum.READ_USER_COMMAND, () -> {
            try {
                registrationController.readUser();
            } catch (IOException e) {
                System.out.println("Ошибка при чтении пользователей: " + e.getMessage());
            }
        });
        this.factory.put(TwitterCommandEnum.READ_POST_COMMAND, () -> {
            try {
                postController.readPosts();
            }catch (IOException e) {
                System.out.println("Ошибка при чтении постов: " + e.getMessage());
            }
        });
    }

    public CommandHandler getCommandHandler(String command) throws UnknowCommandException {
        if(!this.factory.containsKey(TwitterCommandEnum.getByCommand(command))) {
            throw new UnknowCommandException("Комманда " + command + " не найден");
        }
        return this.factory.get(TwitterCommandEnum.getByCommand(command));
    }
}
