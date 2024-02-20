package com.jee.fileUpload.upload;

import java.io.IOException;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/file")
@RestController
public class FileSendController {
    private RestTemplate restTemplate = new RestTemplate();

    @PostMapping(value = "/process")
    public ResponseEntity<?> sendFile(@RequestPart("file") MultipartFile file) throws IOException {

        String imageUrl = "https://img.freepik.com/free-vector/colorful-letter-gradient-logo-design_474888-2309.jpg";

        ResponseEntity<Resource> responseEntity = restTemplate.exchange(imageUrl, HttpMethod.GET, null, Resource.class);
        Resource imgResource = responseEntity.getBody();
        String img = imgResource.getFilename();
        
        String url = "http://localhost:8080/file/upload";
        byte[] imageBytes = imgResource.getContentAsByteArray();

        MultiValueMap<String, Object> data = new LinkedMultiValueMap<String, Object>();
        
        // Here we 
        ByteArrayResource contentsAsResource = new ByteArrayResource(imageBytes) {
            @Override
            public String getFilename() {
                return "image.jpg"; // Filename has to be returned in order to be able to post.
            }
        };

        data.add("file", contentsAsResource);

        
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(data, requestHeaders);

       
        ResponseEntity<String> responseEntity2 = restTemplate.exchange(url, HttpMethod.POST, requestEntity,String.class);
        return responseEntity2;

    }
}
