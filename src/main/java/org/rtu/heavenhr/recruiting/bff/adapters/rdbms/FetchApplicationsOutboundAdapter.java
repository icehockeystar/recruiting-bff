package org.rtu.heavenhr.recruiting.bff.adapters.rdbms;

import java.util.List;
import org.rtu.heavenhr.recruiting.bff.adapters.rdbms.RdmbsRepositories.JobApplicationRepository;
import org.rtu.heavenhr.recruiting.bff.adapters.rdbms.Values.OfferId;
import org.rtu.heavenhr.recruiting.bff.domain.JobApplication;
import org.rtu.heavenhr.recruiting.bff.operations.JobApplicationOperations.FetchApplications;
import org.springframework.stereotype.Component;

@Component
public class FetchApplicationsOutboundAdapter implements FetchApplications {
    private final JobApplicationRepository jobApplicationRepository;

    public FetchApplicationsOutboundAdapter(JobApplicationRepository jobApplicationRepository) {
        this.jobApplicationRepository = jobApplicationRepository;
    }

    @Override
    public List<JobApplication> apply(OfferId offerId) {
        return List.of();
    }
}
