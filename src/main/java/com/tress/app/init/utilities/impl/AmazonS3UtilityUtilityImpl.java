package com.tress.app.init.utilities.impl;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.tress.app.init.utilities.AmazonS3Utility;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class AmazonS3UtilityUtilityImpl implements AmazonS3Utility {

    private static String bucketName     = "test-image-container";
    private static String keyName        = "TJES4DPSTA4RTZH3EXNE";
    //private static String uploadFileName = "/Users/home/Downloads/success.jpg";

    @Override
    public void uploadImageDigitalOcean(File file) {

        AWSCredentials credentials = new BasicAWSCredentials( "TJES4DPSTA4RTZH3EXNE", "+1NV5fp37Mj1rgPgv43ZpY7dv2fS4rhNQnPL1bogC3k");
        AmazonS3 s3client = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration( new AwsClientBuilder.EndpointConfiguration("https://nyc3.digitaloceanspaces.com", "nyc3"))
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();


        try {
            System.out.println("Uploading a new object to S3 from a file\n");
            //File file = new File(uploadFileName);
            s3client.putObject(new PutObjectRequest(
                    bucketName, keyName, file));

        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which " +
                    "means your request made it " +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which " +
                    "means the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
    }
}
