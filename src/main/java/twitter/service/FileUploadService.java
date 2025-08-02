package twitter.service;

import twitter.entity.Post;
import twitter.entity.User;
import twitter.exception.TwitterUploadException;

import java.util.List;

public interface FileUploadService {

    List<User> uploadUser(String fileName) throws TwitterUploadException;

    List<Post> uploadPost(String fileName) throws TwitterUploadException;
}
