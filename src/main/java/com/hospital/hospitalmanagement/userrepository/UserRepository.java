package com.hospital.hospitalmanagement.userrepository;

import com.hospital.hospitalmanagement.userentity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
