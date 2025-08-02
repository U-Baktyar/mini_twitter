package twitter.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @Column(name = "login", nullable = false, unique = true)
    protected String login;

    @Column(name = "password", nullable = false)
    protected String password;

    @Column(name = "currentdate", nullable = false)
    protected LocalDateTime currentDate;


    @OneToMany(mappedBy = "author",fetch = FetchType.EAGER)
    protected List<Post> posts = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name ="m2m_user_likes",
            joinColumns = @JoinColumn(name =  "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name =  "post_id", referencedColumnName = "id")

    )
    protected List<Post> postsLikes = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "type_user", nullable = false)
    protected UserType typeUser;

    protected User() {}

    @PrePersist
    protected void onCreate() {
        this.currentDate = LocalDateTime.now();
    }

    // --- Геттеры и сеттеры ---
    public List<Post> getPostsLikes() {
        return postsLikes;
    }

    public void setPostsLikes(List<Post> postsLikes) {
        this.postsLikes = postsLikes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(LocalDateTime currentDate) {
        this.currentDate = currentDate;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public UserType getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(UserType typeUser) {
        this.typeUser = typeUser;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return id == user.id &&
                Objects.equals(login, user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login);
    }

    // --- toString для отладки ---

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", typeUser=" + typeUser +
                ", created=" + currentDate +
                '}';
    }


    public abstract String beauty();

    public abstract String whatIsYourName();

    public abstract String toFileString();
}
