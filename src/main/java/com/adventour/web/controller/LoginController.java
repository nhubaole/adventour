package com.adventour.web.controller;

import com.adventour.web.dto.LocationDto;
import com.adventour.web.models.Location;
import com.adventour.web.service.ImageService;
import com.adventour.web.service.LocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adventour.web.dto.CustomerDto;
import com.adventour.web.service.CustomerService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
public class LoginController {


    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private ImageService imageService;
    @Autowired
    public LoginController( ImageService imageService ) {
        this.imageService = imageService;
    }

    @GetMapping("/")
    public String home(Model model){
        return "/index";
    }

    @PostMapping("/uploadImageIntoDb")
    public ResponseEntity<String> uploadImageIntoDb (@RequestParam("image")MultipartFile image) throws IOException{
        String response = imageService.uploadImage(image);
        logger.info(image.getName());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/getImage/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName){
        byte[] imageData = imageService.getImage(imageName);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(imageData);
    }
}
