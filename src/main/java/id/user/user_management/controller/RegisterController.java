package id.user.user_management.controller;


import id.user.user_management.entity.Role;
import id.user.user_management.entity.User;
import id.user.user_management.models.UserModel;
import id.user.user_management.repository.RoleRepository;
import id.user.user_management.repository.UserRepository;
import id.user.user_management.responseException.BadRequest;
import id.user.user_management.responseException.UserAlreadyExistsException;
import id.user.user_management.responseException.UserServiceException;
import id.user.user_management.service.UserService;
import id.user.user_management.util.PageableSpec;
import id.user.user_management.util.SpecificationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class RegisterController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserModel user) {

        if (user.getNewPassword() == null) throw new BadRequest("Password is needed.");
        if (user.getUsername() == null) throw new BadRequest("Email is needed.");

        try {
            List<Role> roles = new ArrayList<>();
            Role role = roleRepository.findOneByName("ROLE_USER");
            roles.add(role);
            user.setRoles(roles);
            userService.register(user);
            return ResponseEntity.ok("User registered successfully.");
        } catch (UserAlreadyExistsException ex) {
            throw new BadRequest(ex.getMessage());
        } catch (UserServiceException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PostMapping("/register-admin")
    public ResponseEntity<String> registerAdmin(@RequestBody UserModel user) {

        if (user.getNewPassword() == null) throw new BadRequest("Password is needed.");
        if (user.getUsername() == null) throw new BadRequest("Email is needed.");

        try {
            List<Role> roles = new ArrayList<>();
            Role role = roleRepository.findOneByName("ROLE_USER");
            roles.add(role);
            user.setRoles(roles);
            userService.register(user);
            return ResponseEntity.ok("User registered successfully.");
        } catch (UserAlreadyExistsException ex) {
            throw new BadRequest(ex.getMessage());
        } catch (UserServiceException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

}
