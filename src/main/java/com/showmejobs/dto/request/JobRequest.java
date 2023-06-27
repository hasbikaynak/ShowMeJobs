package com.showmejobs.dto.request;

import com.showmejobs.domain.enums.Seniority;
import com.showmejobs.domain.enums.WorkType;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobRequest {
    @NotBlank(message = "Please enter the role")
    private String role;
    @NotNull(message = "Please enter the seniority level")
    private Seniority seniority;
    @NotBlank(message = "Please enter company name")
    private String companyName;
    @NotNull(message = "Please enter the date")
    private Date date = Calendar.getInstance().getTime();
    @NotNull(message = "Please enter the salary")
    private BigDecimal salary;
    @NotBlank(message = "Please enter the city")
    private String city;
    @NotNull(message = "Please enter the work type")
    private WorkType workType;
}
