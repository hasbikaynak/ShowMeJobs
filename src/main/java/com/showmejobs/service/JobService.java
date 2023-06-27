package com.showmejobs.service;

import com.showmejobs.domain.Job;
import com.showmejobs.domain.enums.Seniority;
import com.showmejobs.domain.enums.WorkType;
import com.showmejobs.dto.request.JobRequest;
import com.showmejobs.dto.response.JobResponse;
import com.showmejobs.exception.ConflictException;
import com.showmejobs.exception.ResourceNotFoundException;
import com.showmejobs.exception.message.ErrorMessage;
import com.showmejobs.repository.JobRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JobService {
    final private JobRepository jobRepository;

    private static final Logger logger = Logger.getLogger(JobService.class.getName());

    public void saveJob(final JobRequest jobRequest) {

        final String seniority = jobRequest.getSeniority().getName();
        final String workType = jobRequest.getWorkType().getName();
        final String companyName = jobRequest.getCompanyName();
        final String role = jobRequest.getRole();
        final String city = jobRequest.getCity();

        logger.info("Saving job for company: " + companyName + ", role: " + role + ", city: " + city);

        final Job foundJob = jobRepository.findByCompanyNameAndRoleAndCity(companyName, role, city);
        if (foundJob != null) {
            logger.warning("Job already applied for company: " + companyName + ", role: " + role + ", city: " + city);
            throw new ConflictException(ErrorMessage.JOB_AlREADY_APPLIED, HttpStatus.CONFLICT);
        }


        final Job job = new Job.Builder()
                .city(city)
                .date(jobRequest.getDate())
                .role(role)
                .companyName(companyName)
                .salary(jobRequest.getSalary())
                .seniority(Seniority.fromName(seniority))
                .workType(WorkType.fromName(workType))
                .build();
        logger.info("Job saved successfully for company: " + companyName + ", role: " + role + ", city: " + city);
        jobRepository.save(job);
    }

    public List<JobResponse> findAllJobs() {
        final List<Job> foundListOfJobs = jobRepository.findAll();

        if (foundListOfJobs.isEmpty()) {
            throw new ResourceNotFoundException(ErrorMessage.JOB_LIST_IS_EMPTY, HttpStatus.OK);
        }

        return foundListOfJobs.stream()
                .map(this::mapJobToJobResponse)
                .collect(Collectors.toList());
    }

    private JobResponse mapJobToJobResponse(final Job job) {
        final JobResponse jobResponse = new JobResponse();

        jobResponse.setId(job.getId().toString());
        jobResponse.setCity(job.getCity());
        jobResponse.setDate(job.getDate());
        jobResponse.setRole(job.getRole());
        jobResponse.setSalary(job.getSalary());
        jobResponse.setSeniority(job.getSeniority());
        jobResponse.setCompanyName(job.getCompanyName());
        jobResponse.setWorkType(job.getWorkType());

        return jobResponse;
    }

    public JobResponse getJobById(final String id) {
        Job foundJob = getById(id);
        logger.info("The applied job has been found" + foundJob.toString());

        return mapJobToJobResponse(foundJob);
    }

    public void updateJobById(final String id, final JobRequest jobRequest) {
        final Job foundJob = getById(id);
        logger.info("The job has been found" + foundJob.toString());

        foundJob.setRole(jobRequest.getRole());
        foundJob.setSeniority(jobRequest.getSeniority());
        foundJob.setCompanyName(jobRequest.getCompanyName());
        foundJob.setDate(jobRequest.getDate());
        foundJob.setSalary(jobRequest.getSalary());
        foundJob.setCity(jobRequest.getCity());
        foundJob.setWorkType(jobRequest.getWorkType());

        jobRepository.save(foundJob);
    }

    private Job getById(final String id) {
        return jobRepository.findById(Long.valueOf(id)).orElseThrow(
                () -> new ResourceNotFoundException(String.format(ErrorMessage.JOB_NOT_FOUND, id), HttpStatus.NOT_FOUND));
    }

    public void deleteJobById(final String id) {
        final Job foundJob = getById(id);
        jobRepository.deleteById(foundJob.getId());
    }
}
