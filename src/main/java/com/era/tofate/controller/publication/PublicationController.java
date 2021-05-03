package com.era.tofate.controller.publication;

import com.era.tofate.entities.photo.Photo;
import com.era.tofate.entities.publication.Publication;
import com.era.tofate.entities.virt.Virt;
import com.era.tofate.enums.FileMediaType;
import com.era.tofate.exceptions.BadRequestException;
import com.era.tofate.payload.file.FilePath;
import com.era.tofate.payload.photo.PhotoId;
import com.era.tofate.payload.publication.PublicationDto;
import com.era.tofate.payload.publication.PublicationPhoto;
import com.era.tofate.payload.publication.PublicationResDto;
import com.era.tofate.payload.virt.VirtId;
import com.era.tofate.security.CurrentUser;
import com.era.tofate.security.UserPrincipal;
import com.era.tofate.service.file.StorageService;
import com.era.tofate.service.photo.PhotoService;
import com.era.tofate.service.publication.PublicationService;
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
import org.springframework.web.multipart.MultipartFile;

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
    private final VirtService virtService;

    /**
     * Create new Publication
     *
     * @param userPrincipal - authorized user
     * @param publicationDto - Publication Entity
     * @return Publication Entity
     */
    @ApiOperation(value = "Create Publication",
            notes = "Create Publication of virt with id of uploaded photo")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "Error with access"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping("/api/admin/virt/publication")
    public ResponseEntity<?> createPublication(@CurrentUser UserPrincipal userPrincipal, @RequestBody PublicationDto publicationDto){
        if (userService.findById(userPrincipal.getId()).isPresent()) {
            Optional<Long> id = Optional.ofNullable(publicationDto.getId());
            if (id.isPresent()){
                if (service.findById(id.get()).isPresent()){
                    Publication publicationExisting = service.findById(id.get()).get();
                    Publication savedPublication = savePubWithNewPhotos(publicationUpdate(publicationDto,publicationExisting));
                    savedPublication.getVirt().setPublications(new ArrayList<>());
                    return ResponseEntity.ok(savedPublication);
                }
            }
            Publication savedPublication = savePubWithNewPhotos(fromDto(publicationDto));
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
    @ApiOperation(value = "Add photos to Publication",
            notes = "also returns existing publication entity with old and newly added photos")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "Error with message (access problem or publication not found)"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping("/api/admin/virt/publication/photo")
    public ResponseEntity<?> addPhoto(@CurrentUser UserPrincipal userPrincipal, @RequestBody PublicationPhoto publication
    ){
        if (userService.findById(userPrincipal.getId()).isPresent()) {
            Optional<Publication> existingPub = service.findById(publication.getId());
            if (existingPub.isPresent()){
                Set<PhotoId> photos = publication.getPhotos();
                photos.forEach(photoId -> {
                    Optional<Photo> photo = photoService.findById(photoId.getId());
                    if (photo.isPresent()){
                        photo.get().setPublication(new Publication(publication.getId()));
                        photoService.save(photo.get());
                    }
                });
                existingPub.get().getVirt().setPublications(new ArrayList<>());
                return ResponseEntity.ok(existingPub);
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
    @ApiOperation(value = "Delete photos from Publication",
            notes = "also returns existing publication entity with remaining photos")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "Error with message (access problem or publication not found)"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @DeleteMapping("/api/admin/virt/publication/photo")
    public ResponseEntity<?> deletePhoto(@CurrentUser UserPrincipal userPrincipal, @RequestBody PublicationPhoto publication
    ){
        if (userService.findById(userPrincipal.getId()).isPresent()) {
            Optional<Publication> savedPublication = service.findById(publication.getId());
            if (savedPublication.isPresent()){
                publication.getPhotos().forEach(photo -> {
                    if (photoService.findById(photo.getId()).isPresent()){
                        photoService.deleteById(photo.getId());
                    }
                });

                Set<Photo> photos = photoService.findAllByPublicationIdAndDeleted(publication.getId(),false);
                savedPublication.get().setPhotos(photos);
                savedPublication.get().getVirt().setPublications(new ArrayList<>());
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
    @GetMapping("/api/virt/publication")
    @ApiOperation(value = "Get publications by page",
            notes = "Returns list of Publications by given paging")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "Error with access"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
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
    @ApiOperation(value = "Upload new photo",
            notes = "Returns saved photo entity with id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "Error with access"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
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

    private Publication publicationUpdate(PublicationDto publicationReq,Publication publicationRes){
        Optional<String> textPub = Optional.ofNullable(publicationReq.getTextPub());
        Optional<Long> id = Optional.ofNullable(publicationReq.getId());
        Optional<FileMediaType> fileMediaType = Optional.ofNullable(publicationReq.getFileMediaType());
        Optional<Set<PhotoId>> photoIds = Optional.ofNullable(publicationReq.getPhotos());
        Optional<Set<Photo>> photos = Optional.of(fromPhotoId(photoIds.get()));
        Optional<VirtId> virtId = Optional.ofNullable(publicationReq.getVirt());
        Optional<Virt> virt = virtService.findById(virtId.get().getId());

        textPub.ifPresent(publicationRes::setTextPub);
        id.ifPresent(publicationRes::setId);
        fileMediaType.ifPresent(publicationRes::setFileMediaType);
        photos.ifPresent(publicationRes::setPhotos);
        virt.ifPresent(publicationRes::setVirt);
        return publicationRes;
    }

    public Map<String, Object> pubResponses(Page<Publication> publications){
        publications.forEach(publication -> {
            Virt virt = publication.getVirt();
            virt.setPublications(new ArrayList<>());
            publication.setVirt(virt);
        });
        List<PublicationResDto> publicationRes = new ArrayList<>();
        publications.forEach(publication -> {
            publicationRes.add(new PublicationResDto(publication));
        });
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
    private Publication fromDto(PublicationDto dto){
        Publication publication = new Publication();
        publication.setPhotos(fromPhotoId(dto.getPhotos()));
        publication.setId(dto.getId());
        publication.setVirt(new Virt(dto.getVirt().getId()));
        publication.setFileMediaType(dto.getFileMediaType());
        publication.setTextPub(dto.getTextPub());
        return publication;
    }

    private Set<Photo> fromPhotoId(Set<PhotoId> photoIds){
        Set<Photo> photos = new HashSet<>();
        photoIds.forEach(photoId -> {
            Photo photo = photoService.findById(photoId.getId()).get();
            photos.add(photo);
        });
        return photos;
    }

}
