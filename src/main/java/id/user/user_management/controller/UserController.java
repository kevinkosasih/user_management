package id.user.user_management.controller;

import id.user.user_management.entity.User;
import id.user.user_management.repository.UserRepository;
import id.user.user_management.responseException.UserNotFound;
import id.user.user_management.util.PageableSpec;
import id.user.user_management.util.SpecificationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;


    @GetMapping({"/",""})
    public Page<User> getUser(@RequestParam Map<String,String> params){
        PageableSpec<User> pageableSpec = SpecificationUtils.of(params);
        return userRepository.findAll(pageableSpec.getSpecification(),pageableSpec.getPageable());
    }

    @GetMapping("/{id}")
    @Cacheable(value = "userCache", key = "#id")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping({"/my","/my/"})
    public User getMyUser(HttpServletRequest request){
        String email = request.getHeader("X-User");
        User user = userRepository.findOneByUsername(email);
        if (user == null)throw new UserNotFound();
        return user;
    }
}
