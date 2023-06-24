package com.showmejobs.controller;

import com.showmejobs.dto.request.JobRequest;
import com.showmejobs.dto.response.ResponseMessage;
import com.showmejobs.dto.response.SMJResponse;
import com.showmejobs.exception.ResourceNotFoundException;
import com.showmejobs.service.JobService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/job")
@AllArgsConstructor
public class JobController {
    private JobService jobService;

    //localhost:8080/api/v1/job
    @PostMapping
    public ResponseEntity<SMJResponse> addJob(@Valid @RequestBody final JobRequest jobRequest){
        try {
           jobService.saveJob(jobRequest);
        }catch (final Exception exception){
            throw new ResourceNotFoundException( exception.getMessage(),HttpStatus.BAD_GATEWAY);
        }
        SMJResponse response = new SMJResponse(ResponseMessage.JOB_SAVE_RESPONSE_MESSAGE,true);
        return ResponseEntity.ok(response);
    }
}
