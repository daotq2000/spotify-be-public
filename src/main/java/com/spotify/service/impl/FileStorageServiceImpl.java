package com.spotify.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.spotify.dto.response.CloudinaryResponse;
import com.spotify.exception.FileStorageException;
import com.spotify.exception.MyFileNotFoundException;
import com.spotify.ultils.Constraints;
import com.spotify.ultils.FileStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
public class FileStorageServiceImpl {
    private final Path fileStorageLocation;
    @Value("${apiCloud.client_id}")
    private String clientKey;
    @Value("${apiCloud.client_secret}")
    private String secretKey;
    @Value("${apiCloud.cloud_name}")
    private String cloudName;
    @Autowired
    public FileStorageServiceImpl(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
    public String uploadToCloudinary(MultipartFile file) throws Exception {
        Map map = null;
        Map result;
        Cloudinary cloudinary = new Cloudinary();
        cloudinary.config.apiKey = clientKey;
        cloudinary.config.apiSecret= secretKey;
        cloudinary.config.cloudName= cloudName;

        if(file.getOriginalFilename().indexOf(".mp3") != -1){
           map = cloudinary.uploader().upload(file.getBytes(),ObjectUtils.asMap("resource_type","video"));
        }else{
            map= cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        }
        result= cloudinary.api().resources(map);
        ArrayList responseList = (ArrayList) result.get("resources");
        Map res = (Map) responseList.get(0);
        return  res.get("secure_url").toString();
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException | MyFileNotFoundException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }
}
