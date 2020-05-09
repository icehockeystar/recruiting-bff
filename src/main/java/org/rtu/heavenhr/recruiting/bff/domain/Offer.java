package org.rtu.heavenhr.recruiting.bff.domain;

import lombok.Builder;
import lombok.Value;
import org.rtu.heavenhr.recruiting.bff.adapters.rdbms.Values.OfferId;

import java.time.Instant;

@Value
@Builder
public class Offer {
    private final OfferId id;
    private final String jobTitle;
    private final Instant startDate;
    private final int numberOfApplications;
}
