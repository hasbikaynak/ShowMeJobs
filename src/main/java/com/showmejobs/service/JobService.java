package com.showmejobs.service;

import com.showmejobs.domain.Job;
import com.showmejobs.domain.enums.Seniority;
import com.showmejobs.domain.enums.WorkType;
import com.showmejobs.dto.request.JobRequest;
import com.showmejobs.dto.response.JobResponse;
import com.showmejobs.exception.ResourceNotFoundException;
import com.showmejobs.exception.message.ErrorMessage;
import com.showmejobs.repository.JobRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
@AllArgsConstructor
public class JobService {
    private JobRepository jobRepository;

    public void saveJob(final JobRequest jobRequest){
//        Job foundJob = jobRepository.findById(jobRequest.getId()).orElseThrow(
//                ()->new ResourceNotFoundException(String.format(ErrorMessage.JOB_NOT_FOUND, jobRequest.getId()))
//        );

        String seniority = jobRequest.getSeniority().getName();
        String workType = jobRequest.getWorkType().getName();

        final Job job = new Job.Builder()
                .city(jobRequest.getCity())
                        .date(jobRequest.getDate())
                                .role(jobRequest.getRole())
                                        .companyName(jobRequest.getCompanyName())
                                                .salary(jobRequest.getSalary())
                                                        .seniority(Seniority.fromName(seniority))
                                                                .workType(WorkType.fromName(workType))
                                                                        .build();
        jobRepository.save(job);
    }
}
