package twitter.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Entity
@Table(name = "post")
@Inheritance(strategy = InheritanceType.JOINED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "create_at")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User author;


    @Column(name = "topic")
    private String topic;

    @Column(name = "text" )
    private String text;

    @Transient
    @Column(name = "tags")
    private String tagsAsString;

    @Transient
    private String[] tags;

    @ManyToMany(mappedBy = "postsLikes", fetch = FetchType.EAGER)
    private List<User> userToLikes;

    public String getTagsAsString() {
        return tagsAsString;
    }

    public void setTagsAsString(String tagsAsString) {
        this.tagsAsString = tagsAsString;
    }

    public List<User> getUserToLikes() {
        return userToLikes;
    }

    public void setUserToLikes(List<User> userToLikes) {
        this.userToLikes = userToLikes;
    }

    @PostLoad
    protected void postLoad() {
        if (tagsAsString != null) {
            tags = tagsAsString.split(",");
        }else {
            this.tags = new String[0];
        }
    }

    @PrePersist
    protected void onCreate(){
        this.date = LocalDateTime.now();
        this.tagsAsString = String.join(",", tags);
    }

    @PreUpdate
    protected void onUpdate(){
        this.tagsAsString = String.join(",", tags);
    }




    public Post() {}

    // Getters
    public Integer getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public User getAuthor() {
        return author;
    }

    public String getTopic() {
        return topic;
    }

    public String getText() {
        return text;
    }

    public String[] getTags() {
        return tags;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    // Check if tag exists in post
    public boolean hasTag(String tag) {
        if (tags == null || tag == null) return false;
        for (String s : tags) {
            if (s.equalsIgnoreCase(tag)) {
                return true;
            }
        }
        return false;
    }

    public String toFileString() {
        String tagsStr = tags != null ? String.join(", ", tags) : "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateStr = date != null ? date.format(formatter) : "";
        return String.format(
                "{%d}{%d}{%s}{%s}{%s}{%s}",
                id,
                author != null ? author.getId() : 0,
                topic,
                text,
                tagsStr,
                dateStr
        );
    }
}
