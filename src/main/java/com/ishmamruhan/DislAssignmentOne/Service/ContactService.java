package com.ishmamruhan.DislAssignmentOne.Service;

import com.ishmamruhan.DislAssignmentOne.Entity.ContactEntity.Contact;
import com.ishmamruhan.DislAssignmentOne.Entity.ContactEntity.ContactSearchCriteria;
import com.ishmamruhan.DislAssignmentOne.ExceptionManagement.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public interface ContactService {

    Contact addContact(Contact contact) throws CustomException;

    List<Contact> addMultipleContacts (List<Contact> contacts) throws CustomException;

    List<Contact> contactCSVFileUpload(MultipartFile file) throws CustomException;

    List<Contact> findContacts(ContactSearchCriteria contactSearchCriteria) throws CustomException;

    Contact updateContact(Contact contact) throws CustomException;

    String deleteContact(long id) throws CustomException;

}
