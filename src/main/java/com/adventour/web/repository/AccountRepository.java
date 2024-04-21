package com.adventour.web.repository;

import com.adventour.web.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {
    public Optional<Account> findByName(String name);

}
