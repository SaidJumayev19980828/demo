package com.era.tofate.controller.virt;

import com.era.tofate.entities.publication.Publication;
import com.era.tofate.entities.virt.Virt;
import com.era.tofate.enums.Sex;
import com.era.tofate.exceptions.BadRequestException;
import com.era.tofate.payload.virt.VirtRequest;
import com.era.tofate.payload.virt.VirtResDto;
import com.era.tofate.payload.virt.VirtResponse;
import com.era.tofate.payload.virt.VirtResponseDetailed;
import com.era.tofate.security.CurrentUser;
import com.era.tofate.security.UserPrincipal;
import com.era.tofate.service.user.UserService;
import com.era.tofate.service.virt.VirtService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.era.tofate.exceptions.ExceptionConstants.NO_ACCESS;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VirtController {
    private final UserService userService;
    private final VirtService virtService;

    /**
     * getting List of Virt by Gender and by Page
     *
     * @param userPrincipal - authorized user
     * @param sex - Gender Enum
     * @param page - number of page
     * @param pageSize - Size of page
     * @return Map<String,List<VirtResponse>>
     */
    @GetMapping("/api/virt/all")
    @ApiOperation(value = "Get virts with paging",
            notes = "Returns list of virts by given paging. By gender if given")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "Error with access"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<?> gender(@CurrentUser UserPrincipal userPrincipal,
                                    @RequestParam(required = false) Sex sex,
                                    @RequestParam int page,
                                    @RequestParam int pageSize){
        if (userService.findById(userPrincipal.getId()).isPresent()) {
            if (sex == null){
                Page<Virt> virtsByGender = virtService.findAll(page, pageSize);
                return ResponseEntity.ok(virtResponsesByGender(virtsByGender));
            }
            Page<Virt> virtsByGender = virtService.findAllBySex(sex, page, pageSize);

            return ResponseEntity.ok(virtResponsesByGender(virtsByGender));
        } else {
            throw new BadRequestException(NO_ACCESS);
        }
    }

     /**
     * getting information about Virt by id
     *
     * @param userPrincipal - authorized user
     * @param virtId - id of Virt
     * @return Virt - Virt Entity
     */
    @GetMapping("/api/virt")
    @ApiOperation(value = "Get virt by id",
            notes = "Returns virt by given id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "Error with access"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<?> byId(@CurrentUser UserPrincipal userPrincipal, @RequestParam Long virtId){
        if (userService.findById(userPrincipal.getId()).isPresent()) {
            Virt virt = virtService.findById(virtId).get();
            virt.getPublications().forEach(publication -> publication.setVirt(null));
            virt.getPublications().forEach(publication -> {});
            return ResponseEntity.ok(new VirtResDto(virt));
        } else {
            throw new BadRequestException(NO_ACCESS);
        }
    }

    /**
     * Create or Update Virt
     *
     * @param userPrincipal - authorized user
     * @param virtRequest - Virt Entity
     * @return VirtResponse Entity
     */
    @PostMapping("/api/admin/virt")
    @ApiOperation(value = "Create or Update virt ",
            notes = "Returns created or updated virt")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "Error with access"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<?> createOrUpdateVirt(@CurrentUser UserPrincipal userPrincipal, @RequestBody VirtRequest virtRequest){
        if (userService.findById(userPrincipal.getId()).isPresent()) {
            System.out.println("QQQ"+userService.findById(userPrincipal.getId()).isPresent());

            Virt existingVirt;
            if (virtRequest.getId() != null){
                Optional<Virt> optionalVirt = virtService.findById(virtRequest.getId());
                if (optionalVirt.isPresent()) {
                    existingVirt = optionalVirt.get();
                    existingVirt = virtRequestToVirt(virtRequest, existingVirt);
                    existingVirt = virtService.save(existingVirt);
                    return ResponseEntity.ok(existingVirt);
                }
            }
            existingVirt = virtService.save(virtRequestToVirt(virtRequest, new Virt()));
            return ResponseEntity.ok(existingVirt);
        } else {
            throw new BadRequestException(NO_ACCESS);
        }
    }
    public Map<String, Object> virtResponsesByGender(Page<Virt> virts){
        List<VirtResponse> virtResponse = new ArrayList<>();
        virts.forEach(virt -> virtResponse.add(new VirtResponse(virt)));
        Map<String, Object> response = new HashMap<>();
        response.put("virts", virtResponse);
        return response;
    }
    private Virt virtRequestToVirt(VirtRequest virtRequest, Virt virt){
        Optional<String>        name = Optional.ofNullable(virtRequest.getName());
        Optional<String>        about = Optional.ofNullable(virtRequest.getAbout());
        Optional<String>        avatar = Optional.ofNullable(virtRequest.getAvatar());
        Optional<Long>          age = Optional.ofNullable(virtRequest.getAge());
        Optional<String>        lkLableOne = Optional.ofNullable(virtRequest.getLkLableOne());
        Optional<Sex>           sex = Optional.ofNullable(virtRequest.getSex());
        Optional<Integer>       publicPostQuantity = Optional.ofNullable(virtRequest.getPublicPostQuantity());
        Optional<Integer>       subscribersQuantity = Optional.ofNullable(virtRequest.getSubscribersQuantity());
        Optional<Integer>       subscriptionQuantity = Optional.ofNullable(virtRequest.getSubscriptionQuantity());

        List<Publication>        publications = virt.getPublications();
        name.ifPresent(virt::setName);
        about.ifPresent(virt::setAbout);
        avatar.ifPresent(virt::setAvatar);
        age.ifPresent(virt::setAge);
        lkLableOne.ifPresent(virt::setLkLableOne);
        sex.ifPresent(virt::setSex);
        publicPostQuantity.ifPresent(virt::setPublicPostQuantity);
        subscribersQuantity.ifPresent(virt::setSubscribersQuantity);
        subscriptionQuantity.ifPresent(virt::setSubscriptionQuantity);
        virt.setPublications(publications);
        return virt;
    }
}
