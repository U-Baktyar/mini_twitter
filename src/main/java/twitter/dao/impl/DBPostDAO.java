package twitter.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import twitter.configuration.*;
import twitter.dao.PostDAO;
import twitter.entity.Post;
import twitter.exception.TwitterCommandException;
import twitter.service.UserService;

import java.util.Arrays;
import java.util.List;

@Component
@Profile(active = {"prod", "default"})
public class DBPostDAO implements PostDAO {

    private final UserService userService;
    private final EntityManagerFactory entityManagerFactory;

    @Injection
    public DBPostDAO(UserService userService, EntityManagerFactory entityManagerFactory) {
        this.userService = userService;
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Post saveNewPost(Post post) {
        try (
                EntityManager entityManager = entityManagerFactory.createEntityManager()
        ) {
           try {
               entityManager.getTransaction().begin();
               entityManager.persist(post);
               entityManager.getTransaction().commit();
               return post;
           }catch (Exception e){
               entityManager.getTransaction().rollback();
               throw new TwitterCommandException("Ошибка при сохранении поста: " + e.getMessage());
           }
        }
    }

    @Override
    public Post[] getAllPosts() {
        try (
                EntityManager entityManager = entityManagerFactory.createEntityManager();
        ) {
          List<Post> posts = entityManager
                  .createQuery("SELECT p FROM Post p", Post.class)
                  .getResultList();
          return posts.toArray(new Post[0]);
        } catch (Exception e) {
            throw new TwitterCommandException(e.getMessage());
        }
    }

    @Override
    public Post[] getAllByUserPosts(int userId) {
        try (
                EntityManager entityManager = entityManagerFactory.createEntityManager()
        ) {
            List<Post> posts = entityManager
                    .createQuery("SELECT p FROM Post p WHERE p.author = :userId", Post.class)
                    .setParameter("userId", userId)
                    .getResultList();
            return posts.toArray(new Post[0]);

        } catch (Exception e) {
            throw new TwitterCommandException(e.getMessage());
        }
    }

    @Override
    public Post[] getAllPostsTag(String tag) {
        try (
                EntityManager entityManager = entityManagerFactory.createEntityManager()
        ) {
            List<Post> posts = entityManager
                    .createQuery("SELECT p FROM Post p WHERE  p.tagsAsString LIKE :tag", Post.class)
                    .setParameter("tag", "%" + tag + "%")
                    .getResultList();
            return posts.toArray(new Post[0]);

        } catch (Exception e) {
            throw new TwitterCommandException(e.getMessage());
        }
    }

    @Override
    public Post[] getAllPostByUserType(int[] idsUser) {
        try (
                EntityManager entityManager = entityManagerFactory.createEntityManager()
        ) {
            List<Post> posts = entityManager
                    .createQuery("SELECT p FROM Post p WHERE p.author.id in :ids ", Post.class)
                    .setParameter("ids", Arrays.stream(idsUser).boxed().toString())
                    .getResultList();
            return posts.toArray(new Post[0]);

        } catch (Exception e) {
            throw new TwitterCommandException(e.getMessage());
        }
    }
}
