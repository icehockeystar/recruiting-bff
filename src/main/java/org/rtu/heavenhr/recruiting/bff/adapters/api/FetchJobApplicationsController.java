package org.rtu.heavenhr.recruiting.bff.adapters.api;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.UUID;
import org.rtu.heavenhr.recruiting.bff.adapters.api.RecruitingProtocol.FetchJobApplicationResponse;
import org.rtu.heavenhr.recruiting.bff.adapters.api.RecruitingProtocol.FetchJobApplicationsResponse;
import org.rtu.heavenhr.recruiting.bff.adapters.rdbms.Values.OfferId;
import org.rtu.heavenhr.recruiting.bff.domain.JobApplication;
import org.rtu.heavenhr.recruiting.bff.operations.JobApplicationOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FetchJobApplicationsController {
    private final JobApplicationOperations jobApplicationOperations;

    public FetchJobApplicationsController(JobApplicationOperations jobApplicationOperations) {
        this.jobApplicationOperations = jobApplicationOperations;
    }

    @GetMapping("/offers/{offerId}/applications")
    FetchJobApplicationsResponse apply(@PathVariable("offerId") UUID offerId) {
        List<JobApplication> jobApplications = jobApplicationOperations.fetchApplications(OfferId.of(offerId));

        return FetchJobApplicationsResponse.builder()
                .jobApplications(jobApplications.stream()
                        .map(jobApplication -> FetchJobApplicationResponse
                                .builder()
                                .status(jobApplication.getStatus())
                                .resumeText(jobApplication.getResumeText())
                                .candidateEmail(jobApplication.getCandidateEmail())
                                .id(jobApplication.getId().getValue())
                                .build())
                        .collect(toList()))
                .build();
    }
}
