package twitter.service.impl;

import twitter.configuration.Component;
import twitter.configuration.Injection;
import twitter.entity.Post;
import twitter.entity.User;
import twitter.exception.TwitterUploadException;
import twitter.mapper.PostMapper;
import twitter.mapper.UserMapper;

import twitter.service.FileUploadService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public  class FileUploadServiceImpl implements FileUploadService {

    private final UserMapper userMapper;
    private final PostMapper postMapper;

    @Injection
    public FileUploadServiceImpl(
            UserMapper userMapper,
            PostMapper postMapper
    ) {
        this.userMapper = userMapper;
        this.postMapper = postMapper;
    }

    @Override
    public List<User> uploadUser(String fileName) throws TwitterUploadException {
        if (fileName == null || fileName.isEmpty()) {
            throw new TwitterUploadException("Имя файла обязательно для заполнение");
        }
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))){
            List<String> users = bufferedReader.lines().toList();
            if (users.isEmpty()) {
                return Collections.emptyList();
            }
            List<User> userList = new ArrayList<>(users.size());
            for (String line : users) {
                if(Objects.nonNull(line) && !line.isBlank()) {
                    User user = this.userMapper.mapUploadFileStringToUser(line);
                    System.out.println(line);
                    userList.add(user);
                }
            }
            return userList;
        } catch (FileNotFoundException e) {
            throw new TwitterUploadException("не найден файл с называнием " + fileName);
        }catch (IOException e) {
            throw new TwitterUploadException("ошибка при чтении файла");
        }
    }

    @Override
    public List<Post> uploadPost(String fileName) throws TwitterUploadException {
        if (fileName == null || fileName.isEmpty()) {
            throw new TwitterUploadException("Имя файла обязательно для заполнение");
        }
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))){
            List<String> posts = bufferedReader.lines().toList();
            if (posts.isEmpty()) {
                return Collections.emptyList();
            }
            List<Post> postList = new ArrayList<>(posts.size());
            for (String line : posts) {
                if(Objects.nonNull(line) && !line.isBlank()) {
                    Post post = this.postMapper.mapUploadFileStringToPost(line);
                    postList.add(post);
                }
            }
            return postList;
        } catch (FileNotFoundException e) {
            throw new TwitterUploadException("не найден файл с называнием " + fileName);
        }catch (IOException e) {
            throw new TwitterUploadException("ошибка при чтении файла");
        }
    }
}
