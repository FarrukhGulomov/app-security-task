package uz.pdp.online.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.online.entity.Attachment;
@Repository
public interface AttachmentRepository extends JpaRepository<Attachment,Long> {
}
