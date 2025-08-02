package twitter.controller.impl;


import twitter.controller.PostController;
import twitter.dto.PostResponseDTO;
import twitter.entity.Post;
import twitter.entity.User;
import twitter.exception.TwitterUploadException;
import twitter.exception.UnknowUserTypeException;
import twitter.exception.UserNotFoundException;
import twitter.mapper.PostMapper;
import twitter.security.SecurityComponent;
import twitter.service.FileUploadService;
import twitter.service.PostService;
import twitter.service.UserService;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

public class PostControllerImpl implements PostController {

    private final PostService postService;
    private final SecurityComponent securityComponent;
    private final PostMapper postMapper;
    private final UserService userService;
    private final FileUploadService fileUploadService;
    private final BufferedReader in;
    private final BufferedWriter out;
    private final String userIp;


    public PostControllerImpl(
            PostService postService,
            SecurityComponent securityComponent,
            PostMapper postMapper,
            UserService userService,
            FileUploadService fileUploadService,
            BufferedReader in,
            BufferedWriter out, String userIp
    ) {
        this.postService = postService;
        this.securityComponent = securityComponent;
        this.postMapper = postMapper;
        this.userService = userService;
        this.fileUploadService = fileUploadService;
        this.in = in;
        this.out = out;
        this.userIp = userIp;
    }

    @Override
    public void readPosts() throws IOException {
        if (this.securityComponent.getAuthentication(userIp) == null) {
            out.append("Для выполнения данной команды необходимо войти в систему.").append("\n");
            out.flush();
            return;
        }

        out.append("Файл для чтения: ");
        out.flush();
        String filename = in.readLine();
        out.append("Считывание публикаций...").append("\n");
        out.flush();

        Thread thread = new Thread(() -> {
            try {
                List<Post> newPosts = this.fileUploadService.uploadPost(filename);
                Post[] createdPosts = this.postService.createSeveralPost(newPosts);

                out.append("Было добавлено " + createdPosts.length + " публикаций").append("\n");
                out.flush();
            } catch (TwitterUploadException | IOException ex) {
                System.out.println(ex.getMessage());
            }
        });
        thread.start();
    }

    @Override
    public void addPost() throws IOException {
        if (this.securityComponent.getAuthentication(userIp) == null) {
            out.append("Для выполнения данной команды необходимо войти в систему.").append("\n");
            out.flush();
            return;
        }

        out.append("<<<<<<  Создание новой публикации  >>>>>").append("\n");

        out.append("Введите тему публикации: ");
        out.flush();
        String topic = in.readLine();
        if (topic == null || topic.trim().isEmpty()) {
            out.append("Тема публикации не может быть пустой").append("\n");
            out.flush();
            return;
        }

        out.append("Введите текст публикации: ");
        out.flush();
        String text = in.readLine();
        if (text == null || text.trim().isEmpty()) {
            out.append("Текст публикации не может быть пустой").append("\n");
            out.flush();
            return;
        }

        out.append("Введите теги публикации(может быть пустым, для отделения тегов использовать ','): ");
        out.flush();
        String tags = in.readLine();

        Post post = new Post();

        User authenticatedUser = securityComponent.getAuthentication(userIp);
        post.setAuthor(authenticatedUser);

        post.setTopic(topic);
        post.setText(text);

        String[] tagArray = tags.split(",");
        post.setTags(tagArray);

        try {
            Post createdPost = postService.createPost(post);
            PostResponseDTO responseDto = postMapper.mapperToDTO(createdPost);

            out.append(responseDto.toString()).append("\n");
            out.append("<<<<<<  Конец создания публикации  >>>>>").append("\n");
            out.flush();
        } catch (UserNotFoundException ex) {
            out.append(ex.getMessage()).append("\n");
            out.flush();
        }
    }

    @Override
    public void myPost() throws IOException {
        if (this.securityComponent.getAuthentication(userIp) == null) {
            out.append("Для выполнения данной команды необходимо войти в систему.").append("\n");
            out.flush();
            return;
        }

        out.append("<<<<<<  Мои публикации  >>>>>").append("\n");
        User authenticatedUser = securityComponent.getAuthentication(userIp);
        Post[] posts = postService.getAllByUserPosts(authenticatedUser);
        try {
            for (Post post : posts) {
                PostResponseDTO responseDto = postMapper.mapperToDTO(post);
                out.append(responseDto.toString()).append("\n");
            }
            out.append("<<<<<<  Конец моих публикаций  >>>>>").append("\n");
            out.flush();
        } catch (UserNotFoundException ex) {
            out.append(ex.getMessage()).append("\n");
            out.flush();
        }
    }

    @Override
    public void allPost() throws IOException {
        if (this.securityComponent.getAuthentication(userIp) == null) {
            out.append("Для выполнения данной команды необходимо войти в систему.").append("\n");
            out.flush();
            return;
        }

        out.append("<<<<<<  Все публикации  >>>>>").append("\n");
        Post[] posts = postService.allPosts();
        try {
            for (Post post : posts) {
                PostResponseDTO responseDto = postMapper.mapperToDTO(post);
                out.append(responseDto.toString()).append("\n");
            }
            out.append("<<<<<<  Конец всех публикаций  >>>>>").append("\n");
            out.flush();
        } catch (UserNotFoundException ex) {
            out.append(ex.getMessage()).append("\n");
            out.flush();
        }
    }

    @Override
    public void allPostTag() throws IOException {
        if (this.securityComponent.getAuthentication(userIp) == null) {
            out.append("Для выполнения данной команды необходимо войти в систему.").append("\n");
            out.flush();
            return;
        }

        out.append("Введите тег публикаций: ");
        out.flush();
        String tag = in.readLine();
        if (tag == null || tag.trim().isEmpty()) {
            out.append("Тег не может быть пустым").append("\n");
            out.flush();
            return;
        }
        try {
            out.append("<<<<<<  Все публикации по тегу >>>>>").append("\n");
            Post[] posts = postService.getAllPostsTag(tag);
            for (Post post : posts) {
                PostResponseDTO responseDto = postMapper.mapperToDTO(post);
                out.append(responseDto.toString()).append("\n");
            }
            out.append("<<<<<<  Конец всех публикаций по тегу  >>>>>").append("\n");
            out.flush();
        } catch (UserNotFoundException ex) {
            out.append(ex.getMessage()).append("\n");
            out.flush();
        }
    }

    @Override
    public void allPostLogin() throws IOException {
        if (this.securityComponent.getAuthentication(userIp) == null) {
            out.append("Для выполнения данной команды необходимо войти в систему.").append("\n");
            out.flush();
            return;
        }

        out.append("Введите логин, чьи публикации показать: ");
        out.flush();
        String login = in.readLine();
        if (login == null || login.trim().isEmpty()) {
            out.append("Логин не может быть пустым").append("\n");
            out.flush();
            return;
        }
        login = login.trim();
        if (login.contains(" ")) {
            out.append("Логин не может содержать пробелы");
            out.flush();
            return;
        }

        try {
            User user = userService.getUserByLogin(login);

            out.append("<<<<<<  Публикации по логину  >>>>>").append("\n");
            Post[] posts = postService.getAllByUserPosts(user);
            for (Post post : posts) {
                PostResponseDTO responseDto = postMapper.mapperToDTO(post);
                out.append(responseDto.toString()).append("\n");
            }
            out.append("<<<<<<  Конец публикаций по логину  >>>>>").append("\n");
            out.flush();
        } catch (UserNotFoundException ex) {
            out.append(ex.getMessage()).append("\n");
            out.flush();
        }
    }

    @Override
    public void allPostTypeUser() throws IOException {
        if (this.securityComponent.getAuthentication(userIp) == null) {
            out.append("Для выполнения данной команды необходимо войти в систему.").append("\n");
            out.flush();
            return;
        }

        out.append("Введите тип пользователя (0 - человек, 1 - организация): ");
        out.flush();
        int userType = Integer.parseInt(in.readLine());
        if (userType != 0 && userType != 1) {
            out.append("Введен неверный тип пользователя.").append("\n");
            out.flush();
            return;
        }

        try {
            out.append("<<<<<<  Публикации по типу пользователя  >>>>>").append("\n");
            Post[] posts = postService.getAllPostsTypeUser(userType);
            for (Post post : posts) {
                PostResponseDTO responseDto = postMapper.mapperToDTO(post);
                out.append(responseDto.toString()).append("\n");
            }
            out.append("<<<<<<  Конец публикаций по типу пользователя  >>>>>").append("\n");
            out.flush();
        } catch (UserNotFoundException | UnknowUserTypeException ex) {
            out.append(ex.getMessage()).append("\n");
            out.flush();
        }
    }
}