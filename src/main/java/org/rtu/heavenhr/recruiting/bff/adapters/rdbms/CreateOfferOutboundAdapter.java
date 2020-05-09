package org.rtu.heavenhr.recruiting.bff.adapters.rdbms;

import org.rtu.heavenhr.recruiting.bff.adapters.rdbms.RdbmsProtocol.OfferEntity;
import org.rtu.heavenhr.recruiting.bff.adapters.rdbms.RdmbsRepositories.OfferRepository;
import org.rtu.heavenhr.recruiting.bff.adapters.rdbms.Values.OfferId;
import org.rtu.heavenhr.recruiting.bff.operations.OfferOperations.CreateOffer;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Component
class CreateOfferOutboundAdapter implements CreateOffer {
    private final OfferRepository offerRepository;

    CreateOfferOutboundAdapter(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }


    @Override
    public OfferId apply(String jobTitle, Instant startDate) {
        return OfferId.of(offerRepository.save(new OfferEntity(UUID.randomUUID(), jobTitle, startDate, List.of())).getId());
    }
}
