package com.era.tofate.controller.feature;

import com.era.tofate.entities.faq.FAQ;
import com.era.tofate.entities.userrole.UserRole;
import com.era.tofate.enums.Role;
import com.era.tofate.exceptions.BadRequestException;
import com.era.tofate.exceptions.ResourceNotFoundException;
import com.era.tofate.repository.faq.FAQRepository;
import com.era.tofate.repository.userrole.UserRoleRepository;
import com.era.tofate.security.CurrentUser;
import com.era.tofate.security.UserPrincipal;
import com.era.tofate.service.user.UserService;
import com.era.tofate.service.userrole.UserRoleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.era.tofate.exceptions.ExceptionConstants.NO_ACCESS;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FAQController {
    private final UserRoleRepository userRoleRepository;
    private final FAQRepository faqRepository;
    private final UserRoleService userRoleService;
    private final UserService userService;

    /**
     *   Creating new FAQ
     * @param userPrincipal - authorized administrator
     * @param faq - FAQ entity
     * @return FAQ entity"
     */
    @PostMapping("/api/admin/createfaq")
    @ApiOperation(value = "Creates FAQ",
            notes = "Returns created FAQ entity")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "Error with access"),
            @ApiResponse(code = 500, message = "Internal server error")
    })

    public ResponseEntity<?> createFAQ(@CurrentUser UserPrincipal userPrincipal, @RequestBody FAQ faq) {
        if(userPrincipal==null)
        {
            throw new BadRequestException(NO_ACCESS);
        } else {
            List<UserRole>   data = userRoleRepository.findAllByUser_Login(userPrincipal.getLogin());

            if (data.get(0).getRole().equals(Role.ADMIN)|| data.get(0).getRole().equals(Role.MANAGER)) {
                return ResponseEntity.ok().body(faqRepository.save(faq));

            } else {
                throw new BadRequestException(NO_ACCESS);
            }
        }
    }

    /**
     *  Get selected FAQ by id
     *
     * @param id - FAQ id
     * @return FAQ Entity
     */
    @GetMapping("/api/faq/{id}")
    @ApiOperation(value = "Get FAQ by given id",
            notes = "Shows selected FAQ by given id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "Error with access"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<?> Getone(@PathVariable Long id) {

        return ResponseEntity.ok().body(faqRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("id not found")));
    }

    /**
     *  Shows all existed FAQs
     *
     * @return FAQ Entity
     */
    @GetMapping("/api/faq")
    @ApiOperation(value = "Get all  existed FAQs",
            notes = "Shows list of  FAQs from database")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "Error with access"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<?> GetAll() {
        return ResponseEntity.ok().body(faqRepository.findAll());
    }

    /**
     *  Update existing FAQ by id
     *
     * @param userPrincipal - authorized administrator
     * @param faq - FAQ entity
     * @param id -  id of FAQ
     * @return FAQ entity by id"
     */
    @PutMapping("/api/faq/editfaq/{id}")
    @ApiOperation(value = "Update selected FAQ by id",
            notes = "Returns FAQ by given id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "Error with access"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<?> editFAQ(@CurrentUser UserPrincipal userPrincipal, @RequestBody FAQ faq, @PathVariable Long id) {
        if(userPrincipal==null)
        {
            throw new BadRequestException(NO_ACCESS);
        } else {
            List<UserRole>   data = userRoleRepository.findAllByUser_Login(userPrincipal.getLogin());

            if (data.get(0).getRole().equals(Role.ADMIN)|| data.get(0).getRole().equals(Role.MANAGER)) {
                return ResponseEntity.ok().body(faqRepository.findById(id)
                        .map(faq1 -> {
                            faq1.setAnswer(faq.getAnswer());
                            faq1.setQuestion(faq.getQuestion());
                            return faqRepository.save(faq1);
                        })
                        .orElseGet(() -> {
                            faq.setId(id);
                            return faqRepository.save(faq);
                        }));
            } else {
                throw new BadRequestException(NO_ACCESS);
            }
        }
    }

    /**
     *  Delete selected FAQ by id
     *
     * @param userPrincipal - authorized user
     * @param id - FAQ id
     * @return FAQ Entity
     */
    @DeleteMapping("/api/faq/deletefaq/{id}")
    @ApiOperation(value = "Delete selected FAQ by id",
            notes = "Returns updated FAQ entity")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "Error with access"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    void deleteFAQ(@PathVariable Long id, @CurrentUser UserPrincipal userPrincipal) {
        if(userPrincipal==null)
        {
            throw new BadRequestException(NO_ACCESS);
        } else {
            List<UserRole> data = userRoleRepository.findAllByUser_Login(userPrincipal.getLogin());
            if (data.get(0).getRole().equals(Role.ADMIN)|| data.get(0).getRole().equals(Role.MANAGER)) {
                faqRepository.deleteById(id);
            } else {
                throw new BadRequestException(NO_ACCESS);
            }
        }
    }
}
