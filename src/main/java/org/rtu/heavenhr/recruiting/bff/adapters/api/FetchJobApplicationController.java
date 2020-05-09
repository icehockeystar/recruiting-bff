package org.rtu.heavenhr.recruiting.bff.adapters.api;

import java.util.UUID;
import org.rtu.heavenhr.recruiting.bff.adapters.api.RecruitingProtocol.FetchJobApplicationResponse;
import org.rtu.heavenhr.recruiting.bff.adapters.rdbms.Values.JobApplicationId;
import org.rtu.heavenhr.recruiting.bff.adapters.rdbms.Values.OfferId;
import org.rtu.heavenhr.recruiting.bff.domain.Errors.JobApplicationNotFound;
import org.rtu.heavenhr.recruiting.bff.operations.JobApplicationOperations;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

public class FetchJobApplicationController {
    private final JobApplicationOperations jobApplicationOperations;

    public FetchJobApplicationController(JobApplicationOperations jobApplicationOperations) {
        this.jobApplicationOperations = jobApplicationOperations;
    }

    @GetMapping("/offers/{offerId}/applications/{applicationId}")
    FetchJobApplicationResponse apply(@PathVariable("offerId") UUID offerId,
                                      @PathVariable("applicationId") UUID applicationId) {
        var jobApplication =
                jobApplicationOperations.fetchApplication(OfferId.of(offerId), JobApplicationId.of(applicationId));

        return FetchJobApplicationResponse.builder()
                .id(jobApplication.getId().getValue())
                .candidateEmail(jobApplication.getCandidateEmail())
                .resumeText(jobApplication.getResumeText())
                .status(jobApplication.getStatus())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    void handleJobApplicationNotFound(JobApplicationNotFound e) {
        //handle exception
    }
}
