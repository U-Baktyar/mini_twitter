package twitter.mapper;

import twitter.dto.PostResponseDTO;
import twitter.entity.Post;
import twitter.exception.UserNotFoundException;

public interface PostMapper {
    PostResponseDTO mapperToDTO(Post post) throws UserNotFoundException;
    Post mapFileStringToPost(String postAsString);

    Post mapUploadFileStringToPost(String postAsString);

}
