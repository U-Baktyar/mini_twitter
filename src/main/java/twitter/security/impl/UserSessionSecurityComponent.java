package twitter.security.impl;

import twitter.configuration.Component;
import twitter.configuration.Injection;
import twitter.entity.User;
import twitter.security.SecurityComponent;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserSessionSecurityComponent implements SecurityComponent {

    private final Map<String, User> userSessions;

    @Injection
    public UserSessionSecurityComponent() {
        this.userSessions = new HashMap<>();
    }

    @Override
    public User getAuthentication(String userIp) {
        if (!this.userSessions.containsKey(userIp)) {
            return null;
        }
        return this.userSessions.get(userIp);
    }

    @Override
    public void setAuthentication(String userIp, User user) {
        this.userSessions.put(userIp, user);
        System.out.println("Пользователь " + user.whatIsYourName() + " вошёл в систему");
        System.out.println("Текущее количество сессий: " + this.userSessions.size());
        for (String key : this.userSessions.keySet()) {
            System.out.println("Сессия: " + key);
        }
    }

    @Override
    public void removeAuthentication(String userIp) {
        if (this.userSessions.containsKey(userIp)) {
            this.userSessions.remove(userIp);
            System.out.println("Пользователь вышел из системы");
            System.out.println("Текущее количество сессий: " + this.userSessions.size());
            for (String key : this.userSessions.keySet()) {
                System.out.println("Сессия: " + key);
            }
        } else {
            System.out.println("Пользователь не вошёл в систему");
        }
    }
}
