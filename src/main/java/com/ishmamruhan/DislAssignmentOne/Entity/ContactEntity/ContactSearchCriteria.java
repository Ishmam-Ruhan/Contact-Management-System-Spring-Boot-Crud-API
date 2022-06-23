package com.ishmamruhan.DislAssignmentOne.Entity.ContactEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ishmamruhan.DislAssignmentOne.Enums.BloodGroupType;
import com.ishmamruhan.DislAssignmentOne.Enums.ContactAddressType;
import com.ishmamruhan.DislAssignmentOne.Enums.GenderType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Objects;

public class ContactSearchCriteria {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;

    private String nationalId;

    private String passport;

    private String jobTitle;

    private String company;

    private String highestEducation;

    private String gender;

    private String bloodGroup;

    private String addressType;

    private String city;
    private String state;
    private String zipcode;

    private Long startAge;

    private Long endAge;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private String startBirthDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private String endBirthDate;

    public boolean isAllFieldNull() {
        Field[] fields = this.getClass().getDeclaredFields();

        for(Field field : fields){
            try {
                if(field.get(this) != null){
                    return false;
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
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

    public String getHighestEducation() {
        return highestEducation;
    }

    public void setHighestEducation(String highestEducation) {
        this.highestEducation = highestEducation;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(GenderType gender) {
        this.gender = gender == null ? null : gender.toString();
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(ContactAddressType addressType) {
        this.addressType = addressType == null ? null : addressType.toString();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Long getStartAge() {
        return startAge;
    }

    public void setStartAge(Long startAge) {
        this.startAge = startAge;
    }

    public Long getEndAge() {
        return endAge;
    }

    public void setEndAge(Long endAge) {
        this.endAge = endAge;
    }

    public String getStartBirthDate() {
        return startBirthDate;
    }

    public void setStartBirthDate(String startBirthDate) {
        this.startBirthDate = startBirthDate;
    }

    public String getEndBirthDate() {
        return endBirthDate;
    }

    public void setEndBirthDate(String endBirthDate) {
        this.endBirthDate = endBirthDate;
    }
}
