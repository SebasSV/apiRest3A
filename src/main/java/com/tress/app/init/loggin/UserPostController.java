package com.tress.app.init.loggin;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.AwsChunkedEncodingInputStream;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.tress.app.init.config.CustomUserDetails;
import com.tress.app.init.entities.post.Post;
import com.tress.app.init.entities.userPost.dao.UserPostDao;
import com.tress.app.init.entities.userPost.report.UserPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class UserPostController {

    @Autowired
    private UserPostDao userPostDao;

    @Autowired
    private TokenStore tokenStore;

    @GetMapping(value="/userPost")
    public List<UserPost> posts(){
        return userPostDao.findAll();
    }

    @GetMapping(value="/userPost/id")
    public List<UserPost> post_id(){

        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(customUserDetails == null){
            return null;
        }

        return userPostDao.findByUserId(customUserDetails.getId());
    }

    @GetMapping("/logouts")
    public void logout(@RequestParam(value = "access_token") String accessToken) throws IOException{

//        this.uploagImageDigitalOcean();

        tokenStore.removeAccessToken(tokenStore.readAccessToken(accessToken));
    }


    private static String bucketName     = "test-image-container";
    private static String keyName        = "TJES4DPSTA4RTZH3EXNE";
    private static String uploadFileName = "/Users/home/Downloads/success.jpg";

    private static void uploagImageDigitalOcean() throws IOException {

        AWSCredentials credentials = new BasicAWSCredentials( "TJES4DPSTA4RTZH3EXNE", "+1NV5fp37Mj1rgPgv43ZpY7dv2fS4rhNQnPL1bogC3k");
        AmazonS3 s3client = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration( new AwsClientBuilder.EndpointConfiguration("https://nyc3.digitaloceanspaces.com", "nyc3"))
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();


        try {
            System.out.println("Uploading a new object to S3 from a file\n");
            File file = new File(uploadFileName);
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
