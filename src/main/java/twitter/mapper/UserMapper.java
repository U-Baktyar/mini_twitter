package twitter.mapper;

import twitter.entity.User;

public interface UserMapper {
    User mapUploadFileStringToUser(String userString);
    User mapFileStringToUser(String userString);

}
