package com.scm.userSeviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.scm.services.ImageService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public String uploadImage(MultipartFile pictureFile) {
        String fileName = UUID.randomUUID().toString(); // Unique filename
        try {
            // Read the file content into a byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(pictureFile.getBytes());
            byte[] data = baos.toByteArray();

            // Upload the image using Cloudinary
            cloudinary.uploader().upload(data, ObjectUtils.asMap("public_id", fileName));

            // Return the URL of the uploaded image
            return this.getUrlFromPublicid(fileName);
        } catch (IOException e) {
            System.out.println("Error uploading image: " + e.getMessage()); // Log error
            return null; // Handle the error appropriately (you might want to throw a custom exception)
        }
    }

    @Override
    public String getUrlFromPublicid(String publicId) {
        // Generate the URL for the uploaded image using the public ID
        return cloudinary.url()
                .publicId(publicId)
                .transformation(
                        new Transformation<>()
                                .height(500)
                                .width(500)
                                .crop("fill")
                )
                .generate(publicId); // Generate the URL
    }
}
