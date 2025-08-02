package twitter.service;

import twitter.entity.Post;
import twitter.entity.User;
import twitter.exception.UnknowUserTypeException;

import java.util.List;

public interface PostService {
    Post createPost(Post post);
    Post[] getAllByUserPosts(User user);
    Post[] allPosts();
    Post[] getAllPostsTag(String tag);
    Post[] getAllPostsTypeUser(int userType) throws UnknowUserTypeException;
    Post[] createSeveralPost(List<Post> posts);

}
