package com.era.tofate.controller.preloaderlabel;

import com.era.tofate.entities.preloaderlabel.PreloaderLabel;
import com.era.tofate.exceptions.BadRequestException;
import com.era.tofate.security.CurrentUser;
import com.era.tofate.security.UserPrincipal;
import com.era.tofate.service.preloaderlabel.PreloaderLabelService;
import com.era.tofate.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.era.tofate.exceptions.ExceptionConstants.NO_ACCESS;
import static com.era.tofate.exceptions.ExceptionConstants.PRELOADER_LABEL_NOT_FOUND;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PreloaderLabelController {
    private final UserService userService;
    private final PreloaderLabelService preloaderLabelService;

    /**
     * Creating and editing a preloader label
     *
     * @param userPrincipal  - authorized administrator
     * @param preloaderLabel - the entity that describes the preloader label
     * @return the entity that describes the preloader label
     */
    @PostMapping("/api/admin/preloaderlabel")
    public ResponseEntity<?> createAndEditPreloaderLabel(@CurrentUser UserPrincipal userPrincipal,
                                                         @RequestBody PreloaderLabel preloaderLabel) {
        if (userService.findById(userPrincipal.getId()).isPresent()) {
            if (preloaderLabel.getId() != null) {
                PreloaderLabel requestedPreloaderLabel = preloaderLabelService.getOne(preloaderLabel.getId());
                preloaderLabel.setLabelText(requestedPreloaderLabel.getLabelText());
            }

            preloaderLabelService.save(preloaderLabel);

            return ResponseEntity.ok(preloaderLabel);
        } else {
            throw new BadRequestException(NO_ACCESS);
        }
    }

    /**
     * Getting all the preloader labels
     *
     * @return list of preloader labels
     */
    @GetMapping("/api/user/preloaderlabel")
    public ResponseEntity<?> getTips() {
        return ResponseEntity.ok(preloaderLabelService.getAll());
    }

    /**
     * Getting a preloader label by id
     *
     * @param id - preloader label id
     * @return the entity that describes the preloader label
     */
    @GetMapping("/api/user/preloaderlabel/{id}")
    public ResponseEntity<?> getTips(@PathVariable("id") Long id) {
        if (preloaderLabelService.findById(id).isPresent()) {
            return ResponseEntity.ok(preloaderLabelService.findById(id).get());
        } else {
            throw new BadRequestException(PRELOADER_LABEL_NOT_FOUND);
        }
    }
}
