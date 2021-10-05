package com.askrmrboffin.reciterproject.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.askrmrboffin.reciterproject.model.UserAudioFiles;
import com.askrmrboffin.reciterproject.repository.UserAudioFilesRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class S3FileUploadService {

    @Autowired
    UserAudioFilesRepository userAudioFilesRepository;

    public boolean uploadSampleFileToS3Bucket(){
        Regions clientRegion = Regions.US_EAST_2;
        String bucketName = "reciter-output-files";
        String stringObjKeyName = "sample-string-123";
        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .build();

            s3Client.putObject(bucketName, stringObjKeyName, "Uploaded String Object");
        }catch (AmazonServiceException e){
            e.printStackTrace();
            return false;
        }catch (SdkClientException e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public byte[] downloadSampleFileFromS3Bucket(String filename){
        Regions clientRegion = Regions.US_EAST_2;
        String bucketName = "reciter-output-files";
        String stringObjKeyName = filename;

        S3Object fullObject = null;

        try{
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .build();

            // Get an object and print its contents.
            System.out.println("Downloading an object");
            fullObject = s3Client.getObject(new GetObjectRequest(bucketName, stringObjKeyName));

            /**
            S3ObjectInputStream inputStream = fullObject.getObjectContent();
            FileUtils.copyInputStreamToFile(inputStream, new File("C:/Udacity Java Web Nanodegree/Personal Projects/reciterproject/output/"+filename));
            System.out.println("Content-Type: " + fullObject.getObjectMetadata().getContentType());
            **/

            byte[] downloadedFile = fullObject.getObjectContent().readAllBytes();
            return downloadedFile;
        }catch(AmazonServiceException e){
            e.printStackTrace();
        }catch(SdkClientException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public boolean deleteSampleFileFromS3Bucket(String filename) {
        Regions clientRegion = Regions.US_EAST_2;
        String bucketName = "reciter-output-files";
        String stringObjKeyName = filename;

        try{
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .build();

            s3Client.deleteObject(bucketName,stringObjKeyName);
            UserAudioFiles audioFile = userAudioFilesRepository.findAudioFileByName(filename);
            userAudioFilesRepository.deleteById(audioFile.getFileId());
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
