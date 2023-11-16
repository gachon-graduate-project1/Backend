package homemate.config.S3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    public String uploadFile(MultipartFile multipartFile) {

        // forEach 구문을 통해 multipartFiles 리스트로 넘어온 파일들을 순차적으로 fileNameList 에 추가
        String fileName = createFileName(multipartFile.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());

        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
        }
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    public List<String> uploadFileList(List<MultipartFile> multipartFiles) {
        List<String> fileNameList = new ArrayList<>();

        // forEach 구문을 통해 multipartFiles 리스트로 넘어온 파일들을 순차적으로 fileNameList 에 추가
        multipartFiles.forEach(file -> {
            fileNameList.add(uploadFile(file));
        });
        return fileNameList;
    }


    public void deleteFile(String fileName) {
        if (amazonS3.doesObjectExist(bucket, fileName)) {
            amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
        } else throw new ResponseStatusException(HttpStatus.FORBIDDEN, "파일이 없습니다");
        System.out.println(bucket);
    }

    public void deleteFileList(List<String> fileNameList) {
        fileNameList.forEach(this::deleteFile);
    }

//    public List<String> getFileList(List<String> fileNameList) {
//        List<String> fileUriList = new ArrayList<>();
//        fileNameList.forEach(file-> {
//            fileUriList.add(getFile(file));
//        });
//        return fileUriList;
//    }

//    public List<String> getFolderList(String folderName) {
//        ListObjectsV2Request req = new ListObjectsV2Request().withBucketName(bucket).withPrefix(folderName);
//        ListObjectsV2Result result;
//        List<String> fileUriList = new ArrayList<>();
//
//        do {
//            result = amazonS3.listObjectsV2(req);
//
//            for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
//                String fileName = objectSummary.getKey();
//                fileUriList.add(getFile(fileName));
//            }
//            String token = result.getNextContinuationToken();
//            req.setContinuationToken(token);
//        } while(result.isTruncated());
//
//        return fileUriList;
//    }



    public List<String> getFolderList(String folderName) {
        ListObjectsV2Request req = new ListObjectsV2Request().withBucketName(bucket).withPrefix(folderName);
        ListObjectsV2Result result;
        List<String> fileUriList = new ArrayList<>();

        do {
            result = amazonS3.listObjectsV2(req);

            for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
                String fileName = objectSummary.getKey();
                if (!fileName.endsWith(".DS_Store")) {
                    fileUriList.add(getFile(fileName));
                }
            }
            String token = result.getNextContinuationToken();
            req.setContinuationToken(token);
        } while(result.isTruncated());

        if(fileUriList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return fileUriList;
    }







    public String getFile(String fileName) {
        try{
            return amazonS3.getUrl(bucket, fileName).toString();
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "파일이 없습니다");
        }
    }


    // 먼저 파일 업로드시, 파일명을 난수화하기 위해 UUID 를 활용하여 난수를 돌린다.
    public String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    // file 형식이 잘못된 경우를 확인하기 위해 만들어진 로직이며, 파일 타입과 상관없이 업로드할 수 있게 하기위해, "."의 존재 유무만 판단하였습니다.
    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일" + fileName + ") 입니다.");
        }
    }
}
