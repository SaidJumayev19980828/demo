package com.era.tofate.controller.feature;

import com.era.tofate.entities.faq.FAQ;
import com.era.tofate.entities.userrole.UserRole;
import com.era.tofate.entities.virt.Virt;
import com.era.tofate.enums.Role;
import com.era.tofate.exceptions.BadRequestException;
import com.era.tofate.exceptions.ResourceNotFoundException;
import com.era.tofate.payload.faq.FAQRequest;

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
import retrofit2.http.POST;

import java.util.List;
import java.util.Optional;

import static com.era.tofate.exceptions.ExceptionConstants.NO_ACCESS;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FAQController {
    private final FAQRepository faqRepository;
    private final UserService userService;

    /**
     *  Get selected FAQ by id
     *
     * @param faqId - FAQ id
     * @return FAQ Entity
     */
    @GetMapping("/api/faq/{faqId}")
    @ApiOperation(value = "Get FAQ by given id",
            notes = "Shows selected FAQ by given id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "Error with access"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<?> Getone(@PathVariable Long faqId) {

        return ResponseEntity.ok().body(faqRepository.findById(faqId)
                .orElseThrow(() -> new ResourceNotFoundException("id not found")));
    }

    /**
     *  Shows all existed FAQs
     *
     * @return FAQ Entity
     */
    @GetMapping("/api/faq/all")
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
     * @return FAQ entity by id"
     */
    @PostMapping("/api/admin/faq")
    @ApiOperation(value = "Update selected FAQ by id",
            notes = "Returns FAQ by given id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "Error with access"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<?> createOrUpdateFAQ(@RequestBody FAQRequest faqRequest, @CurrentUser UserPrincipal userPrincipal) {
        if (userService.findById(userPrincipal.getId()).isPresent()) {

           FAQ existingFAQ;
            if (faqRequest.getId() != null){
                Optional<FAQ>  optionalFAQ = faqRepository.findById(faqRequest.getId());
                if (optionalFAQ.isPresent()) {
                    existingFAQ = optionalFAQ.get();
                    existingFAQ = faqRequestToFAQ(faqRequest, existingFAQ);
                    existingFAQ = faqRepository.save(existingFAQ);
                    return ResponseEntity.ok(existingFAQ);
                }
            }
            existingFAQ = faqRepository.save(faqRequestToFAQ(faqRequest, new FAQ()));
            return ResponseEntity.ok(existingFAQ);
        } else {
            throw new BadRequestException(NO_ACCESS);
        }
    }

    private FAQ faqRequestToFAQ(FAQRequest faq, FAQ existingFAQ) {
        Optional<String>        answer = Optional.ofNullable(faq.getAnswer());
        Optional<String>        question = Optional.ofNullable(faq.getQuestion());
        answer.ifPresent(existingFAQ::setAnswer);
        question.ifPresent(existingFAQ::setQuestion);
        return existingFAQ;
    }

    /**
     *  Delete selected FAQ by id
     *

     * @param id - FAQ id
     * @return FAQ Entity
     */
    @DeleteMapping("/api/faq/deletefaq")
    @ApiOperation(value = "Delete selected FAQ by id",
            notes = "Returns updated FAQ entity")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "Error with access"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    void deleteFAQ(@PathVariable Long id) {
        faqRepository.deleteById(id);
    }
}
