package com.adventour.web.service.impl;

import com.adventour.web.models.Image;
import com.adventour.web.models.ImageUtils;
import com.adventour.web.repository.ImageRepository;
import com.adventour.web.service.ImageService;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.zip.DataFormatException;

@Service
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository) { this.imageRepository = imageRepository;}

    @Override
    public String uploadImage(MultipartFile imageFile)  throws IOException {
       Image image = Image.builder()
               .name(imageFile.getOriginalFilename())
               .type(imageFile.getContentType())
               .imageData(imageFile.getBytes())
               .build();

       imageRepository.save(image);

       if(image.getId() !=null){
           return "Success";
       }
       return null;

    }

    @Override
    @Transactional
    public byte[] getImage(String imageName) {
        Optional<Image> dbImage =  imageRepository.findByName(imageName);
//        return dbImage.map(image -> {
//            try {
//                return ImageUtils.decompressImage(image.getImageData());
//            } catch (DataFormatException | IOException exception) {
//                throw new ContextedRuntimeException("Error downloading an image", exception)
//                        .addContextValue("Image ID",  image.getId())
//                        .addContextValue("Image name", imageName);
//            }
//        }).orElse(null);
        assert dbImage.orElse(null) != null;
        return dbImage.orElse(null).getImageData();

    }

}
