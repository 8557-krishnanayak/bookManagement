package com.godigit.bookmybook.service;

import com.godigit.bookmybook.dto.DataHolder;
import com.godigit.bookmybook.exception.ResourceAlreadyExistException;
import com.godigit.bookmybook.exception.ResourceNotFoundException;
import com.godigit.bookmybook.exception.UnauthorizedException;
import com.godigit.bookmybook.model.ImageModel;
import com.godigit.bookmybook.repository.ImageRepository;
import com.godigit.bookmybook.util.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ImageService {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    TokenUtility tokenUtility;

    /**
     * Purpose: This method checks for duplication of an uploaded image file.
     *
     * @param logo This is the image file provided in a @RequestPart.
     * @throws NullPointerException If the uploaded file is invalid or empty.
     * @throws ResourceAlreadyExistException If an image with the same name and type already exists in the repository.
     */

    private void checkDuplication(MultipartFile logo)
    {
        if(logo.getOriginalFilename().equals(""))
            throw new NullPointerException("Please upload a valid file");
        List<ImageModel> imageModels = imageRepository.findAll() .stream().filter(image->{
            return image.getImageName()
                    .equalsIgnoreCase(logo.getOriginalFilename())
                    && image.getImageType().equalsIgnoreCase(logo.getContentType());
        }).toList();

        if(!imageModels.isEmpty())
            throw new ResourceAlreadyExistException("Image already exists");
    }

    private void checkAdmin(String token)
    {
        DataHolder dataHolder = tokenUtility.decode(token);
        if(!dataHolder.getRole().equalsIgnoreCase("admin"))
            throw new UnauthorizedException("You are not authorized :-)");
    }

    /**
     * Purpose: This method is used to add a new image to the repository.
     *
     * @param token This is the user token used for authorizing the user.
     *              The user must be an admin to perform this operation.
     * @param logo  This is the image file provided in a @RequestPart.
     * @return ImageModel The response entity containing the newly added ImageModel object.
     * @throws IOException If an input or output exception occurred.
     * @throws NullPointerException If the uploaded file is invalid or empty.
     */
    public ImageModel addImage(String token, MultipartFile logo) throws IOException,NullPointerException {
        checkAdmin(token);
        checkDuplication(logo);
        ImageModel imageModel = new ImageModel();
        imageModel.setImageName(logo.getOriginalFilename());
        imageModel.setImageType(logo.getContentType());
        imageModel.setImageData(logo.getBytes());
        imageRepository.save(imageModel);

        return imageModel;
    }

    /**
     * Purpose: This method is used to retrieve an image by its ID.
     *
     * @param token This is the user token used for authorizing the user.
     *              The user must be an admin to perform this operation.
     * @param id    The ID of the image to be retrieved.
     * @return ImageModel The response entity containing the ImageModel object of the retrieved image.
     * @throws ResourceNotFoundException If the image with the specified ID is not found.
     */

    public ImageModel getImageByID(String token,long id){
        checkAdmin(token);
        return imageRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Image doesn't exists :-)"));
    }

    /**
     * Purpose: This method is used to update an existing image in the repository.
     *
     * @param token   This is the user token used for authorizing the user.
     *                The user must be an admin to perform this operation.
     * @param logo    This is the new image file provided in a @RequestPart.
     * @param imageId The ID of the image to be updated.
     * @return String A message indicating the result of the image update operation.
     * @throws IOException If an input or output exception occurred.
     */
    public String updateImage(String token, MultipartFile logo, long imageId) throws IOException {
        checkDuplication(logo);
        ImageModel update_image = getImageByID(token,imageId);
        update_image.setImageData(logo.getBytes());
        update_image.setImageName(logo.getOriginalFilename());
        update_image.setImageType(logo.getContentType());
        imageRepository.save(update_image);
        return "Updated the image with id "+imageId;
    }
}
