package twitter.controller;

import twitter.exception.ClientDisconnectedException;

import java.io.IOException;

public interface AuthentificationController {
    void exit_command() throws IOException, ClientDisconnectedException;
    void login_command() throws IOException;
    void logout_command() throws IOException;
}
