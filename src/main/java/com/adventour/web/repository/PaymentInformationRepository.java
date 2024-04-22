package com.adventour.web.repository;

import com.adventour.web.models.Booking;
import com.adventour.web.models.PaymentInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentInformationRepository extends JpaRepository<PaymentInformation, Long> {
    Optional<PaymentInformation> findById(Long id);
}