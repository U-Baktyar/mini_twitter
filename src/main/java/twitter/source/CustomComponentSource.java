package twitter.source;


import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.flywaydb.core.Flyway;
import twitter.configuration.ComponentMethod;
import twitter.configuration.ComponentSource;
import twitter.configuration.Value;

import java.util.HashMap;
import java.util.Map;


@ComponentSource
public class CustomComponentSource {
    @Value(kay = "database.url")
    private  String dbUrl;

    @Value(kay = "database.user")
    private  String dbUser;

    @Value(kay = "database.password")
    private  String dbPassword;

    @ComponentMethod
    public Flyway flyway() {
        return Flyway
                .configure()
                .driver("org.postgresql.Driver")
                .dataSource(dbUrl, dbUser, dbPassword)
                .validateMigrationNaming(true)
                .validateOnMigrate(true)
                .baselineOnMigrate(true)
                .outOfOrder(true)
                .load();
    }

    @ComponentMethod
    public EntityManagerFactory entityManagerFactory(){
        Map<String, String> properties = new HashMap<>();
        properties.put("jakarta.persistence.jdbc.driver", "org.postgresql.Driver");
        properties.put("jakarta.persistence.jdbc.url", dbUrl);
        properties.put("jakarta.persistence.jdbc.user", dbUser);
        properties.put("jakarta.persistence.jdbc.password", dbPassword);
        return Persistence.createEntityManagerFactory("mini_twitter", properties);
    }



}


