package com.tress.app.init.utilities;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface AmazonS3Utility {

    /**
     * DIGITAL OCEAN
     */

    void uploadImageDigitalOcean(File file);
}
