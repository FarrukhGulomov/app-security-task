package uz.pdp.online.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.online.entity.AttachmentContent;
import uz.pdp.online.repository.AttachmentContentRepository;
import uz.pdp.online.repository.AttachmentRepository;
import uz.pdp.online.service.AttachmentService;

@RestController
@RequestMapping("/api/attachments")
public class AttachmentController {
    private final AttachmentService attachmentService;
    public AttachmentController(AttachmentService attachmentService){
        this.attachmentService=attachmentService;
    }
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','MODERATOR')")
    @PostMapping("/upload")
    public ResponseEntity<?> uploadPhoto(@RequestPart MultipartFile photo){
        return attachmentService.upload(photo);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','MODERATOR','OPERATOR','CUSTOMER')")
    @GetMapping("/photo/{id}")
    public ResponseEntity<?> getPhoto(HttpServletResponse response,@PathVariable Long id){
        return attachmentService.getPhoto(response,id);
    }

}
