package com.nighthawk.spring_portfolio.mvc.image;
package com.nighthawk.spring_portfolio.mvc.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/image")
public class ImageApiController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        String message = imageService.storeFile(file);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}