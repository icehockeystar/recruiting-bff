package org.rtu.heavenhr.recruiting.bff.adapters.rdbms;

import java.util.UUID;
import org.rtu.heavenhr.recruiting.bff.adapters.rdbms.RdbmsProtocol.JobApplicationEntity;
import org.rtu.heavenhr.recruiting.bff.adapters.rdbms.RdmbsRepositories.JobApplicationRepository;
import org.rtu.heavenhr.recruiting.bff.adapters.rdbms.RdmbsRepositories.OfferRepository;
import org.rtu.heavenhr.recruiting.bff.adapters.rdbms.Values.JobApplicationId;
import org.rtu.heavenhr.recruiting.bff.domain.ApplicationStatus;
import org.rtu.heavenhr.recruiting.bff.domain.Errors;
import org.rtu.heavenhr.recruiting.bff.operations.ApplyForOfferCommand;
import org.rtu.heavenhr.recruiting.bff.operations.JobApplicationOperations.ApplyForOffer;
import org.springframework.stereotype.Component;

@Component
public class ApplyForOfferOutboundAdapter implements ApplyForOffer {
    private final JobApplicationRepository jobApplicationRepository;
    private final OfferRepository offerRepository;

    public ApplyForOfferOutboundAdapter(JobApplicationRepository jobApplicationRepository, OfferRepository offerRepository) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.offerRepository = offerRepository;
    }

    @Override
    public JobApplicationId apply(ApplyForOfferCommand command) {
        var offer = offerRepository.findById(command.getOfferId().getValue()).orElseThrow(Errors.OfferNotFound::new);
        var id = jobApplicationRepository.save(
                new JobApplicationEntity(UUID.randomUUID(), command.getCandidateEmail(), command.getResumeText(),
                        offer, ApplicationStatus.APPLIED))
                .getId();
        return JobApplicationId.of(id);
    }
}
