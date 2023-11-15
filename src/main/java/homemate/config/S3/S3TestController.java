package homemate.config.S3;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class S3TestController {

    private final S3Service s3Service;



    @DeleteMapping("/deleteFileList")
    public ResponseEntity<?> deleteFileList(@RequestParam List<String> fileNameList) {
        s3Service.deleteFileList(fileNameList);
        return ResponseEntity.ok(fileNameList);
    }

    @DeleteMapping("/deleteFile")
    public ResponseEntity<String> deleteFile(@RequestParam String fileName) {
        s3Service.deleteFile(fileName);
        return ResponseEntity.ok(fileName);
    }

    @GetMapping("/getFile")
    public ResponseEntity<?> getFile(@RequestParam String fileName) {
        return ResponseEntity.ok(s3Service.getFile(fileName));
    }

//    @GetMapping("/getFileList")
//    public ResponseEntity<?> getFileList(@RequestParam List<String> fileNameList) {
//        return ResponseEntity.ok(s3Service.getFileList(fNameList));
//    }

    @GetMapping("/getFileList")
    public ResponseEntity<?> getFileList(@RequestParam String folderName) {
        return ResponseEntity.ok(s3Service.getFileList(folderName));
    }


}
