package twitter.dto;

public class PostResponseDTO {
    private String author;
    private String createdAt;
    private String topic;
    private String text;
    private String tags;
    private Integer likesCount;

    public Integer getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    public PostResponseDTO(){}

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return String.format("Публикации: {\n    Автор: %s\n    Cоздано %s\n    Тема: %s\n    Текст: %s\n    Теги: %s\n    каличество лайков:  %s\n}",this.getAuthor(),this.getCreatedAt(),this.getTopic(),this.getText(),this.getTags(), this.likesCount);
    }
}
