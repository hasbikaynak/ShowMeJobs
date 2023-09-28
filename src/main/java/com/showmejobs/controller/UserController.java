package com.showmejobs.controller;

import com.showmejobs.dto.request.LoginRequest;
import com.showmejobs.dto.request.RegisterRequest;
import com.showmejobs.dto.response.LoginResponse;
import com.showmejobs.dto.response.ResponseMessage;
import com.showmejobs.dto.response.SMJResponse;
import com.showmejobs.exception.ConflictException;
import com.showmejobs.security.jwt.JwtUtils;
import com.showmejobs.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    private AuthenticationManager authenticationManager;

    private JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<SMJResponse> registerUser(@Valid @RequestBody final RegisterRequest registerRequest) {
        SMJResponse response;
        try {
            userService.saveUser(registerRequest);
            response = new SMJResponse(ResponseMessage.USER_SAVE_RESPONSE_MESSAGE, true);
        } catch (final ConflictException conflictException) {
            response = new SMJResponse(conflictException.getMessage(), false);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        } catch (final Exception exception) {
            response = new SMJResponse(ResponseMessage.JOB_SAVE_SOMETHING_WENT_WRONG, false);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@Valid @RequestBody LoginRequest loginRequest) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String jwtToken = jwtUtils.generateJwtToken(userDetails);

        LoginResponse loginResponse = new LoginResponse(jwtToken);

        return new ResponseEntity<>(loginResponse, HttpStatus.OK);

    }
}
