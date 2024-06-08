package com.adventour.web.service.impl;

import com.adventour.web.service.BucketService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;

@Service
public class BucketServiceImpl implements BucketService {

    Logger LOG = LogManager.getLogger(BucketServiceImpl.class);

    @Autowired
    S3Client s3Client;

    @Value("${aws.s3.endpoint}")
    String endpoint;

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";

    private String bucketName = "testBucket";

    @Override
    public ListBucketsResponse getBucketList() {
        LOG.info("getting bucket list... ");
        return s3Client.listBuckets();
    }

    @Override
    public void getObjectFromBucket(String bucketName, String objectName) throws IOException {
        LOG.info("Getting object from bucket: {} with key: {}", bucketName, objectName);

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(objectName)
                .build();

        File localFile = new File(UPLOAD_DIRECTORY + File.separator + objectName);
        try (ResponseInputStream<GetObjectResponse> s3Object = s3Client.getObject(getObjectRequest);
             FileOutputStream fos = new FileOutputStream(localFile)) {

            byte[] readBuffer = new byte[4096];
            int readLength;
            while ((readLength = s3Object.read(readBuffer)) > 0) {
                fos.write(readBuffer, 0, readLength);
            }
        } catch (IOException e) {
            LOG.error("IOException occurred while reading object from S3", e);
            throw e;
        } catch (Exception e) {
            LOG.error("Exception occurred while getting object from S3", e);
            throw new IOException("Error occurred while getting object from S3", e);
        }

        LOG.info("Object successfully downloaded to: {}", localFile.getAbsolutePath());
    }

    @Override
    public void putObjectIntoBucket(String bucketName, String objectName, String filePathToUpload) {

    }

    @Override
    public void createBucket(String bucketName) {

    }

    @Override
    public String uploadFile(MultipartFile multipartFile) throws IOException {
        File file = convertMultiPartToFile(multipartFile);
        String fileName = generateFileName(multipartFile);
        //https://ztxujxoonvnlhrnacclt.supabase.co/storage/v1/object/public
        String fileUrl = "/" + bucketName + "/" + fileName;
        uploadFileToS3Bucket(fileName, file);
        file.delete();
        return fileUrl;
    }

    private void uploadFileToS3Bucket(String fileName, File file) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .acl(ObjectCannedACL.PUBLIC_READ)
                .build();

        try {
            PutObjectResponse putObjectResponse = s3Client.putObject(putObjectRequest, Path.of(file.getAbsolutePath()));
            LOG.info("File uploaded successfully. ETag: {}", putObjectResponse.eTag());
        } catch (Exception e) {
            LOG.error("Exception occurred while uploading file to S3", e);
            throw new RuntimeException("Error occurred while uploading file to S3", e);
        }
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