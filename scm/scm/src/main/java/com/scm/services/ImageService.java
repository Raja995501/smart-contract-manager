package com.scm.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    String uploadImage(MultipartFile pictureFile);
    String getUrlFromPublicid(String public_id); 
}