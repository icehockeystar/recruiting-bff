package org.rtu.heavenhr.recruiting.bff.operations;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import org.rtu.heavenhr.recruiting.bff.adapters.rdbms.Values.OfferId;
import org.rtu.heavenhr.recruiting.bff.domain.Offer;
import org.springframework.stereotype.Component;

@Component
public class OfferOperations {
    private final CreateOffer createOffer;
    private final FetchOffer fetchOffer;
    private final FetchOffers fetchOffers;

    public OfferOperations(CreateOffer createOffer, FetchOffer fetchOffer, FetchOffers fetchOffers) {
        this.createOffer = createOffer;
        this.fetchOffer = fetchOffer;
        this.fetchOffers = fetchOffers;
    }

    public OfferId createOffer(String jobTitle, Instant startDate) {
        return createOffer.apply(jobTitle, startDate);
    }

    public Offer fetchOffer(UUID offerId) {
        return fetchOffer.apply(OfferId.of(offerId));
    }

    public List<Offer> fetchOffers() {
        return fetchOffers.get();
    }

    public interface CreateOffer extends BiFunction<String, Instant, OfferId> {
    }

    public interface FetchOffer extends Function<OfferId, Offer> {
    }

    public interface FetchOffers extends Supplier<List<Offer>> {
    }
}
