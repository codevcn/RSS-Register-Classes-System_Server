package com.example.demo.repositories;

import com.example.demo.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, String> {
    @Query(
        value = "select u" + " from Account u" + " where u.username = ?1" + " and u.deleted = 0",
        nativeQuery = true
    )
    Account findByUsername(String username);
}
