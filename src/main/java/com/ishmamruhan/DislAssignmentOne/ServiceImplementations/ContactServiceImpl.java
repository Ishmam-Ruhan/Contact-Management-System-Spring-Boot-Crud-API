package com.ishmamruhan.DislAssignmentOne.ServiceImplementations;

import com.ishmamruhan.DislAssignmentOne.Dao.AddressRepo;
import com.ishmamruhan.DislAssignmentOne.Dao.ContactRepo;
import com.ishmamruhan.DislAssignmentOne.Entity.ContactEntity.Contact;
import com.ishmamruhan.DislAssignmentOne.Entity.ContactEntity.ContactSearchCriteria;
import com.ishmamruhan.DislAssignmentOne.ExceptionManagement.CustomException;
import com.ishmamruhan.DislAssignmentOne.Service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {

    public static final Logger logger = LoggerFactory.getLogger(ContactServiceImpl.class);

    @Autowired
    private ContactRepo contactRepo;

    @Autowired
    private AddressRepo addressRepo;

    @Override
    public Contact addContact(Contact contact) throws CustomException {
        Contact ourContact = null;

        try {
            ourContact = contactRepo.save(contact);
        }catch (Exception ex){
            throw new CustomException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        return ourContact;
    }

    @Override
    @Transactional(rollbackFor = {CustomException.class})
    public List<Contact> addMultipleContacts(List<Contact> contacts) throws CustomException {
        List<Contact> ourContactList = null;
        try {
            contacts.forEach(contact -> addContact(contact));
        }catch (Exception ex){
            throw new CustomException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        return ourContactList;
    }

    @Override
    public List<Contact> contactCSVFileUpload(MultipartFile file) throws CustomException {
        List<Contact> contacts = null;

        return addMultipleContacts(contacts);
    }

    @Override
    public List<Contact> getAllContacts(Boolean blockStatus) {

        List<Contact> allContacts = contactRepo.findAll();

        if(blockStatus == null) return allContacts;

        return allContacts
                .stream()
                .filter(contact -> contact.isBlocked() == blockStatus)
                .collect(Collectors.toList());
    }

    @Override
    public List<Contact> findContacts(ContactSearchCriteria contactSearchCriteria) throws CustomException {

        if(contactSearchCriteria.isAllFieldNull()){
            throw new CustomException(HttpStatus.BAD_REQUEST, "Please enter minimum one search criteria!");
        }

        List<Contact> contacts = contactRepo.getContactByCustomQuery(contactSearchCriteria);

        Long ageStart = contactSearchCriteria.getStartAge();
        Long ageEnd = contactSearchCriteria.getEndAge();

        if(ageStart == null && ageEnd == null)return contacts;

        if(ageStart != null && ageEnd != null){
            return contacts.stream()
                    .filter(contact ->
                            contact.getAge() >= ageStart
                                    && contact.getAge() <= ageEnd)
                    .collect(Collectors.toList());
        }

        long singleAgeParam = ageStart != null ? ageStart : ageEnd;

        return contacts.stream()
                .filter(contact -> contact.getAge() == singleAgeParam)
                .collect(Collectors.toList());
    }

    @Override
    public Contact updateContact(Contact contact) throws CustomException {

        Contact ourContact = getContactById(contact.getId());

        Contact updatedContact = new Contact();

        BeanUtils.copyProperties(contact, updatedContact);

        updatedContact.setId(ourContact.getId());

        return addContact(updatedContact);
    }


    @Override
    public Boolean blockCheck(Long id) throws CustomException {
        Contact contact = getContactById(id);

        return contact.isBlocked();
    }

    @Override
    public String deleteContact(Long id) throws CustomException {

        Contact contact = getContactById(id);

        try{
            contactRepo.deleteById(contact.getId());
        }catch (Exception ex){
            throw new CustomException(HttpStatus.BAD_REQUEST, "Cannot perform this operation right now! Try again later.");
        }

        return null;
    }

    @Override
    public String blockContact(Long id) throws CustomException {
        Contact contact = getContactById(id);

        contact.setBlocked(true);

        addContact(contact);

        return "Contact Blocked Success!";
    }

    @Override
    public String removeBlockedContact(Long id) throws CustomException {
        Contact contact = getContactById(id);

        contact.setBlocked(false);

        addContact(contact);

        return "Contact Unblocked Success!";
    }


    private Contact getContactById(long id) throws CustomException{

        Contact contact = contactRepo.findById(id).orElseThrow(()-> new CustomException(HttpStatus.NOT_FOUND, "No contact found with id: "+id));

        return contact;
    }



}
