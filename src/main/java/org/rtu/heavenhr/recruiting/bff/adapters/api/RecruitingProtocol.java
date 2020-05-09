package org.rtu.heavenhr.recruiting.bff.adapters.api;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import lombok.Value;
import org.rtu.heavenhr.recruiting.bff.domain.ApplicationStatus;

class RecruitingProtocol {
    @Data
    static class CreateOfferRequest {
        private String jobTitle;
        private Instant startDate;
    }

    @Builder
    @Value
    static class CreateOfferResponse {
        private final UUID id;
    }

    @Builder
    @Value
    static class FetchOfferResponse {
        private final UUID id;
        private final String jobTitle;
        private final Instant startDate;
        private final int numberOfApplications;
    }

    @Builder
    @Value
    static class FetchOffersResponse {
        private final List<FetchOfferResponse> offers;
    }

    @Data
    static class ApplyForOfferRequest {
        private String candidateEmail;
        private String resumeText;
    }

    @Builder
    @Value
    static class ApplyForOfferResponse {
        private final UUID id;
    }

    @Builder
    @Value
    static class FetchJobApplicationResponse {
        private final UUID id;
        private final String candidateEmail;
        private final String resumeText;
        private final ApplicationStatus status;
    }

    @Builder
    @Value
    static class FetchJobApplicationsResponse {
        private final List<FetchJobApplicationResponse> jobApplications;
    }
}
