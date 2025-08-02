package twitter.controller;


import java.io.IOException;

public interface PostController {
    void readPosts() throws IOException;
    void addPost() throws IOException;
    void myPost() throws IOException;
    void allPost() throws IOException;
    void allPostTag() throws IOException;
    void allPostLogin() throws IOException;
    void allPostTypeUser() throws IOException;

}
