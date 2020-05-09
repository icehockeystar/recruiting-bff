package org.rtu.heavenhr.recruiting.bff.adapters.rdbms;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.StreamSupport;
import org.rtu.heavenhr.recruiting.bff.adapters.rdbms.RdmbsRepositories.OfferRepository;
import org.rtu.heavenhr.recruiting.bff.adapters.rdbms.Values.OfferId;
import org.rtu.heavenhr.recruiting.bff.domain.Offer;
import org.rtu.heavenhr.recruiting.bff.operations.OfferOperations.FetchOffers;
import org.springframework.stereotype.Component;

@Component
public class FetchOffersOutboundAdapter implements FetchOffers {
    private final OfferRepository offerRepository;

    public FetchOffersOutboundAdapter(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Override
    public List<Offer> get() {
        return StreamSupport.stream(offerRepository.findAll().spliterator(), false)
                .map(offerEntity -> Offer.builder()
                        .startDate(offerEntity.getStartDate())
                        .numberOfApplications(offerEntity.getJobApplications().size())
                        .jobTitle(offerEntity.getJobTitle())
                        .id(OfferId.of(offerEntity.getId()))
                        .build())
                .collect(toList());
    }
}
