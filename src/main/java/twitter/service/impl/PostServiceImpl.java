package twitter.service.impl;

import twitter.configuration.Component;
import twitter.configuration.Injection;
import twitter.dao.PostDAO;
import twitter.entity.Post;
import twitter.entity.User;
import twitter.exception.UnknowUserTypeException;
import twitter.service.PostService;
import twitter.service.UserService;

import java.util.List;
import java.util.Objects;

@Component
public class PostServiceImpl implements PostService {

    private final PostDAO postDAO;
    private final UserService userService;

    @Injection
    public PostServiceImpl(PostDAO postDAO, UserService userService) {
        this.postDAO = postDAO;
        this.userService = userService;
    }

    @Override
    public Post createPost(Post post) {
        return postDAO.saveNewPost(post);
    }

    @Override
    public Post[] getAllByUserPosts(User user) {
        Post[] userPosts = postDAO.getAllByUserPosts(user.getId());
        return userPosts;
    }

    @Override
    public Post[] allPosts() {
        Post[] posts = postDAO.getAllPosts();
        return posts;
    }

    @Override
    public Post[] getAllPostsTag(String tag) {
        Post[] posts = postDAO.getAllPostsTag(tag);
        return posts;
    }

    @Override
    public Post[] getAllPostsTypeUser(int userType) throws UnknowUserTypeException {
        User[] users = userService.getUsersByType(userType);
        if (users.length == 0) {
            return new Post[0];
        }

        int[] userIds = new int[users.length];
        for (int i = 0; i < users.length; i++) {
            userIds[i] = users[i].getId();
        }

        return postDAO.getAllPostByUserType(userIds);
    }

    @Override
    public Post[] createSeveralPost(List<Post> posts) {
        if (Objects.isNull(posts) || posts.isEmpty()) {
            return new Post[0];
        }

        for (Post post : posts) {
            createPost(post);
        }

        return posts.toArray(new Post[0]);
    }

}
