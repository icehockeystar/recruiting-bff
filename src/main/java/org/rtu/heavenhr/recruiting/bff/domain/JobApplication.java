package org.rtu.heavenhr.recruiting.bff.domain;

import lombok.Builder;
import lombok.Value;
import org.rtu.heavenhr.recruiting.bff.adapters.rdbms.Values.JobApplicationId;
import org.rtu.heavenhr.recruiting.bff.adapters.rdbms.Values.OfferId;

@Value
@Builder
public class JobApplication {
    private final JobApplicationId id;
    private final OfferId offerId;
    private final String candidateEmail;
    private final String resumeText;
    private final ApplicationStatus status;
}
