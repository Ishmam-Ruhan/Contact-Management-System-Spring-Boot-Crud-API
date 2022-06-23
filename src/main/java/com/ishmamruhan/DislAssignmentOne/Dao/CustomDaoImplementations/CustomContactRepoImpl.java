package com.ishmamruhan.DislAssignmentOne.Dao.CustomDaoImplementations;

import com.ishmamruhan.DislAssignmentOne.Dao.CustomContactRepo;
import com.ishmamruhan.DislAssignmentOne.Entity.ContactEntity.Contact;
import com.ishmamruhan.DislAssignmentOne.Entity.ContactEntity.ContactSearchCriteria;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Repository
public class CustomContactRepoImpl implements CustomContactRepo {

    private final EntityManager entityManager;

    private final CriteriaBuilder  criteriaBuilder;

    public CustomContactRepoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    @Override
    public List<Contact> getContactByCustomQuery(ContactSearchCriteria contactSearchCriteria) {

        CriteriaQuery<Contact> criteriaQuery = criteriaBuilder.createQuery(Contact.class);

        Root<Contact> criteriaRoot = criteriaQuery.from(Contact.class);

        Predicate criteriaPredicate = getPredicate(criteriaRoot, contactSearchCriteria);
        criteriaQuery.where(criteriaPredicate);

        TypedQuery<Contact> typedQuery = entityManager.createQuery(criteriaQuery);

        return typedQuery.getResultList();
    }

    private Predicate getPredicate(Root<Contact> criteriaRoot,
                                   ContactSearchCriteria contactSearchCriteria) {

        List<Predicate> predicateList = new ArrayList<>();

        if(Objects.nonNull(contactSearchCriteria.getId())){
            predicateList.add(
                    criteriaBuilder.equal(criteriaRoot.get("id"),contactSearchCriteria.getId())
            );
        }

        if(Objects.nonNull(contactSearchCriteria.getFirstname())){
            predicateList.add(
                    criteriaBuilder.like(
                            criteriaBuilder.lower(criteriaRoot.get("firstName"))
                            ,"%"+contactSearchCriteria.getFirstname().toLowerCase().trim()+"%")
            );
        }
        if(Objects.nonNull(contactSearchCriteria.getLastname())){
            predicateList.add(
                    criteriaBuilder.like(
                            criteriaBuilder.lower(criteriaRoot.get("lastName"))
                            ,"%"+contactSearchCriteria.getLastname().toLowerCase().trim()+"%")
            );
        }
        if(Objects.nonNull(contactSearchCriteria.getEmail())){
            predicateList.add(
                    criteriaBuilder.like(
                            criteriaBuilder.lower(criteriaRoot.get("email"))
                            ,"%"+contactSearchCriteria.getEmail().toLowerCase().trim()+"%")
            );
        }

        if(Objects.nonNull(contactSearchCriteria.getNationalId())){
            predicateList.add(
                    criteriaBuilder.like(criteriaRoot.get("nationalId"),"%"+contactSearchCriteria.getNationalId().trim()+"%")
            );
        }

        if(Objects.nonNull(contactSearchCriteria.getPassport())){
            predicateList.add(
                    criteriaBuilder.like(criteriaRoot.get("passportNo"),"%"+contactSearchCriteria.getPassport().trim()+"%")
            );
        }

        if(Objects.nonNull(contactSearchCriteria.getJobTitle())){
            predicateList.add(
                    criteriaBuilder.like(
                            criteriaBuilder.lower(criteriaRoot.get("jobTitle"))
                            ,"%"+contactSearchCriteria.getJobTitle().toLowerCase().trim()+"%")
            );
        }

        if(Objects.nonNull(contactSearchCriteria.getCompany())){
            predicateList.add(
                    criteriaBuilder.like(
                            criteriaBuilder.lower(criteriaRoot.get("company"))
                            ,"%"+contactSearchCriteria.getCompany().toLowerCase().trim()+"%")
            );
        }

        if(Objects.nonNull(contactSearchCriteria.getHighestEducation())){
            predicateList.add(
                    criteriaBuilder.like(
                            criteriaBuilder.lower(criteriaRoot.get("highestLevelEducation"))
                            ,"%"+contactSearchCriteria.getHighestEducation().toLowerCase().trim()+"%")
            );
        }

        if(Objects.nonNull(contactSearchCriteria.getGender())){
            predicateList.add(
                    criteriaBuilder.equal(
                            criteriaBuilder.lower(criteriaRoot.get("gender"))
                            ,contactSearchCriteria.getGender().toLowerCase().trim())
            );
        }

        if(Objects.nonNull(contactSearchCriteria.getBloodGroup())){
            predicateList.add(
                    criteriaBuilder.equal(
                            criteriaBuilder.lower(criteriaRoot.get("bloodGroup"))
                            ,contactSearchCriteria.getBloodGroup().toLowerCase().trim())
            );
        }

        if(Objects.nonNull(contactSearchCriteria.getBlocked())){
            predicateList.add(
                    criteriaBuilder.equal(criteriaRoot.get("isBlocked"), contactSearchCriteria.getBlocked())
            );
        }

        if(Objects.nonNull(contactSearchCriteria.getCity())){
            predicateList.add(
                    criteriaBuilder.like(
                            criteriaBuilder.lower(criteriaRoot.join("addressList").get("city"))
                            ,"%"+contactSearchCriteria.getCity().toLowerCase().trim()+"%")
            );
        }
        if(Objects.nonNull(contactSearchCriteria.getState())){
            predicateList.add(
                    criteriaBuilder.like(
                            criteriaBuilder.lower(criteriaRoot.join("addressList").get("state"))
                            ,"%"+contactSearchCriteria.getState().toLowerCase().trim()+"%")
            );
        }
        if(Objects.nonNull(contactSearchCriteria.getZipcode())){
            predicateList.add(
                    criteriaBuilder.like(criteriaRoot.join("addressList").get("zipCode"),"%"+contactSearchCriteria.getZipcode().trim()+"%")
            );
        }

        if(Objects.nonNull(contactSearchCriteria.getStartBirthDate()) &&
                Objects.nonNull(contactSearchCriteria.getEndBirthDate())){

            Date startDate, endDate;

            try {
                startDate = new SimpleDateFormat("dd-MM-YYYY").parse(contactSearchCriteria.getStartBirthDate().trim());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            try {
                endDate = new SimpleDateFormat("dd-MM-YYYY").parse(contactSearchCriteria.getEndBirthDate().trim());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            predicateList.add(
                    criteriaBuilder.between(criteriaRoot.get("birthDate"),startDate,endDate)
            );
        }
        else if(Objects.nonNull(contactSearchCriteria.getStartBirthDate()) ||
                Objects.nonNull(contactSearchCriteria.getEndBirthDate())){
            String dateString =
                    contactSearchCriteria.getStartBirthDate() == null ? contactSearchCriteria.getEndBirthDate().trim() : contactSearchCriteria.getStartBirthDate().trim();

            Date exactDate;

            try {
                exactDate = new SimpleDateFormat("dd-MM-yyyy").parse(dateString);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            predicateList.add(
                    criteriaBuilder.equal(criteriaRoot.get("birthDate"), exactDate)
            );
        }


        return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
    }


}
