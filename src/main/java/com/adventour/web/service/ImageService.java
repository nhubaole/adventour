package com.adventour.web.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    public String uploadImage(MultipartFile imageFile) throws IOException;

    @Transactional
    public byte[] getImage(String imageName);

}
