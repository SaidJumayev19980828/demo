package com.era.tofate.controller.virt;

import com.era.tofate.entities.virt.Virt;
import com.era.tofate.exceptions.BadRequestException;
import com.era.tofate.payload.AboutAppResponse;
import com.era.tofate.payload.virt.VirtRequest;
import com.era.tofate.payload.virt.VirtResponse;
import com.era.tofate.security.CurrentUser;
import com.era.tofate.security.UserPrincipal;
import com.era.tofate.service.user.UserService;
import com.era.tofate.service.virt.VirtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.era.tofate.exceptions.ExceptionConstants.*;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VirtController {
    private final UserService userService;
    private final VirtService virtService;

    @PostMapping("/api/virt/gender")
    public ResponseEntity<?> gender(@CurrentUser UserPrincipal userPrincipal, VirtRequest virtRequest){
        if (userService.findById(userPrincipal.getId()).isPresent()) {
            if (virtService.findById(virtRequest.getId()).isPresent()){
                if (virtRequest.getSex().getSex().equals("male") || virtRequest.getSex().getSex().equals("female")){
                    Virt virt = new Virt();
                    virt.setId(virtRequest.getId());
                    virt.setSex(virtRequest.getSex());
                    virtService.save(virt);
                    return ResponseEntity.ok(new VirtResponse(virt.getId(),virt.getSex()));
                }else {
                    throw new BadRequestException(GENDER_IS_NOT_VALID);
                }
            }else {
                throw new BadRequestException(VIRT_NOT_FOUND);
            }
        }else {
            throw new BadRequestException(NO_ACCESS);
        }
    }
    @PostMapping("/api/virt/new")
    public ResponseEntity<?> createVirt(@CurrentUser UserPrincipal userPrincipal, VirtRequest virtRequest){
        if (userService.findById(userPrincipal.getId()).isPresent()) {
            if (virtService.findById(virtRequest.getId()).isPresent()){
                Virt virt = new Virt();
                virt.setId(virtRequest.getId());
                //TODO create new Virt
                virtService.save(virt);
                return ResponseEntity.ok(new VirtResponse(virt.getId(),virt.getSex()));
            }else {
                //TODO update Virt
                throw new BadRequestException(VIRT_NOT_FOUND);
            }
        }else {
            throw new BadRequestException(NO_ACCESS);
        }
    }
}
