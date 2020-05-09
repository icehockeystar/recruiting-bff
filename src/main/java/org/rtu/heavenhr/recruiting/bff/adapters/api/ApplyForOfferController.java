package org.rtu.heavenhr.recruiting.bff.adapters.api;

import java.util.UUID;
import org.rtu.heavenhr.recruiting.bff.adapters.api.RecruitingProtocol.ApplyForOfferRequest;
import org.rtu.heavenhr.recruiting.bff.adapters.api.RecruitingProtocol.ApplyForOfferResponse;
import org.rtu.heavenhr.recruiting.bff.adapters.rdbms.Values;
import org.rtu.heavenhr.recruiting.bff.operations.ApplyForOfferCommand;
import org.rtu.heavenhr.recruiting.bff.operations.JobApplicationOperations;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ApplyForOfferController {
    private final JobApplicationOperations jobApplicationOperations;

    ApplyForOfferController(JobApplicationOperations jobApplicationOperations) {
        this.jobApplicationOperations = jobApplicationOperations;
    }

    @PostMapping("/offers/{offerId}/applications")
    @ResponseStatus(HttpStatus.CREATED)
    ApplyForOfferResponse applyForOffer(@RequestBody ApplyForOfferRequest request,
                                        @PathVariable("offerId") UUID offerId) {
        var jobApplicationId = jobApplicationOperations.applyForOffer(
                ApplyForOfferCommand.builder()
                        .offerId(Values.OfferId.of(offerId))
                        .candidateEmail(request.getCandidateEmail())
                        .resumeText(request.getResumeText())
                        .build());

        return ApplyForOfferResponse.builder()
                .id(jobApplicationId.getValue())
                .build();
    }
}
