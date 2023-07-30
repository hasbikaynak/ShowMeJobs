package com.showmejobs.service;

import com.showmejobs.domain.User;
import com.showmejobs.dto.request.RegisterRequest;
import com.showmejobs.exception.ConflictException;
import com.showmejobs.exception.message.ErrorMessage;
import com.showmejobs.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    final private UserRepository userRepository;

    public void saveUser(final RegisterRequest registerRequest) {
        final User foundUser = userRepository.findByEmail(registerRequest.getEmail());
        if (foundUser != null) {
            throw new ConflictException(ErrorMessage.USER_ALREADY_REGISTERED, HttpStatus.CONFLICT);
        }
        final User user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .address(registerRequest.getAddress())
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .phoneNumber(registerRequest.getPhoneNumber())
                .zipCode(registerRequest.getZipCode())
                .build();

        userRepository.save(user);
    }
}
