package id.user.user_management.service;

import id.user.user_management.entity.Role;
import id.user.user_management.entity.User;
import id.user.user_management.models.UserModel;
import id.user.user_management.repository.RoleRepository;
import id.user.user_management.repository.UserRepository;
import id.user.user_management.responseException.UserAlreadyExistsException;
import id.user.user_management.responseException.UserServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public User register(UserModel user) {

        User checkUser = userRepository.findOneByUsername(user.getUsername());
        if (checkUser != null) throw new UserAlreadyExistsException(user.getUsername());
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getNewPassword()));

        //set Role
        newUser.setRoles(user.getRoles());

        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserServiceException("User registration failed due to a database error.");
        }
    }

}
