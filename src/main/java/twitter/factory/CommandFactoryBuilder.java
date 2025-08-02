package twitter.factory;

import twitter.configuration.Component;
import twitter.configuration.Injection;
import twitter.controller.AuthentificationController;
import twitter.controller.InfoController;
import twitter.controller.PostController;
import twitter.controller.RegistrationController;
import twitter.controller.impl.AuthentificationControllerImpl;
import twitter.controller.impl.InfoControllerImpl;
import twitter.controller.impl.PostControllerImpl;
import twitter.controller.impl.RegistrationControllerImpl;
import twitter.factory.impl.CommandFactoryImpl;
import twitter.mapper.PostMapper;
import twitter.security.SecurityComponent;
import twitter.service.FileUploadService;
import twitter.service.PostService;
import twitter.service.UserService;

import java.io.BufferedReader;
import java.io.BufferedWriter;



@Component
public class CommandFactoryBuilder {
    private final UserService userService;
    private final SecurityComponent securityComponent;
    private final PostService postService;
    private final PostMapper postMapper;
    private final FileUploadService fileUploadService;

    @Injection
    public CommandFactoryBuilder(
            UserService userService,
            SecurityComponent securityComponent,
            PostService postService,
            PostMapper postMapper,
            FileUploadService fileUploadService
    ) {
        this.userService = userService;
        this.securityComponent = securityComponent;
        this.postService = postService;
        this.postMapper = postMapper;
        this.fileUploadService = fileUploadService;
    }

    public CommandFactory buildCommandFactoryForUser(String userIp, BufferedReader reader, BufferedWriter writer) {
        AuthentificationController authentificationController = new AuthentificationControllerImpl(userService, securityComponent, reader, writer, userIp);
        InfoController infoController = new InfoControllerImpl(userService, securityComponent, reader, writer, userIp);
        PostController postController = new PostControllerImpl(postService, securityComponent, postMapper, userService, fileUploadService, reader, writer, userIp);
        RegistrationController registrationController = new RegistrationControllerImpl(userService, fileUploadService, securityComponent, reader, writer, userIp);
        return new CommandFactoryImpl(authentificationController, registrationController, infoController, postController);
    }
}
