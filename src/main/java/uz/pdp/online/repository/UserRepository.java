package uz.pdp.online.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean findByPhoneNumber(String phoneNumber);
    boolean findByEmail(String email);

    boolean findByEmailAndId(String email, Long id);
    boolean findByPhoneNumberAndId(String phoneNumber, Long id);

    Optional<User> findByEmailAndPassword(String email, String password);
}
