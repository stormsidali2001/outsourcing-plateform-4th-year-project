package com.example.storagemicroservice.API;

import com.example.storagemicroservice.Entities.media;
import com.example.storagemicroservice.Repositories.MediaRepo;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.nio.file.*;
import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
//@RequestMapping("storage")
public class MediaApi {
    @Autowired
    MediaRepo mediaRepo;
    @Autowired
    private ResourceLoader resourceLoader;

//    @PostMapping("/addMedia")
//    public media add_media(@RequestBody media media){
//       return mediaRepo.save(media);
//    }
//
//    @GetMapping("/media/{idm}")
//    public media getMediaById(@PathVariable("idm") Long idm){
//        media media=mediaRepo.findById(idm).get();
//        return media;
//    }


    @GetMapping("/download/{filename}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String filename) throws IOException {
//        get the specified file form filePath location
        String filePath = "F:\\Media\\" + filename;
        File file = new File(filePath);

        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + filePath);
        }

        byte[] fileContents = Files.readAllBytes(file.toPath());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.attachment().filename(filename).build());
        headers.setContentLength(fileContents.length);

        return new ResponseEntity<>(fileContents, headers, HttpStatus.OK);
    }




    @PostMapping("/upload/{type}")
    public ResponseEntity<String> uploadFile(@PathVariable String type , @RequestParam("File") MultipartFile file) {
        try {
///     put the uploaded file in this location(client to server)

            // Generate a unique filename for the uploaded file
            String filename = UUID.randomUUID().toString();
            String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
          if (check_file_extension(extension,type)) {
              if (!StringUtils.isEmpty(extension)) {
                  filename += "." + extension;
              }

              Path filePath = Paths.get("D:\\Media\\" + filename);

              Files.write(filePath, file.getBytes());
              //http://localhost:7777/storage/files/filename
              // Check if the file was successfully saved
              if (Files.exists(filePath)) {
                  media media = new media(null, filename, type);
                  mediaRepo.save(media);
                  return ResponseEntity.ok("http://localhost:7777/storage/files/" + filename);
              } else {
                  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save file.");
              }
          }
            else {
              return ResponseEntity.ok("file with wrong format");
          }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file.");
        }
    }
//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadFile(@RequestParam("File") MultipartFile file) {
//        try {
////            put the uploaded file in this location(client to server)
//            Path filePath = Paths.get("F:\\Media\\" + file.getOriginalFilename());
//            Files.write(filePath, file.getBytes());
//            return ResponseEntity.ok("File uploaded successfully.");
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file.");
//        }
//    }





    @GetMapping("/files/{filename:.+}")
    public String getImage(@PathVariable String filename, HttpServletResponse response) throws IOException {
        File file = new File("D:\\Media\\" + filename);
        if (file.exists()) {
            InputStream inputStream = new FileInputStream(file);
            IOUtils.copy(inputStream,response.getOutputStream());
            response.flushBuffer();
        }
        else
            return "file not found";
        return "";
    }


    private Boolean check_file_extension(String extension,String type_of_file){
        switch (type_of_file) {
            case "cv" -> {
                return Objects.equals(extension, "pdf");
            }
            case "photo" -> {
                List<String> allowedExtensions = List.of("png", "jpeg","jpg");
                return allowedExtensions.contains(extension.toLowerCase());
            }
        }


        return false;
    }
}
