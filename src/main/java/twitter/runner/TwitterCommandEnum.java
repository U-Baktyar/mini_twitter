package twitter.runner;

public enum TwitterCommandEnum {
    EXIT_COMMAND("exit", "Выход из системы"),
    HELP_COMMAND("help", "Вывод списка команд"),
    REGISTER_USER_COMMAND("register", "Регистрация пользователя в системе"),
    LOGIN_COMMAND("login", "Аутентификация пользователя"),
    LOGOUT_COMMAND("logout", "Выход из системы"),
    USER_INFO_COMMAND("info", "Вывод информации о текущем пользователе"),
    INFO_BY_LOGIN_COMMAND("info_by_login", "Вывод информации о пользователе по логину"),
    INFO_ALL_COMMAND("info_all", "Вывод информации о всех пользователях"),
    ADD_POST_COMMAND("add_post", "Добавление новой публикации"),
    MY_POSTS_COMMAND("my_posts", "Показать мои публикации"),
    POST_ALL_COMMAND("post_all", "Показать все публикации"),
    POST_BY_TAG_COMMAND("post_by_tag", "Показать публикации по тегу"),
    POST_BY_LOGIN_COMMAND("post_by_login", "Показать публикации пользователя по логину"),
    POST_BY_USER_TYPE_COMMAND("post_by_user_type", "Показать публикации по типу пользователя"),
    READ_USER_COMMAND("read_user", "Загрузить пользователей из файла"),
    READ_POST_COMMAND("read_post", "Загрузить публикации из файла");

    private final String command;
    private final String description;

    TwitterCommandEnum(String command, String description) {
        this.command = command;
        this.description = description;
    }

    public static TwitterCommandEnum getByCommand(String command) {
        for (TwitterCommandEnum commandEnum : TwitterCommandEnum.values()) {
            if (commandEnum.command.equals(command)) {
                return commandEnum;
            }
        }
        return null;
    }

    public static String info() {
        StringBuilder stringBuilder = new StringBuilder();
        for (TwitterCommandEnum commandEnum : TwitterCommandEnum.values()) {
            stringBuilder.append(commandEnum.command).append(" - ").append(commandEnum.description).append("\n");
        }
        return stringBuilder.toString();
    }
}
