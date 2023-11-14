package uz.pdp.online.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.HttpRequestHandlerServlet;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.online.entity.Attachment;
import uz.pdp.online.entity.AttachmentContent;
import uz.pdp.online.repository.AttachmentContentRepository;
import uz.pdp.online.repository.AttachmentRepository;

import java.util.Arrays;
import java.util.Optional;

@Service
public class AttachmentService {
    private final AttachmentRepository attachmentRepository;
    private final AttachmentContentRepository attachmentContentRepository;
    public AttachmentService(AttachmentRepository attachmentRepository,AttachmentContentRepository attachmentContentRepository){
        this.attachmentRepository=attachmentRepository;
        this.attachmentContentRepository=attachmentContentRepository;
    }

    @SneakyThrows
    public ResponseEntity<?> upload(MultipartFile multipartFile){
        String contentType = multipartFile.getContentType();
      assert  contentType!=null;
        if(contentType.equals("image/jpeg") || contentType.equals("image/png")) {
            Attachment attachment = new Attachment();
            attachment.setContentType(contentType);
            attachment.setSize(multipartFile.getSize());
            String originalFilename = multipartFile.getOriginalFilename();
            assert originalFilename != null;
            String photoName = originalFilename.split("\\.")[0];
            attachment.setPhotoName(photoName);
            Attachment savedAttachment = attachmentRepository.save(attachment);
            AttachmentContent attachmentContent = new AttachmentContent();
            attachmentContent.setAttachment(savedAttachment);
            attachmentContent.setBytes(multipartFile.getBytes());
            attachmentContentRepository.save(attachmentContent);
            return ResponseEntity.ok().build();
        }
     return ResponseEntity.status(405).body("upload only jpeg or png file");
    }
    @SneakyThrows
    public ResponseEntity<?> getPhoto(HttpServletResponse response, Long id){
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if(optionalAttachment.isEmpty()) return ResponseEntity.status(404).build();
        Attachment attachment = optionalAttachment.get();

        Optional<AttachmentContent> optionalContent = attachmentContentRepository.findAttachmentContentByAttachment_Id(id);
        if(optionalContent.isEmpty()) return ResponseEntity.status(404).build();
        AttachmentContent attachmentContent = optionalContent.get();

        response.setContentType(attachment.getContentType());
        response.setHeader("Content-disposition","inline");
        FileCopyUtils.copy(attachmentContent.getBytes(),response.getOutputStream());
        return ResponseEntity.ok().build();

    }

}
