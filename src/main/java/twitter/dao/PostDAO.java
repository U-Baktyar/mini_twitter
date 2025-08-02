package twitter.dao;

import twitter.entity.Post;

public interface PostDAO {
    Post saveNewPost(Post post);
    Post[] getAllPosts();
    Post[] getAllByUserPosts(int userId);
    Post[] getAllPostsTag(String tag);
    Post[] getAllPostByUserType(int[] idsUser);
}
