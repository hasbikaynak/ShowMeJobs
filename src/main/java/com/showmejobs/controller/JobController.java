package com.showmejobs.controller;

import com.showmejobs.dto.request.JobRequest;
import com.showmejobs.dto.response.JobResponse;
import com.showmejobs.dto.response.ResponseMessage;
import com.showmejobs.dto.response.SMJResponse;
import com.showmejobs.exception.ConflictException;
import com.showmejobs.exception.ResourceNotFoundException;
import com.showmejobs.exception.message.ErrorMessage;
import com.showmejobs.service.JobService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job")
@AllArgsConstructor
public class JobController {
    final private JobService jobService;

    //localhost:8080/api/v1/job
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<SMJResponse> addJob(@Valid @RequestBody final JobRequest jobRequest) {
        SMJResponse response;
        try {
            jobService.saveJob(jobRequest);
            response = new SMJResponse(ResponseMessage.JOB_SAVE_RESPONSE_MESSAGE, true);
        } catch (final ConflictException exception) {
            response = new SMJResponse(ErrorMessage.JOB_AlREADY_APPLIED, false);
        } catch (final Exception exception) {
            response = new SMJResponse(ResponseMessage.JOB_SAVE_SOMETHING_WENT_WRONG, false);
        }
        return ResponseEntity.ok(response);
    }

    //localhost:8080/api/v1/job/all
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/all")
    public ResponseEntity<List<JobResponse>> getAllJobs() {
        try {
            final List<JobResponse> response = jobService.findAllJobs();
            return ResponseEntity.ok(response);
        } catch (final ResourceNotFoundException exception) {
            throw new ResourceNotFoundException(ErrorMessage.JOB_LIST_IS_EMPTY, HttpStatus.OK);
        } catch (final Exception exception) {
            throw new ConflictException(ErrorMessage.JOB_UNKNOWN_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //localhost:8080/api/v1/job/{id}
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> getJobById(@PathVariable final String id) {
        try {
            final JobResponse foundJob = jobService.getJobById(id);
            return ResponseEntity.ok(foundJob);
        } catch (final ResourceNotFoundException exception) {
            throw new ResourceNotFoundException(String.format(ErrorMessage.JOB_NOT_FOUND, id), HttpStatus.NOT_FOUND);
        } catch (final Exception exception) {
            throw new ConflictException(ErrorMessage.JOB_UNKNOWN_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //localhost:8080/api/v1/job/{id}
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<SMJResponse> updateJobById(@PathVariable final String id, @Valid @RequestBody final JobRequest jobRequest) {
        try {
            jobService.updateJobById(id, jobRequest);
            final SMJResponse response = new SMJResponse(ResponseMessage.JOB_UPDATE, true);
            return ResponseEntity.ok(response);
        } catch (final ResourceNotFoundException exception) {
            throw new ResourceNotFoundException(String.format(ErrorMessage.JOB_NOT_FOUND, id), HttpStatus.NOT_FOUND);
        } catch (final Exception exception) {
            throw new ConflictException(ErrorMessage.JOB_UNKNOWN_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //localhost:8080/api/v1/job/{id}
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<SMJResponse> deleteJobById(@PathVariable final String id) {
        try {
            jobService.deleteJobById(id);
            final SMJResponse response = new SMJResponse(ResponseMessage.JOB_DELETE, true);
            return ResponseEntity.ok(response);
        } catch (final ResourceNotFoundException exception) {
            throw new ResourceNotFoundException(String.format(ErrorMessage.JOB_NOT_FOUND, id), HttpStatus.NOT_FOUND);
        } catch (final Exception exception) {
            throw new ConflictException(ErrorMessage.JOB_UNKNOWN_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
