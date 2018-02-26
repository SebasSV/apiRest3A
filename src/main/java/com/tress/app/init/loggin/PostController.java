package com.tress.app.init.loggin;


import com.tress.app.init.config.CustomUserDetails;
import com.tress.app.init.entities.post.Post;
import com.tress.app.init.entities.post.service.PostService;
import com.tress.app.init.entities.user.service.UserService;
import com.tress.app.init.utilities.AmazonS3Utility;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private AmazonS3Utility amazonS3Utility;

    @GetMapping(value="/posts")
    public List<Post> posts(){
        return postService.getAllPosts();
    }

    @PostMapping(value="/post")
    public String publishPost(HttpServletRequest request){
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ServletFileUpload.isMultipartContent(request);


        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = null;
        if(multiRequest.getFileMap() != null && !multiRequest.getFileMap().values().isEmpty()) {
            multipartFile = multiRequest.getFileMap().values().iterator().next();
            System.out.print("algo");
        }

        try {
            if(multipartFile != null){
                amazonS3Utility.uploadImageDigitalOcean(this.multipartToFile(multipartFile));
            }
        }
        catch (IllegalStateException iae){
            iae.printStackTrace();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }


//        if(post.getCreatedDate() == null)
//            post.setCreatedDate(new Date());
//        post.setUse r(userService.getUser(userDetails.getUsername()));
//        postService.insert(post);
        return "Post was published";
    }

    private File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException
    {
        File convFile = new File( "//tmp/" + multipart.getOriginalFilename());
        multipart.transferTo(convFile);
        return convFile;
    }


}
