package org.rtu.heavenhr.recruiting.bff.operations;

import lombok.Builder;
import lombok.Value;
import org.rtu.heavenhr.recruiting.bff.adapters.rdbms.Values.OfferId;

@Builder
@Value
public class ApplyForOfferCommand {
    private final OfferId offerId;
    private final String candidateEmail;
    private final String resumeText;
}
