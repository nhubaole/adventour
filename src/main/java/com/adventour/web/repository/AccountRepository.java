package com.adventour.web.repository;

import com.adventour.web.models.Account;
import com.adventour.web.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Booking> findByNameAccount(String  name);
}
