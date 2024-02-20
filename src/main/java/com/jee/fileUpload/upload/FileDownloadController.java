package com.jee.fileUpload.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RequestMapping("/file")
@RestController
public class FileDownloadController {

    

    @GetMapping(value = "/download")
    public ResponseEntity<Resource> download() throws IOException{

        RestTemplate restTemplate = new RestTemplate();
        String url = "https://img.freepik.com/free-vector/colorful-letter-gradient-logo-design_474888-2309.jpg";

        ResponseEntity<Resource> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, Resource.class);

        save(responseEntity.getBody());

        return responseEntity;
    }

    private void save(Resource resource) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(".").getFile() + "/test.jpg");
        if (file.createNewFile()) {
            System.out.println("File is created!");
        } else {
            System.out.println("File already exists.");
        }

        
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(resource.getContentAsByteArray());
        }

    }

   
    
}
