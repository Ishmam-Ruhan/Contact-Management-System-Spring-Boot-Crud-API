package com.ishmamruhan.DislAssignmentOne.Dao;


import com.ishmamruhan.DislAssignmentOne.Entity.ContactEntity.Contact;
import com.ishmamruhan.DislAssignmentOne.Entity.ContactEntity.ContactSearchCriteria;

import java.util.List;

public interface CustomContactRepo {
    List<Contact> getContactByCustomQuery(ContactSearchCriteria contactSearchCriteria);
}
