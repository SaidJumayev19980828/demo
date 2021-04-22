package com.era.tofate.controller.virt;

import com.era.tofate.entities.virt.Virt;
import com.era.tofate.exceptions.BadRequestException;
import com.era.tofate.payload.virt.VirtRequest;
import com.era.tofate.payload.virt.VirtRequestByGender;
import com.era.tofate.payload.virt.VirtResponse;
import com.era.tofate.security.CurrentUser;
import com.era.tofate.security.UserPrincipal;
import com.era.tofate.service.user.UserService;
import com.era.tofate.service.virt.VirtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.era.tofate.exceptions.ExceptionConstants.*;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VirtController {
    private final UserService userService;
    private final VirtService virtService;

    @PostMapping("/api/virt/byGender")
    public ResponseEntity<?> gender(@CurrentUser UserPrincipal userPrincipal, VirtRequestByGender virtRequest){
        if (userService.findById(userPrincipal.getId()).isPresent()) {
            if (virtRequest.getSex().getSex().equals("male") || virtRequest.getSex().getSex().equals("female")){
                Page<Virt> virtsByGender = virtService.findAllBySex(virtRequest.getSex(),virtRequest.getPage(),virtRequest.getPageSize());
                return ResponseEntity.ok(virtResponsesByGender(virtsByGender));
            }else {
                throw new BadRequestException(GENDER_IS_NOT_VALID);
            }
        }else {
            throw new BadRequestException(NO_ACCESS);
        }
    }
    @PostMapping("/api/virt/new")
    public ResponseEntity<?> createVirt(@CurrentUser UserPrincipal userPrincipal, Virt virt){
        if (userService.findById(userPrincipal.getId()).isPresent()) {
            virtService.save(virt);
            return ResponseEntity.ok(new VirtResponse(virt.getId(),virt.getSex()));
        }else {
            throw new BadRequestException(NO_ACCESS);
        }
    }
    public List<VirtResponse> virtResponsesByGender(Page<Virt> virts){
        List<VirtResponse> virtResponse = new ArrayList<>();
        virts.forEach(virt -> virtResponse.add(new VirtResponse(virt)));
        return virtResponse;
    }
}
