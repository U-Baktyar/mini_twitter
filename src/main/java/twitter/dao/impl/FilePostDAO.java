package twitter.dao.impl;

import twitter.configuration.Component;
import twitter.configuration.Injection;
import twitter.configuration.Profile;
import twitter.dao.PostDAO;
import twitter.entity.Post;
import twitter.mapper.PostMapper;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

@Component
@Profile(active = "test")
public class FilePostDAO implements PostDAO {

    private final PostMapper postMapper;
    private final String fileName = "PostData.txt";
    private final List<Post> list;
    private int id = 1;

    @Injection
    public FilePostDAO(PostMapper postMapper) {
        this.postMapper = postMapper;
        this.list = new LinkedList<>();
        this.init();
    }

    private void init() {
        try (BufferedReader reader = new BufferedReader(new FileReader(this.fileName))) {
            List<String> lines = reader.lines().toList();
            for (String line : lines) {
                if (line != null && !line.isBlank()) {
                    Post post = postMapper.mapFileStringToPost(line);
                    this.list.add(post);
                }
            }
            this.id = this.list.size() + 1;
        } catch (IOException e) {
            System.err.println("Ошибка при инициализации постов из файла: " + e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public Post saveNewPost(Post post) {
        post.setId(id++);
        post.setDate(LocalDateTime.now());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.fileName, true))) {
            writer.newLine();
            writer.write(post.toFileString());
            list.add(post);
        } catch (IOException e) {
            System.err.println("Не удалось сохранить публикацию: " + e.getMessage());
        }

        return post;
    }

    @Override
    public Post[] getAllPosts() {
        return list.toArray(new Post[0]);
    }

    @Override
    public Post[] getAllByUserPosts(int userId) {
        return list.stream()
                .filter(post -> post.getAuthor().getId() == userId)
                .toArray(Post[]::new);
    }

    @Override
    public Post[] getAllPostsTag(String tag) {
        return list.stream()
                .filter(post -> {
                    String[] tags = post.getTags();
                    if (tags == null) return false;
                    for (String t : tags) {
                        if (t.equalsIgnoreCase(tag)) return true;
                    }
                    return false;
                })
                .toArray(Post[]::new);
    }

    @Override
    public Post[] getAllPostByUserType(int[] idsUser) {
        Set<Integer> userIdSet = new HashSet<>();
        for (int id : idsUser) {
            userIdSet.add(id);
        }

        return list.stream()
                .filter(post -> userIdSet.contains(post.getAuthor().getId()))
                .toArray(Post[]::new);
    }
}
