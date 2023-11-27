package uz.pdp.online.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.pdp.online.entity.User;
import uz.pdp.online.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public HttpEntity<?> createUser(User user) {
        boolean byEmail = userRepository.existsByEmail(user.getEmail());
        boolean byPhoneNumber = userRepository.existsByPhoneNumber(user.getPhoneNumber());

        if (!byEmail) {
            if (!byPhoneNumber) {
                userRepository.save(user);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.status(401).body("This phone is already exist!");
        }
        return ResponseEntity.status(401).body("This email is already exist!");
    }

    public HttpEntity<?> getUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
//       if(optionalUser.isEmpty()) return ResponseEntity.status(404).build();
//       return ResponseEntity.ok(optionalUser.get());
        return optionalUser.map(ResponseEntity::ok).orElse(ResponseEntity.status(404).build());
    }

    public HttpEntity<?> getUsers(int pageNumber, int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return ResponseEntity.ok(userRepository.findAll(pageable));

    }

    public ResponseEntity<?> editUser(Long id, @RequestBody User user) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) return ResponseEntity.status(404).build();
        boolean byEmailAndId = userRepository.existsByEmailAndIdNot(user.getEmail(), id);
        boolean byPhoneNumberAndId = userRepository.existsByPhoneNumberAndIdNot(user.getPhoneNumber(), id);
        if (byEmailAndId) return ResponseEntity.status(401).build();
        if (byPhoneNumberAndId) return ResponseEntity.status(401).build();
        User editingUser = optionalUser.get();
        editingUser.setEmail(user.getEmail());
        editingUser.setPassword(user.getPassword());
        editingUser.setAddress(user.getAddress());
        editingUser.setLastName(user.getLastName());
        editingUser.setFirstName(user.getFirstName());
        editingUser.setPhoneNumber(user.getPhoneNumber());
        userRepository.save(editingUser);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> deleteUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) return ResponseEntity.status(404).build();
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

  public ResponseEntity<User> signIn(String email,String password){
      Optional<User> optionalUser = userRepository.findByEmailAndPassword(email, password);
      if(optionalUser.isEmpty()) return ResponseEntity.status(404).build();

      User user = optionalUser.get();
      return ResponseEntity.ok(user);

  }

}
