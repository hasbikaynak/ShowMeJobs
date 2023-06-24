package com.showmejobs.dto.response;

import com.showmejobs.domain.enums.Seniority;
import com.showmejobs.domain.enums.WorkType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobResponse {
    private Long id;
    private String role;
    private Seniority seniority;
    private String companyName;
    private Date date;
    private BigDecimal salary;
    private String city;
    private WorkType workType;
}
