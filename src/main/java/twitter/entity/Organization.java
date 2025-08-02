package twitter.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Entity
@Table(name = "organization_info")
@PrimaryKeyJoinColumn(name = "user_id")
public class Organization extends User {

    @Column(name = "organizationname")
    private String organizationName;

    @Column(name = "organizationtype")
    private String organizationType;

    @Column(name = "br_data")
    private LocalDate organizationBirthday;

    public Organization() {
        super();
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public String getOrganizationType() {
        return organizationType;
    }

    public LocalDate getOrganizationBirthday() {
        return organizationBirthday;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public void setOrganizationType(String organizationType) {
        this.organizationType = organizationType;
    }

    public void setOrganizationBirthday(LocalDate organizationBirthday) {
        this.organizationBirthday = organizationBirthday;
    }

    @Override
    public String beauty() {
        return String.format("[Organization]{\n  ID: %d\n  name: %s\n  type: %s\n  birthday: %s\n}", this.id, this.organizationName, this.organizationName, this.organizationBirthday);
    }

    @Override
    public String whatIsYourName() {
        return this.organizationName;
    }
    @Override
    public String toFileString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String registerDate = this.currentDate.format(dateTimeFormatter);
        String birthDate = this.organizationBirthday.format(dateFormatter);
        return String.format("{%d}{%s}{%s}{%s}{%s}{%s}{%s}{%s}", this.id, this.login, this.password, registerDate, this.typeUser, this.organizationName, this.organizationType, birthDate);
    }
}
