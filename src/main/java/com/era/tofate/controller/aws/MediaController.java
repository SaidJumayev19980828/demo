package com.era.tofate.controller.aws;

import com.era.tofate.service.aws.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

@RestController
public class MediaController {

    @Autowired
    private Config config;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam(value = "file") MultipartFile[] file) {
        Arrays.asList(file).stream().forEach(multipartFile -> config.uploadFile(multipartFile));
        return  "File uploaded";
    }

    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        return new ResponseEntity<>(config.deleteFile(fileName), HttpStatus.OK);
    }

    @GetMapping("/generateurl/{fileName}")
    public String downloadFile(@PathVariable String fileName) {
        String mediaUrl= "https://video_box.hb.bizmrg.com/"+fileName;
         return mediaUrl;
    }
}