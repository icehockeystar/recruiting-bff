package org.rtu.heavenhr.recruiting.bff.adapters.rdbms;

import org.rtu.heavenhr.recruiting.bff.adapters.rdbms.RdmbsRepositories.JobApplicationRepository;
import org.rtu.heavenhr.recruiting.bff.adapters.rdbms.Values.JobApplicationId;
import org.rtu.heavenhr.recruiting.bff.adapters.rdbms.Values.OfferId;
import org.rtu.heavenhr.recruiting.bff.domain.JobApplication;
import org.rtu.heavenhr.recruiting.bff.operations.JobApplicationOperations.FetchApplication;
import org.springframework.stereotype.Component;

@Component
public class FetchApplicationOutboundAdapter implements FetchApplication {
    private final JobApplicationRepository jobApplicationRepository;

    public FetchApplicationOutboundAdapter(JobApplicationRepository jobApplicationRepository) {
        this.jobApplicationRepository = jobApplicationRepository;
    }

    @Override
    public JobApplication apply(OfferId offerId, JobApplicationId jobApplicationId) {
        //TODO
        return JobApplication.builder().build();
    }
}
