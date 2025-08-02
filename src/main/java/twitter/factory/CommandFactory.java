package twitter.factory;

import twitter.exception.UnknowCommandException;

public interface CommandFactory {
    CommandHandler getCommandHandler(String command) throws UnknowCommandException;
}
