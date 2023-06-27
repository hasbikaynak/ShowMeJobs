package com.showmejobs.domain;

import com.showmejobs.domain.enums.Seniority;
import com.showmejobs.domain.enums.WorkType;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Setter
@NoArgsConstructor
public class Job extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String role;
    @Enumerated(EnumType.STRING)
    private Seniority seniority;
    private String companyName;
    private LocalDateTime date = LocalDateTime.now();
    private BigDecimal salary;
    private String city;
    @Enumerated(EnumType.STRING)
    private WorkType workType;

    public static class Builder {
        private Long id;
        private String role;
        private Seniority seniority;
        private String companyName;
        private LocalDateTime date;
        private BigDecimal salary;
        private String city;
        private WorkType workType;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

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

        public Builder date(LocalDateTime date) {
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
            job.id = this.id;
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

    public Long getId() {
        return id;
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

    public LocalDateTime getDate() {
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

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", role='" + role + '\'' +
                ", seniority=" + seniority +
                ", companyName='" + companyName + '\'' +
                ", date=" + date +
                ", salary=" + salary +
                ", city='" + city + '\'' +
                ", workType=" + workType +
                '}';
    }
}
