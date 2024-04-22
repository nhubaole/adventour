package com.adventour.web.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TourGuideDto {

    private Long id;

    @NotEmpty(message = "Name of tour guide can't be empty")
    private String nameTourGuide;

    private boolean isMale;

    private LocalDate dateOfBirth;

    private String addressTourGuide;

    @NotEmpty(message = "Phone of tour guide can't be empty")
    private String phoneNumber;


}
