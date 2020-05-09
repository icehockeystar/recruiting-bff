package org.rtu.heavenhr.recruiting.bff.adapters.rdbms;

import static org.rtu.heavenhr.recruiting.bff.adapters.rdbms.RdmbsRepositories.OfferRepository;

import org.rtu.heavenhr.recruiting.bff.adapters.rdbms.Values.OfferId;
import org.rtu.heavenhr.recruiting.bff.domain.Errors;
import org.rtu.heavenhr.recruiting.bff.domain.Offer;
import org.rtu.heavenhr.recruiting.bff.operations.OfferOperations.FetchOffer;
import org.springframework.stereotype.Component;

@Component
public class FetchOfferOutboundAdapter implements FetchOffer {
    private final OfferRepository offerRepository;

    public FetchOfferOutboundAdapter(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Override
    public Offer apply(OfferId offerId) {
        return offerRepository.findById(offerId.getValue()).map(offerEntity ->
                Offer.builder()
                        .id(OfferId.of(offerEntity.getId()))
                        .jobTitle(offerEntity.getJobTitle())
                        .startDate(offerEntity.getStartDate())
                        .numberOfApplications(offerEntity.getJobApplications().size())
                        .build())
                .orElseThrow(Errors.OfferNotFound::new);
    }
}
