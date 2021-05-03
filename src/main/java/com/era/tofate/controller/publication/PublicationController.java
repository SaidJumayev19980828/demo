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
import com.era.tofate.service.photo.PhotoService;
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
    private final PhotoService photoService;

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
                    Publication savedPublication = savePubWithNewPhotos(publicationUpdate(publication,publicationExisting));
                    savedPublication.getVirt().setPublications(new ArrayList<>());
                    return ResponseEntity.ok(savedPublication);
                }
            }
            Publication savedPublication = savePubWithNewPhotos(publication);
            savedPublication.getVirt().setPublications(new ArrayList<>());
            return ResponseEntity.ok(savedPublication);
        } else {
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
                publicationExists.setPhotos(newPhotoSet);
                Publication savedPublication = savePubWithNewPhotos(publicationExists);
                savedPublication.getVirt().setPublications(new ArrayList<>());
                return ResponseEntity.ok(savedPublication);
            }
            throw new BadRequestException(PUBLICATION_NOT_FOUND);
        } else {
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
                publication.getPhotos().forEach(photo -> {
                    if (photoService.findById(photo.getId()).isPresent()){
                        photoService.deleteById(photo.getId());
                    }
                });
                Publication savedPublication = service.findById(publication.getId()).get();
                Set<Photo> photos = photoService.findAllByPublicationIdAndDeleted(publication.getId(),false);
                savedPublication.setPhotos(photos);
                savedPublication.getVirt().setPublications(new ArrayList<>());
                return ResponseEntity.ok(savedPublication);
            }
            throw new BadRequestException(PUBLICATION_NOT_FOUND);
        } else {
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
                                         @RequestPart MultipartFile file,
                                         @RequestPart String type
    ){
        if (userService.findById(userPrincipal.getId()).isPresent()) {
            String path = storageService.store(file);
            FilePath filePath = new FilePath(path);
            Photo photo = new Photo();
            photo.setType(type);
            String fileUrl = filePath.getFilePath();//TODO need to be changed to download file url
            photo.setUrl(fileUrl);
            photo = photoService.save(photo);
            return ResponseEntity.ok(photo);
        } else {
            throw new BadRequestException(NO_ACCESS);
        }
    }
    //TODO getPhoto api

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
        publications.forEach(publication -> {
            Virt virt = publication.getVirt();
            virt.setPublications(new ArrayList<>());
            publication.setVirt(virt);
        });
        List<Publication> publicationRes = new ArrayList<>();
        publications.forEach(publicationRes::add);
        Map<String, Object> response = new HashMap<>();
        response.put("publications", publicationRes);
        return response;
    }

    private Publication savePubWithNewPhotos(Publication publication) {
        Set<Photo> photoSet = publication.getPhotos();
        //give null to photos in order to not save duplicate in db
        //photos earlier should be saved to db
        publication.setPhotos(new HashSet<>());
        Publication savedPub = service.save(publication);

        photoSet.forEach(photo -> {
            if (photo.getId() != null){
                if (photoService.findById(photo.getId()).isPresent()){
                    photo = photoService.findById(photo.getId()).get();
                    photo.setPublication(savedPub);
                    photoService.save(photo);
                }
            }
        });
        savedPub.setPhotos(photoService.findAllByPublicationIdAndDeleted(publication.getId(),false));
        return savedPub;
    }

}
