package com.example.WithRun.service;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.WithRun.domain.User;
import com.example.WithRun.repository.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Service
@NoArgsConstructor
@PropertySource("classpath:application-aws.yml")
public class ImageService {
    private AmazonS3 s3Client;

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Autowired
    UserRepository userRepository;

    @PostConstruct
    public void setS3Client(){
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey,this.secretKey);

        s3Client= AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region).build();
    }

    public String upload(MultipartFile file, String userId)throws IOException{
        String filename = file.getOriginalFilename();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());
        InputStream inputStream= file.getInputStream();
        String filepath = userId+ "/" + filename;

        s3Client.putObject(new PutObjectRequest(bucket,filepath,inputStream,objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        User user = userRepository.findByUserID(userId);

        return s3Client.getUrl(bucket,filepath).toString();
    }

    public void delete(String filename, String userId){
        bucket +="/" + userId;
        s3Client.deleteObject(new DeleteObjectRequest(bucket, filename));

    }

    public String getFilePath(MultipartFile file, String userId){

        return userId + "/" + file.getOriginalFilename();
    }

}
