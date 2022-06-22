package com.ishmamruhan.DislAssignmentOne.Dao;

import com.ishmamruhan.DislAssignmentOne.Entity.ContactEntity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepo extends JpaRepository<Contact, Long>,CustomContactRepo {

    // Note:  querybuilder,specification: have to learn

    //@Query("SELECT c FROM Contact c WHERE ?1")
    //List<Contact> findContactsWithParam(@Param("q") String q);
}
