package com.jee.fileUpload.upload;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/file")
@RestController
public class FileUploadController {

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(@RequestPart("file") MultipartFile file){
        int x = 0;
        return file.getOriginalFilename();
    }

   
    
}
