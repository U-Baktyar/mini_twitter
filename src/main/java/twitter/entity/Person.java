package twitter.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "person_info")
@PrimaryKeyJoinColumn(name = "user_id")
public class Person extends User {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "br_data")
    private LocalDate dateOfBirth;

    public Person() {
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String beauty() {
        return String.format("[Person] {\n   ID:%d\n   Login: %s \n   name: %s %s\n   dateOfBirth: %s\n}", this.id, this.login, this.firstName, this.lastName, this.dateOfBirth);
    }

    @Override
    public String whatIsYourName() {
        return this.firstName + " " + this.lastName;
    }

    @Override
    public String toFileString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String registerDate = this.currentDate.format(dateTimeFormatter);
        String birthDate = this.dateOfBirth.format(dateFormatter);
        return String.format("{%d}{%s}{%s}{%s}{%s}{%s}{%s}{%s}", this.id, this.login, this.password, registerDate, this.typeUser, this.firstName, this.lastName, birthDate);
    }
}
