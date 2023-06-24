package com.showmejobs.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.showmejobs.domain.enums.Seniority;
import com.showmejobs.domain.enums.WorkType;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@NoArgsConstructor
public class Job extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role;
    @Enumerated(EnumType.STRING)
    private Seniority seniority;
    private String companyName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;
    private BigDecimal salary;
    private String city;
    @Enumerated(EnumType.STRING)
    private WorkType workType;

    public static class Builder {
        private String role;
        private Seniority seniority;
        private String companyName;
        private Date date;
        private BigDecimal salary;
        private String city;
        private WorkType workType;

        public Builder role(String role) {
            this.role = role;
            return this;
        }

        public Builder seniority(Seniority seniority) {
            this.seniority = seniority;
            return this;
        }

        public Builder companyName(String companyName) {
            this.companyName = companyName;
            return this;
        }

        public Builder date(Date date) {
            this.date = date;
            return this;
        }

        public Builder salary(BigDecimal salary) {
            this.salary = salary;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder workType(WorkType workType) {
            this.workType = workType;
            return this;
        }

        public Job build() {
            Job job = new Job();
            job.role = this.role;
            job.seniority = this.seniority;
            job.companyName = this.companyName;
            job.date = this.date;
            job.salary = this.salary;
            job.city = this.city;
            job.workType = this.workType;
            return job;
        }
    }

    public String getRole() {
        return role;
    }

    public Seniority getSeniority() {
        return seniority;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Date getDate() {
        return date;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public String getCity() {
        return city;
    }

    public WorkType getWorkType() {
        return workType;
    }
}