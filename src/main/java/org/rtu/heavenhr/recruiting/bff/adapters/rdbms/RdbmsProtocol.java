package org.rtu.heavenhr.recruiting.bff.adapters.rdbms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.rtu.heavenhr.recruiting.bff.domain.ApplicationStatus;

class RdbmsProtocol {
    private RdbmsProtocol() {
    }

    @Data
    @Entity
    @Table(name = "offer")
    @NoArgsConstructor
    @AllArgsConstructor
    static class OfferEntity {
        @Id
        private UUID id;

        @Column(name = "job_title", unique = true, nullable = false)
        private String jobTitle;

        @Column(name = "start_date", nullable = false)
        private Instant startDate;

        @OneToMany(targetEntity = JobApplicationEntity.class, mappedBy = "offer")
        private List<JobApplicationEntity> jobApplications;
    }

    @Data
    @Entity
    @Table(name = "job_application")
    @NoArgsConstructor
    @AllArgsConstructor
    static class JobApplicationEntity {
        @Id
        private UUID id;

        @Column(name = "candidate_email", unique = true, nullable = false)
        private String candidateEmail;

        @Column(name = "resume_text", nullable = false)
        private String resumeText;

        @ManyToOne
        @JoinColumn(name = "offer_id", referencedColumnName = "id", nullable = false)
        private OfferEntity offer;

        @Column(name = "status", nullable = false)
        private ApplicationStatus status;
    }
}
