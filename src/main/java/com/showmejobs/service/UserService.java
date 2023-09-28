package com.showmejobs.service;

import com.showmejobs.domain.Role;
import com.showmejobs.domain.User;
import com.showmejobs.domain.enums.RoleType;
import com.showmejobs.dto.request.RegisterRequest;
import com.showmejobs.exception.ConflictException;
import com.showmejobs.exception.ResourceNotFoundException;
import com.showmejobs.exception.message.ErrorMessage;
import com.showmejobs.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    final private UserRepository userRepository;

    final private RoleService roleService;

    final private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleService roleService, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveUser(final RegisterRequest registerRequest) {
        final Optional<User> foundUser = userRepository.findByEmail(registerRequest.getEmail());
        if (foundUser.isPresent()) {
            throw new ConflictException(ErrorMessage.USER_ALREADY_REGISTERED, HttpStatus.CONFLICT);
        }
//        roleService.createRoles();
        Role role = roleService.findByType(RoleType.ROLE_USER);

        Set<Role> roles = new HashSet<>();
        roles.add(role);

        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());

        final User user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .address(registerRequest.getAddress())
                .email(registerRequest.getEmail())
                .password(encodedPassword)
                .phoneNumber(registerRequest.getPhoneNumber())
                .zipCode(registerRequest.getZipCode())
                .roles(roles)
                .build();

        userRepository.save(user);
    }

    public User getUserByEmail(final String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE, email), HttpStatus.NOT_FOUND));
    }
}
