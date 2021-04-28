package com.era.tofate.controller.publication;

import com.era.tofate.entities.photo.Photo;
import com.era.tofate.entities.publication.Publication;
import com.era.tofate.entities.virt.Virt;
import com.era.tofate.enums.FileMediaType;
import com.era.tofate.exceptions.BadRequestException;
import com.era.tofate.payload.file.FilePath;
import com.era.tofate.payload.publication.PublicationPhoto;
import com.era.tofate.security.CurrentUser;
import com.era.tofate.security.UserPrincipal;
import com.era.tofate.service.file.StorageService;
import com.era.tofate.service.publication.PublicationService;
import com.era.tofate.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

import static com.era.tofate.exceptions.ExceptionConstants.NO_ACCESS;
import static com.era.tofate.exceptions.ExceptionConstants.PUBLICATION_NOT_FOUND;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PublicationController {
    private final PublicationService service;
    private final UserService userService;
    private final StorageService storageService;

    /**
     * Create new Publication
     *
     * @param userPrincipal - authorized user
     * @param publication - Publication Entity
     * @return Publication Entity
     */
    @PostMapping("/api/admin/virt/publication")
    public ResponseEntity<?> createPublication(@CurrentUser UserPrincipal userPrincipal, @RequestBody Publication publication){
        if (userService.findById(userPrincipal.getId()).isPresent()) {
            Optional<Long> id = Optional.ofNullable(publication.getId());
            if (id.isPresent()){
                if (service.findById(id.get()).isPresent()){
                    Publication publicationExisting = service.findById(id.get()).get();
                    Publication savedPublication = service.save(publicationUpdate(publication,publicationExisting));
                    return ResponseEntity.ok(savedPublication);
                }
            }
            Publication savedPublication = service.save(publication);
            return ResponseEntity.ok(savedPublication);
        }else {
            throw new BadRequestException(NO_ACCESS);
        }
    }

    /**
     * Add photos of Publication
     *
     * @param userPrincipal - authorized user
     * @param publication - Publication Entity
     * @return Publication Entity
     */
    @PostMapping("/api/admin/virt/publication/photo")
    public ResponseEntity<?> addPhoto(@CurrentUser UserPrincipal userPrincipal, @RequestBody PublicationPhoto publication
    ){
        if (userService.findById(userPrincipal.getId()).isPresent()) {
            if (service.findById(publication.getId()).isPresent()){
                Publication publicationExists = service.findById(publication.getId()).get();
                Set<Photo> newPhotoSet = publication.getPhotos();
                newPhotoSet.addAll(publication.getPhotos());
                publication.setPhotos(newPhotoSet);
                service.save(publicationExists);
            }
            throw new BadRequestException(PUBLICATION_NOT_FOUND);
        }else {
            throw new BadRequestException(NO_ACCESS);
        }
    }

    /**
     * Delete photos of Publication
     *
     * @param userPrincipal - authorized user
     * @param publication - Publication Entity
     * @return Publication Entity
     */
    @DeleteMapping("/api/admin/virt/publication/photo")
    public ResponseEntity<?> deletePhoto(@CurrentUser UserPrincipal userPrincipal, @RequestBody PublicationPhoto publication
    ){
        if (userService.findById(userPrincipal.getId()).isPresent()) {
            if (service.findById(publication.getId()).isPresent()){
                Publication publicationExists = service.findById(publication.getId()).get();
                Set<Photo> newPhotoSet = publication.getPhotos();
                newPhotoSet.removeAll(publication.getPhotos());
                //TODO physically remove files from storage
                publication.setPhotos(newPhotoSet);
                service.save(publicationExists);
            }
            throw new BadRequestException(PUBLICATION_NOT_FOUND);
        }else {
            throw new BadRequestException(NO_ACCESS);
        }
    }

    /**
     * Get list of Publication
     *
     * @param userPrincipal - authorized user
     * @param page - number of page
     * @param pageSize - Size of page
     * @return Map<String,List<Publication>>
     */
    @GetMapping("/api/virt/publication/")
    public ResponseEntity<?> publications(@CurrentUser UserPrincipal userPrincipal,
                                               @RequestParam int page,
                                               @RequestParam int pageSize){
        if (userService.findById(userPrincipal.getId()).isPresent()) {

            return ResponseEntity.ok(pubResponses(service.findAll(page,pageSize)));
        }else {
            throw new BadRequestException(NO_ACCESS);
        }
    }

    /**
     * Upload file
     *
     * @param userPrincipal - authorized user
     * @param file - Multipart File
     * @return FilePath Entity
     */
    @PostMapping("/api/admin/virt/file-upload")
    public ResponseEntity<?> uploadFile(@CurrentUser UserPrincipal userPrincipal,
                                         @RequestPart MultipartFile file
    ){
        if (userService.findById(userPrincipal.getId()).isPresent()) {
            String path = storageService.store(file);
            FilePath filePath = new FilePath(path);
            return ResponseEntity.ok(filePath);
        }else {
            throw new BadRequestException(NO_ACCESS);
        }
    }

    private Publication publicationUpdate(Publication publicationReq,Publication publicationRes){
        Optional<String> textPub = Optional.ofNullable(publicationReq.getTextPub());
        Optional<Long> id = Optional.ofNullable(publicationReq.getId());
        Optional<FileMediaType> fileMediaType = Optional.ofNullable(publicationReq.getFileMediaType());
        Optional<Set<Photo>> photos = Optional.ofNullable(publicationReq.getPhotos());
        Optional<Virt> virt = Optional.ofNullable(publicationReq.getVirt());
        Optional<LocalDateTime> publicationDate = Optional.ofNullable(publicationReq.getPublicationDate());

        textPub.ifPresent(publicationRes::setTextPub);
        id.ifPresent(publicationRes::setId);
        fileMediaType.ifPresent(publicationRes::setFileMediaType);
        photos.ifPresent(publicationRes::setPhotos);
        virt.ifPresent(publicationRes::setVirt);
        publicationDate.ifPresent(publicationRes::setPublicationDate);
        return publicationRes;
    }
    public Map<String, Object> pubResponses(Page<Publication> publications){
        List<Publication> publicationRes = new ArrayList<>();
        publications.forEach(publicationRes::add);
        Map<String, Object> response = new HashMap<>();
        response.put("publications", publicationRes);
        return response;
    }
}
