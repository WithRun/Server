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
import com.example.WithRun.dto.ImageDTO;
import com.example.WithRun.repository.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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

    public List<ImageDTO> upload(List<MultipartFile> files, String userId)throws IOException{
        List<ImageDTO> imageDTOList = new ArrayList<>();

        files.forEach(file -> {
            String fileName = file.getOriginalFilename();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());
            try(InputStream inputStream= file.getInputStream()){
                String filepath = userId+ "/" + fileName;
                s3Client.putObject(new PutObjectRequest(bucket,filepath,inputStream,objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));

//                s3Client.getUrl(bucket,filepath).toString();
//                fileNameList.add(s3Client.getUrl(bucket,filepath).toString());
                ImageDTO imageDTO = ImageDTO.builder()
                        .filename(fileName)
                        .url(s3Client.getUrl(bucket,filepath).toString()).build();
                imageDTOList.add(imageDTO);
            }catch (IOException e){
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Upload failed");
            }
        });

        return imageDTOList;
    }

    public ImageDTO uploadFreePostImage(MultipartFile file, String userId)throws IOException{

        String fileName = file.getOriginalFilename();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());
        try(InputStream inputStream= file.getInputStream()){
            String filepath = userId+ "/" + fileName;
            s3Client.putObject(new PutObjectRequest(bucket,filepath,inputStream,objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

//                s3Client.getUrl(bucket,filepath).toString();
//                fileNameList.add(s3Client.getUrl(bucket,filepath).toString());
            return ImageDTO.builder()
                    .filename(fileName)
                    .url(s3Client.getUrl(bucket,filepath).toString()).build();
        }catch (IOException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Upload failed");
        }
    }




    public void delete(List<String> filenameList, String userId){
        for(String filename : filenameList) {
            bucket = "withrun";
            bucket += "/" + userId;
            s3Client.deleteObject(new DeleteObjectRequest(bucket, filename));
        }
        bucket = "withrun";
    }


    public void deleteFreePostImage(String filename, String userId, String localOrServer){
        bucket = "withrun";
        bucket +=  "/" + localOrServer + "/" + userId;
        s3Client.deleteObject(new DeleteObjectRequest(bucket, filename));
        bucket = "withrun";
    }

    public String getFilePath(MultipartFile file, String userId){

        return userId + "/" + file.getOriginalFilename();
    }

}
