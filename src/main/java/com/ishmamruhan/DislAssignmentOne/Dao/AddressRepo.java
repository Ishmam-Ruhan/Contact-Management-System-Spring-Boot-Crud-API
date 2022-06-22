package com.ishmamruhan.DislAssignmentOne.Dao;

import com.ishmamruhan.DislAssignmentOne.Entity.AddressEntity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepo extends JpaRepository<Address, Long> {
}
