package twitter.mapper.impl;

import twitter.configuration.Component;
import twitter.configuration.Injection;
import twitter.entity.Organization;
import twitter.entity.Person;
import twitter.entity.User;
import twitter.entity.UserType;
import twitter.mapper.UserMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class UserMapperImpl implements UserMapper {

    @Injection
    public UserMapperImpl() {}

    @Override
    public User mapUploadFileStringToUser(String userString) {
        String login = userString.substring(1, userString.indexOf("}"));
        userString = userString.substring(userString.indexOf("}") + 1);

        String password = userString.substring(1, userString.indexOf("}"));
        userString = userString.substring(userString.indexOf("}") + 1);

        UserType userType = UserType.valueOf(userString.substring(1, userString.indexOf("}")));
        userString = userString.substring(userString.indexOf("}") + 1);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if(UserType.PERSON.equals(userType)) {
            Person person = new Person();
            person.setLogin(login);
            person.setPassword(password);
            person.setTypeUser(userType);
            String name = userString.substring(1, userString.indexOf("}"));
            userString = userString.substring(userString.indexOf("}") + 1);
            person.setLastName(name);
            String firstName = userString.substring(1, userString.indexOf("}"));
            userString = userString.substring(userString.indexOf("}") + 1);
            person.setFirstName(firstName);
            LocalDate dateOfBirth = LocalDate.parse(userString.substring(1, userString.indexOf("}")),dtf);
            person.setDateOfBirth(dateOfBirth);
            return person;
        }
        Organization organization = new Organization();
        organization.setLogin(login);
        organization.setPassword(password);
        organization.setTypeUser(userType);
        String title = userString.substring(1, userString.indexOf("}"));
        userString = userString.substring(userString.indexOf("}") + 1);
        organization.setOrganizationName(title);
        String organizationType = userString.substring(1, userString.indexOf("}"));
        userString = userString.substring(userString.indexOf("}") + 1);
        organization.setOrganizationType(organizationType);
        LocalDate localDateOfBirth = LocalDate.parse(userString.substring(1, userString.indexOf("}")), dtf);
        organization.setOrganizationBirthday(localDateOfBirth);
        return organization;
    }

    @Override
    public User mapFileStringToUser(String userString) {
        userString = userString.substring(userString.indexOf("}") + 1);
        String login = userString.substring(1, userString.indexOf("}"));
        userString = userString.substring(userString.indexOf("}") + 1);
        String password = userString.substring(1, userString.indexOf("}"));
        userString = userString.substring(userString.indexOf("}") + 1);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime registerTime = LocalDateTime.parse(userString.substring(1, userString.indexOf("}")), dtf);
        userString = userString.substring(userString.indexOf("}") + 1);
        UserType userType = UserType.valueOf(userString.substring(1, userString.indexOf("}")));
        userString = userString.substring(userString.indexOf("}") + 1);
        dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if(UserType.PERSON.equals(userType)) {
            Person person = new Person();
            person.setLogin(login);
            person.setPassword(password);
            person.setCurrentDate(registerTime);
            person.setTypeUser(userType);
            String name = userString.substring(1, userString.indexOf("}"));
            userString = userString.substring(userString.indexOf("}") + 1);
            person.setLastName(name);
            String firstName = userString.substring(1, userString.indexOf("}"));
            userString = userString.substring(userString.indexOf("}") + 1);
            person.setFirstName(firstName);
            LocalDate dateOfBirth = LocalDate.parse(userString.substring(1, userString.indexOf("}")),dtf);
            userString = userString.substring(userString.indexOf("}") + 1);
            person.setDateOfBirth(dateOfBirth);
            return person;
        }
        Organization organization = new Organization();
        organization.setLogin(login);
        organization.setPassword(password);
        organization.setCurrentDate(registerTime);
        organization.setTypeUser(userType);
        String title = userString.substring(1, userString.indexOf("}"));
        userString = userString.substring(userString.indexOf("}") + 1);
        organization.setOrganizationName(title);
        String organizationType = userString.substring(1, userString.indexOf("}"));
        userString = userString.substring(userString.indexOf("}") + 1);
        organization.setOrganizationType(organizationType);
        LocalDate localDateOfBirth = LocalDate.parse(userString.substring(1, userString.indexOf("}")), dtf);
        organization.setOrganizationBirthday(localDateOfBirth);
        return organization;
    }
}
