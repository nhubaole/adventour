package com.adventour.web.service.impl;

import com.adventour.web.service.BucketService;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

@Service
public class BucketServiceImpl implements BucketService {

    Logger LOG = LogManager.getLogger(BucketServiceImpl.class);

    @Autowired
    AmazonS3 s3Client;

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";

    private String bucketName = "testBucket";

    @Value("${aws.s3.endpoint-url}")
    private String endpointUrl;


    @Override
    public List<Bucket> getBucketList() {
        LOG.info("getting bucket list... ");
        return s3Client.listBuckets();
    }

    @Override
    public boolean validateBucket(String bucketName) {
        List<Bucket> bucketList = getBucketList();
        LOG.info("Bucket list:"+bucketList);
        return bucketList.stream().anyMatch(m -> bucketName.equals(m.getName()));
    }

    @Override
    public void getObjectFromBucket(String bucketName, String objectName) throws IOException {
        S3Object s3Object = s3Client.getObject(bucketName, objectName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        FileOutputStream fos = new FileOutputStream(new File("opt/test/v1/"+objectName));
        byte[] read_buf = new byte[1024];
        int read_len = 0;
        while ((read_len = inputStream.read(read_buf)) > 0) {
            fos.write(read_buf, 0, read_len);
        }
        inputStream.close();
        fos.close();
    }

    @Override
    public void createBucket(String bucketName) {
        s3Client.createBucket(bucketName);
    }

    @Override
    public void putObjectIntoBucket(String bucketName, String objectName, String filePathToUpload) {
        try {
            s3Client.putObject(bucketName, objectName, new File(filePathToUpload));
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
    }

    @Override
    public String uploadFile(MultipartFile multipartFile) throws IOException {
        File file = convertMultiPartToFile(multipartFile);
        String fileName = generateFileName(multipartFile);
        String fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
        uploadFileToS3Bucket(fileName, file);
        file.delete();
        return fileUrl;
    }

    private void uploadFileToS3Bucket(String fileName, File file) {
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File tempFile = Files.createTempFile("upload_", file.getOriginalFilename()).toFile();
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(file.getBytes());
        }
        return tempFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

}