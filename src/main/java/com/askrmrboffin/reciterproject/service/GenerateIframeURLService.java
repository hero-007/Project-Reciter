package com.askrmrboffin.reciterproject.service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
public class GenerateIframeURLService {

        public String generateIframeUrl(String filename){
            Regions clientRegion = Regions.US_EAST_2;
            String bucketName = "reciter-output-files";
            String stringObjKeyName = filename;

            S3Object fullObject = null;
            String objectURL = null;
            try{
                AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                        .withRegion(clientRegion)
                        .build();
                URL objectUrl = s3Client.getUrl(bucketName, filename);
                System.out.print("Object URL is - "+objectUrl.toString());
                objectURL = objectUrl.toString();
            }catch(Exception e){
                e.printStackTrace();
            }

            if(objectURL == null)
                return null;

            String generatedIframe = "<iframe src=\""+objectURL+"\" style=\"border:0px #ffffff none;\" name=\"myiFrame\" scrolling=\"no\" frameborder=\"0\" marginheight=\"0px\" marginwidth=\"0px\" height=\"100px\" width=\"200px\" allowfullscreen></iframe>";
            return generatedIframe;
        }
}
