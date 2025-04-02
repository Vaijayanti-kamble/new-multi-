package com.example.profiledocument.entity;

import com.google.cloud.firestore.annotation.PropertyName;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfessionDetails {

    @PropertyName("document_id")
    private String documentId;

    @NotEmpty(message = "First name is required")
    @PropertyName("first_name")
    private String firstName;

    @NotEmpty(message = "Middle name is required")
    @PropertyName("middle_name")
    private String middleName;

    @NotEmpty(message = "Last name is required")
    @PropertyName("last_name")
    private String lastName;

    @NotEmpty(message = "Phone number is required")
    @PropertyName("phone_no")
    private String phoneNo;

    @NotEmpty(message = "Email ID is required")
    @PropertyName("email_id")
    private String emailId;

    @NotNull(message = "Created date is required")
    @PropertyName("created_date")
    private String createdDate;

    @NotNull(message = "Updated date is required")
    @PropertyName("updated_date")
    private String updatedDate;

    @NotEmpty(message = "Main profession is required")
    @PropertyName("main_profession")
    private String mainProfession;

    @NotEmpty(message = "Other professions are required")
    @PropertyName("other_professions")
    private String otherProfessions;

    // Getters and Setters with @PropertyName annotations
    @PropertyName("document_id")
    public String getDocumentId() {
        return documentId;
    }

    @PropertyName("document_id")
    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    @PropertyName("first_name")
    public String getFirstName() {
        return firstName;
    }

    @PropertyName("first_name")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @PropertyName("middle_name")
    public String getMiddleName() {
        return middleName;
    }

    @PropertyName("middle_name")
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @PropertyName("last_name")
    public String getLastName() {
        return lastName;
    }

    @PropertyName("last_name")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @PropertyName("phone_no")
    public String getPhoneNo() {
        return phoneNo;
    }

    @PropertyName("phone_no")
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    @PropertyName("email_id")
    public String getEmailId() {
        return emailId;
    }

    @PropertyName("email_id")
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    @PropertyName("created_date")
    public String getCreatedDate() {
        return createdDate;
    }

    @PropertyName("created_date")
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @PropertyName("updated_date")
    public String getUpdatedDate() {
        return updatedDate;
    }

    @PropertyName("updated_date")
    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    @PropertyName("main_profession")
    public String getMainProfession() {
        return mainProfession;
    }

    @PropertyName("main_profession")
    public void setMainProfession(String mainProfession) {
        this.mainProfession = mainProfession;
    }

    @PropertyName("other_professions")
    public String getOtherProfessions() {
        return otherProfessions;
    }

    @PropertyName("other_professions")
    public void setOtherProfessions(String otherProfessions) {
        this.otherProfessions = otherProfessions;
    }
}
