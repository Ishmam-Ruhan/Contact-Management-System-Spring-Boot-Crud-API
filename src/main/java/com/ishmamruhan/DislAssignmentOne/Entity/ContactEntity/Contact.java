package com.ishmamruhan.DislAssignmentOne.Entity.ContactEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.ishmamruhan.DislAssignmentOne.Entity.AddressEntity.Address;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;

@Entity
@Table(name = "contacts")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"fullName","age","createdAt","updatedAt"}, allowGetters = true)
public class Contact{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Email field cannot be null!")
    @Email(message = "Please provide a valid e-mail address.")
    private String email;

    @NotNull(message = "Firstname field cannot be null!")
    @Size(min = 2, message = "Minimum firstname length is 2.")
    private String firstName;

    private String lastName;

    @Transient
    private String fullName;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Address> addressList;

    @NotNull(message = "Birthdate field cannot be null!")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date birthDate;

    @Transient
    private long age;

    @NotNull(message = "Job Title field cannot be null!")
    private String jobTitle;

    @NotNull(message = "Company field cannot be null!")
    private String company;

    private String highestLevelEducation;

    private String bloodGroup;

    @NotNull(message = "Gender field cannot be null!")
    private String gender;

    private String nationalId;

    private String passportNo;

    private Boolean isBlocked = false;

    @CreatedDate
    @Temporal(TemporalType.DATE)
    @Column(nullable = false,updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date createdAT;

    @LastModifiedDate
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date updatedAt;

    public Contact() {
    }

    public Contact(Long id, String email, String firstName, String lastName, String fullName, List<Address> addressList, Date birthDate, long age, String jobTitle, String company, String highestLevelEducation, String bloodGroup, String gender, String nationalId, String passportNo, Boolean isBlocked, Date createdAT, Date updatedAt) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.addressList = addressList;
        this.birthDate = birthDate;
        this.age = age;
        this.jobTitle = jobTitle;
        this.company = company;
        this.highestLevelEducation = highestLevelEducation;
        this.bloodGroup = bloodGroup;
        this.gender = gender;
        this.nationalId = nationalId;
        this.passportNo = passportNo;
        this.isBlocked = isBlocked;
        this.createdAT = createdAT;
        this.updatedAt = updatedAt;
    }

    @PostLoad
    public void afterLoadedEntity(){
        this.setFullName(this.getFirstName() +" "+this.getLastName());
        this.setAge(calculateAge(this.getBirthDate()));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getHighestLevelEducation() {
        return highestLevelEducation;
    }

    public void setHighestLevelEducation(String highestLevelEducation) {
        this.highestLevelEducation = highestLevelEducation;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public Boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(Boolean blocked) {
        isBlocked = blocked;
    }

    public Date getCreatedAT() {
        return createdAT;
    }

    public void setCreatedAT(Date createdAT) {
        this.createdAT = createdAT;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", birthDate=" + birthDate +
                ", age=" + age +
                ", jobTitle='" + jobTitle + '\'' +
                ", company='" + company + '\'' +
                ", highestLevelEducation='" + highestLevelEducation + '\'' +
                ", bloodGroup='" + bloodGroup + '\'' +
                ", gender='" + gender + '\'' +
                ", nationalId='" + nationalId + '\'' +
                ", passportNo='" + passportNo + '\'' +
                ", isActive=" + isBlocked +
                ", createdAT=" + createdAT +
                ", updatedAt=" + updatedAt +
                '}';
    }

    private long calculateAge(Date birthDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(birthDate);

        LocalDate todayLocalDate = LocalDate.now();
        LocalDate birthLocalDate  = LocalDate.ofInstant(calendar.toInstant(), ZoneId.systemDefault());

        Period calculatedDate = Period.between(birthLocalDate,todayLocalDate);


        return calculatedDate.getYears();
    }
}
