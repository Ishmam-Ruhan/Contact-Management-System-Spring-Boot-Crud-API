package com.ishmamruhan.DislAssignmentOne.ServiceImplementations;

import com.ishmamruhan.DislAssignmentOne.Dao.AddressRepo;
import com.ishmamruhan.DislAssignmentOne.Dao.ContactRepo;
import com.ishmamruhan.DislAssignmentOne.Entity.ContactEntity.Contact;
import com.ishmamruhan.DislAssignmentOne.Entity.ContactEntity.ContactSearchCriteria;
import com.ishmamruhan.DislAssignmentOne.ExceptionManagement.CustomException;
import com.ishmamruhan.DislAssignmentOne.Helpers.CsvToContactObjects;
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
import java.util.Map;
import java.util.Optional;
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
        List<Contact> contacts = CsvToContactObjects.processAndGetContacts(file);

        return addMultipleContacts(contacts);
    }

    @Override
    public List<Contact> findContacts(ContactSearchCriteria contactSearchCriteria) throws CustomException {

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

        Contact ourContact = getContactById(contact.getContactId());

        Contact updatedContact = new Contact();

        BeanUtils.copyProperties(contact, updatedContact);

        updatedContact.setContactId(ourContact.getContactId());

        logger.debug("Updated Contact: "+contact);
        logger.debug("Modified Contact: "+updatedContact);

        return addContact(updatedContact);
    }

    @Override
    public String deleteContact(long id) throws CustomException {

        Contact contact = getContactById(id);

        try{
            contactRepo.deleteById(id);
        }catch (Exception ex){
            throw new CustomException(HttpStatus.BAD_REQUEST, "Cannot perform this operation right now! Try again later.");
        }

        return null;
    }

    private Contact getContactById(long id) throws CustomException{
        Optional<Contact> contact = contactRepo.findById(id);

        if(contact == null){
            throw new CustomException(HttpStatus.NOT_FOUND, "No contact found with id: "+id);
        }

        return contact.get();
    }


    private String queryBuilder(Map<String, Object> params){

        StringBuilder queryParam = new StringBuilder("");

        boolean isFirstParam = true;

        for(String key: params.keySet()){

            if(!isFirstParam){
                queryParam.append(" AND ");
            }
            if(isFirstParam)isFirstParam=false;

            if(key.equals("contactId")){
                queryParam.append("c."+key+" = "+params.get(key));
                continue;
            }

            if(key.equals("firstName") || key.equals("lastName") || key.equals("email")){
                queryParam.append("c."+key+" LIKE "+"%"+params.get(key)+"%");
                continue;
            }

            if(key.equals("birthDateStart") && params.get("birthDateEnd") != null){
                queryParam.append("c.birthDate BETWEEN "+params.get("birthDateStart")+" AND "+params.get("birthDateEnd"));
                continue;
            }
            else if(key.equals("birthDateStart") && params.get("birthDateEnd") == null){
                queryParam.append("c.birthDate = "+params.get("birthDateStart"));
                continue;
            }
            else if(key.equals("birthDateEnd") && params.get("birthDateStart") == null){
                queryParam.append("c.birthDate = "+params.get("birthDateEnd"));
                continue;
            }
        }

        return params.size() > 1 ? queryParam.substring(4) : queryParam.toString();
    }


}
