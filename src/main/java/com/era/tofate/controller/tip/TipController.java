package com.era.tofate.controller.tip;

import com.era.tofate.entities.tip.Tip;
import com.era.tofate.exceptions.BadRequestException;
import com.era.tofate.security.CurrentUser;
import com.era.tofate.security.UserPrincipal;
import com.era.tofate.service.tip.TipService;
import com.era.tofate.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.era.tofate.exceptions.ExceptionConstants.NO_ACCESS;
import static com.era.tofate.exceptions.ExceptionConstants.TIP_NOT_FOUND;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TipController {
    private final TipService tipService;
    private final UserService userService;

    /**
     * Creating and editing a hint
     *
     * @param userPrincipal - authorized administrator
     * @param tip           - the entity that describes the hint
     * @return the entity that describes the hint
     */
    @PostMapping("/api/admin/tip")
    public ResponseEntity<?> createAndEditTip(@CurrentUser UserPrincipal userPrincipal,
                                              @RequestBody Tip tip) {
        if (userService.findById(userPrincipal.getId()).isPresent()) {
            if (tip.getId() != null) {
                Tip requestedTip = tipService.getOne(tip.getId());
                tip.setSequenceNumber(requestedTip.getSequenceNumber());
                tip.setTipText(requestedTip.getTipText());
            }

            tipService.save(tip);

            return ResponseEntity.ok(tip);
        } else {
            throw new BadRequestException(NO_ACCESS);
        }
    }

    /**
     * Getting all the tips
     *
     * @return list of tips
     */
    @GetMapping("/api/user/tip")
    public ResponseEntity<?> getTips() {
        return ResponseEntity.ok(tipService.findAllByOrderBySequenceNumberAsc());
    }

    /**
     * Getting a tip by id
     *
     * @param id - tip id
     * @return the entity that describes the tip
     */
    @GetMapping("/api/user/tip/{id}")
    public ResponseEntity<?> getTips(@PathVariable("id") Long id) {
        if (tipService.findById(id).isPresent()) {
            return ResponseEntity.ok(tipService.findById(id).get());
        } else {
            throw new BadRequestException(TIP_NOT_FOUND);
        }
    }
}
