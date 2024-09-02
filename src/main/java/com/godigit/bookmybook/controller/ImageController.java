package com.godigit.bookmybook.controller;

import com.godigit.bookmybook.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    ImageService imageService;

    /**
     * Purpose: This API is used to save an image.
     *
     * @param token This is the user token given in a @RequestHeader for authorizing the user.
     * @param logo  This is the image file provided in a @RequestPart.
     * @return ResponseEntity<?> The response entity containing the result of the image save operation.
     *                           With the status code given.
     * @throws IOException If an input or output exception occurred.
     */

    @PostMapping()
    public ResponseEntity<?> saveImage(@RequestHeader String token, @RequestPart MultipartFile logo) throws IOException {
        return new ResponseEntity<>(imageService.addImage(token,logo), HttpStatus.ACCEPTED);
    }

    /**
     * Purpose: This API is used to update an existing image.
     *
     * @param token This is the user token given in a @RequestHeader for authorizing the user.
     * @param logo  This is the new image file provided in a @RequestPart.
     * @param id    The ID of the image to be updated.
     * @return ResponseEntity<?> The response entity containing the result of the image update operation.
     *                           With the status code given.
     * @throws IOException If an input or output exception occurred.
     */

    @PutMapping("/{id}")
    public ResponseEntity<?> updateImage(@RequestHeader String token,@RequestPart MultipartFile logo,@PathVariable long id) throws IOException {
        return new ResponseEntity<>(imageService.updateImage(token,logo,id),HttpStatus.ACCEPTED);
    }

}
