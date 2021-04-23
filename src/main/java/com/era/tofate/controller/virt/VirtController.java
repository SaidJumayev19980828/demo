package com.era.tofate.controller.virt;

import com.era.tofate.entities.virt.Virt;
import com.era.tofate.enums.Sex;
import com.era.tofate.exceptions.BadRequestException;
import com.era.tofate.payload.virt.VirtResponse;
import com.era.tofate.security.CurrentUser;
import com.era.tofate.security.UserPrincipal;
import com.era.tofate.service.user.UserService;
import com.era.tofate.service.virt.VirtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.era.tofate.exceptions.ExceptionConstants.NO_ACCESS;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VirtController {
    private final UserService userService;
    private final VirtService virtService;
    /**
     *
     * getting List of Virt by Gender and by Page
     *
     * @param userPrincipal - authorized user
     * @param sex - Gender Enum
     * @param page - number of page
     * @param pageSize - Size of page
     * @return Map<String,List<VirtResponse>>
     */
    @GetMapping("/api/virt/all")
    public ResponseEntity<?> gender(@CurrentUser UserPrincipal userPrincipal,
                                    @RequestParam Sex sex,
                                    @RequestParam int page,
                                    @RequestParam int pageSize){
        if (userService.findById(userPrincipal.getId()).isPresent()) {
            Page<Virt> virtsByGender = virtService.findAllBySex(sex,page,pageSize);
            return ResponseEntity.ok(virtResponsesByGender(virtsByGender));
        }else {
            throw new BadRequestException(NO_ACCESS);
        }
    }
    /**
     *
     * Create new Virt
     *
     * @param userPrincipal - authorized user
     * @param virt - Virt Entity
     * @return VirtResponse Entity
     */
    @PostMapping("/api/virt/new")
    public ResponseEntity<?> createVirt(@CurrentUser UserPrincipal userPrincipal, Virt virt){
        if (userService.findById(userPrincipal.getId()).isPresent()) {
            virtService.save(virt);
            return ResponseEntity.ok(new VirtResponse(virt.getId(),virt.getSex()));
        }else {
            throw new BadRequestException(NO_ACCESS);
        }
    }
    public Map<String, Object> virtResponsesByGender(Page<Virt> virts){
        List<VirtResponse> virtResponse = new ArrayList<>();
        virts.forEach(virt -> virtResponse.add(new VirtResponse(virt)));
        Map<String, Object> response = new HashMap<>();
        response.put("virts",virtResponse);
        return response;
    }
}
