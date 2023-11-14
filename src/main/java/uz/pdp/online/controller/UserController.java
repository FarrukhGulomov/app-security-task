package uz.pdp.online.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.entity.User;
import uz.pdp.online.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("/new")
    public HttpEntity<?> createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','CUSTOMER','MODERATOR','OPERATOR')")
    @GetMapping("/{id}")
    public HttpEntity<?> getUser(@PathVariable Long id){
        return  userService.getUser(id);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','MODERATOR')")
    @GetMapping
    public HttpEntity<?> getUsers(@RequestParam int pageNumber,@RequestParam int pageSize){
        return userService.getUsers(pageNumber,pageSize);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','CUSTOMER')")
    @PutMapping("/{id}")
    public HttpEntity<?> editUser(@PathVariable Long id,@RequestBody User user){
        return userService.editUser(id,user);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }
    
}
