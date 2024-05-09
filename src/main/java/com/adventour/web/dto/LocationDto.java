package com.adventour.web.dto;

import com.adventour.web.models.Image;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {
    private Long id;

    @NotEmpty(message = "Name of location can't be empty")
    private String nameLocation;

    @NotEmpty(message = "Address of location can't be empty")
    private String address;
    private String description;

    private List<Image> images;

}
