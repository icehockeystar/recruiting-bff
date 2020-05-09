package org.rtu.heavenhr.recruiting.bff.adapters.rdbms;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.rtu.heavenhr.recruiting.bff.adapters.rdbms.RdmbsRepositories.OfferRepository;
import org.rtu.heavenhr.recruiting.bff.adapters.rdbms.Values.OfferId;
import org.rtu.heavenhr.recruiting.bff.domain.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase
@Import(RdmbsConfig.class)
class FetchOffersOutboundAdapterTest {
    private static final String JOB_TITLE = "Java engineer";
    private static final Instant START_DATE = Instant.now();
    private static final String JOB_TITLE_2 = "Devops";
    private static final Instant START_DATE_2 = Instant.now();

    @Autowired
    private OfferRepository offerRepository;
    private FetchOffersOutboundAdapter fetchOffersOutboundAdapter;
    private CreateOfferOutboundAdapter createOfferOutboundAdapter;

    List<OfferId> offerIds;

    @BeforeEach
    void setUp() {
        fetchOffersOutboundAdapter = new FetchOffersOutboundAdapter(offerRepository);
        createOfferOutboundAdapter = new CreateOfferOutboundAdapter(offerRepository);
    }

    @Test
    void shouldFetchOffers() {
        givenOffers();

        assertThat(fetchOffersOutboundAdapter.get()).containsOnly(
                Offer.builder()
                        .id(offerIds.get(0))
                        .jobTitle(JOB_TITLE)
                        .numberOfApplications(0)
                        .startDate(START_DATE)
                        .build(),
                Offer.builder()
                        .id(offerIds.get(1))
                        .jobTitle(JOB_TITLE_2)
                        .numberOfApplications(0)
                        .startDate(START_DATE_2)
                        .build()
                );
    }

    @Test
    void shouldFetchNoOffers() {
        assertThat(fetchOffersOutboundAdapter.get()).isEmpty();
    }

    private void givenOffers() {
        offerIds = List.of(createOfferOutboundAdapter.apply(JOB_TITLE, START_DATE),
                createOfferOutboundAdapter.apply(JOB_TITLE_2, START_DATE_2));
    }
}