/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.interfaces;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.beecode.toolvendor.controller.AmazonS3Controller;
import java.io.File;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author luisvespa
 */
public interface AwsS3Service {
    
    public static final Logger logger = LoggerFactory.getLogger(AmazonS3Controller.class);
    public static final String SUFFIX = "/";
    public static final String ACCESS_KEY_ID = "AKIAJ76F5XF6OLA4M2UA";
    public static final String SECRET_ACCESS_KEY = "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";
    
    public void uploadFile(String bucketName, String fileName, File file );
    
    public void getBucket(String bucketName);
    
    public List<Bucket> getListBuckets();
    
    public void createFolder(String bucketName, String folderName);
    
    public void deleteFolder(String bucketName, String folderName);
    
    public void deleteBucket(String bucketName);
    
    
}
