/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beecode.toolvendor.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.beecode.toolvendor.controller.AmazonS3Controller;
import com.beecode.toolvendor.interfaces.AwsS3Service;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author luisvespa
 */
public class AmazonS3Service implements AwsS3Service {
    
    // credentials object identifying user for authentication
    // user must have AWSConnector and AmazonS3FullAccess for 
    // this example to work
    AWSCredentials credentials = new BasicAWSCredentials(
                        ACCESS_KEY_ID, 
                        SECRET_ACCESS_KEY);
    // create a client connection based on credentials
    AmazonS3 s3client;
            
    public AmazonS3Service() {
        // create a client connection based on credentials
        s3client = new AmazonS3Client(credentials);
    }

    @Override
    public void getBucket(String bucketName) {
        try {
            s3client.getBucketAcl(bucketName);
        } catch (Exception e) {
            System.out.println("Error AmazonS3Service "+ e.getMessage());
        }
    }

    @Override
    public List<Bucket> getListBuckets() {
        try {
            // list buckets
            for (Bucket bucket : s3client.listBuckets()) {
                    System.out.println(" - " + bucket.getName());
            }
            return s3client.listBuckets();
        } catch (Exception e) {
            System.out.println("Error AmazonS3Service "+ e.getMessage());
            return null;
        }
    }
    
    @Override
    public void deleteBucket(String bucketName) {
        try {
            s3client.deleteBucket(bucketName);
        } catch (Exception e) {
            System.out.println("Error AmazonS3Service "+ e.getMessage());
        }
    }
    
    @Override
    public void createFolder(String bucketName, String folderName) {
        try {
            // create meta-data for your folder and set content-length to 0
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(0);
            // create empty content
            InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
            // create a PutObjectRequest passing the folder name suffixed by /
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,
                            folderName + SUFFIX, emptyContent, metadata);
            // send request to S3 to create folder
            s3client.putObject(putObjectRequest);
        } catch (Exception e) {
            System.out.println("Error AmazonS3Service "+ e.getMessage());
        }
        
    }

    @Override
    public void deleteFolder(String bucketName, String folderName) {
        try {
            List<S3ObjectSummary> fileList = 
				s3client.listObjects(bucketName, folderName).getObjectSummaries();
            for (S3ObjectSummary file : fileList) {
                    s3client.deleteObject(bucketName, file.getKey());
            }
            s3client.deleteObject(bucketName, folderName);
        } catch (Exception e) {
            System.out.println("Error AmazonS3Service "+ e.getMessage());
        }
    }

    @Override
    public void uploadFile(String bucketName, String fileName, File file) {
        try {
            s3client.putObject(new PutObjectRequest(bucketName, fileName, 
				file)
				.withCannedAcl(CannedAccessControlList.PublicReadWrite));
        } catch (Exception e) {
            System.out.println("Error AmazonS3Service "+ e.getMessage());
        }
    }

    
}
