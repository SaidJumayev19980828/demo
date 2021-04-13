package com.era.tofate.controller.aboutapp;

import com.era.tofate.entities.aboutapp.AboutApp;
import com.era.tofate.exceptions.BadRequestException;
import com.era.tofate.payload.AboutAppRequest;
import com.era.tofate.payload.AboutAppResponse;
import com.era.tofate.security.CurrentUser;
import com.era.tofate.security.UserPrincipal;
import com.era.tofate.service.aboutapp.AboutAppService;
import com.era.tofate.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.era.tofate.exceptions.ExceptionConstants.ABOUT_APP_NOT_FOUND;
import static com.era.tofate.exceptions.ExceptionConstants.NO_ACCESS;
import static com.era.tofate.utils.Constants.DEFAULT_ABOUT_APP_ID;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AboutAppController {
    private final UserService userService;
    private final AboutAppService aboutAppService;

    /**
     * Creating and editing "about app" text
     *
     * @param userPrincipal - authorized administrator
     * @param request       - the "about app" text
     * @return the entity that describes the "about app"
     */
    @PostMapping("/api/admin/aboutapp")
    public ResponseEntity<?> createAndEditAboutApp(@CurrentUser UserPrincipal userPrincipal,
                                                   @RequestBody AboutAppRequest request) {
        if (userService.findById(userPrincipal.getId()).isPresent()) {
            AboutApp aboutApp;

            if (aboutAppService.count() == 1) {
                aboutApp = aboutAppService.getOne(DEFAULT_ABOUT_APP_ID);
            } else if (aboutAppService.count() == 0) {
                aboutApp = new AboutApp();
            } else {
                throw new BadRequestException(ABOUT_APP_NOT_FOUND);
            }

            aboutApp.setAboutAppText(request.getAboutAppText());
            aboutAppService.save(aboutApp);

            return ResponseEntity.ok(new AboutAppResponse(aboutApp));
        } else {
            throw new BadRequestException(NO_ACCESS);
        }
    }

    /**
     * Getting an "about app" text
     *
     * @return the entity that describes the "about app"
     */
    @GetMapping("/api/user/aboutapp")
    public ResponseEntity<?> getAboutApp() {
        if (aboutAppService.findById(DEFAULT_ABOUT_APP_ID).isPresent()) {
            return ResponseEntity.ok(new AboutAppResponse(aboutAppService.findById(DEFAULT_ABOUT_APP_ID).get()));
        } else {
            throw new BadRequestException(ABOUT_APP_NOT_FOUND);
        }
    }
}
