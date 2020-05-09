package org.rtu.heavenhr.recruiting.bff.operations;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.rtu.heavenhr.recruiting.bff.adapters.rdbms.Values.JobApplicationId;
import org.rtu.heavenhr.recruiting.bff.adapters.rdbms.Values.OfferId;
import org.rtu.heavenhr.recruiting.bff.domain.JobApplication;
import org.springframework.stereotype.Component;

@Component
public class JobApplicationOperations {
    private final ApplyForOffer applyForOffer;
    private final FetchApplication fetchApplication;
    private final FetchApplications fetchApplications;

    public JobApplicationOperations(ApplyForOffer applyForOffer,
                                    FetchApplication fetchApplication,
                                    FetchApplications fetchApplications) {
        this.applyForOffer = applyForOffer;
        this.fetchApplication = fetchApplication;
        this.fetchApplications = fetchApplications;
    }

    public JobApplicationId applyForOffer(ApplyForOfferCommand command) {
        return applyForOffer.apply(command);
    }

    public List<JobApplication> fetchApplications(OfferId offerId) {
        return fetchApplications.apply(offerId);
    }

    public JobApplication fetchApplication(OfferId offerId, JobApplicationId jobApplicationId) {
        return fetchApplication.apply(offerId, jobApplicationId);
    }

    public interface ApplyForOffer extends Function<ApplyForOfferCommand, JobApplicationId> {
    }

    public interface FetchApplications extends Function<OfferId, List<JobApplication>> {
    }

    public interface FetchApplication extends BiFunction<OfferId, JobApplicationId, JobApplication> {
    }
}
