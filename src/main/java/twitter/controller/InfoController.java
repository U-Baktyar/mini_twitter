package twitter.controller;

import java.io.IOException;

public interface InfoController {
    void help_command() throws IOException;
    void info_command() throws IOException;
    void ingoByLogin_command() throws IOException;
    void infoAll_command() throws IOException;
}
