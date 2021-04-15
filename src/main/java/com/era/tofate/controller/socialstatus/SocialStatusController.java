package com.era.tofate.controller.socialstatus;

import com.era.tofate.entities.socialstatus.SocialStatus;
import com.era.tofate.exceptions.InternalServerException;
import com.era.tofate.exceptions.ResourceNotFoundException;
import com.era.tofate.service.socialstatus.SocialStatusService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import static com.era.tofate.exceptions.ExceptionConstants.PHOTO_NOT_FOUND;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SocialStatusController {

    private final SocialStatusService socialStatusService;

    /**
     * Getting all possible social statuses
     * @return List of all social statuses
     */
    @GetMapping("/api/user/socialstatus")
    public ResponseEntity<?> getSocialStatus() {
        return ResponseEntity.ok(socialStatusService.getAll());
    }

    /**
     *
     * Getting a social status photo
     * @param id - Social status ID
     * @param response social status photo
     */
    @GetMapping(path = "/api/user/socialstatus/{id}")
    public void getPhoto(@PathVariable("id") Long id,
                         HttpServletResponse response) {
        if (socialStatusService.findById(id).isPresent()) {
            SocialStatus currentSocialStatus = socialStatusService.findById(id).get();

            try {
                ByteArrayInputStream imageStream = new ByteArrayInputStream(currentSocialStatus.getImage());
                response.setContentType(currentSocialStatus.getMimeType());
                IOUtils.copy(imageStream, response.getOutputStream());
                response.flushBuffer();
            } catch (NullPointerException | IOException ex) {
                throw new InternalServerException(ex.getMessage());
            }
        } else {
            throw new ResourceNotFoundException(PHOTO_NOT_FOUND);
        }
    }
}
