package com.showmejobs.controller;

import com.showmejobs.dto.request.RegisterRequest;
import com.showmejobs.dto.response.ResponseMessage;
import com.showmejobs.dto.response.SMJResponse;
import com.showmejobs.exception.ConflictException;
import com.showmejobs.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping
    public ResponseEntity<SMJResponse> registerUser(@Valid @RequestBody final RegisterRequest registerRequest) {
        SMJResponse response;
        try {
            userService.saveUser(registerRequest);
            response = new SMJResponse(ResponseMessage.USER_SAVE_RESPONSE_MESSAGE, true);
        } catch (final ConflictException conflictException) {
            throw new ConflictException(conflictException.getMessage(), HttpStatus.CONFLICT);
        } catch (final Exception exception) {
            response = new SMJResponse(ResponseMessage.JOB_SAVE_SOMETHING_WENT_WRONG, false);
        }
        return ResponseEntity.ok(response);
    }
}
