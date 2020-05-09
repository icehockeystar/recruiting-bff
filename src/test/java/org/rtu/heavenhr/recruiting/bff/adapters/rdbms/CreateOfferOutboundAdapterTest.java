package org.rtu.heavenhr.recruiting.bff.adapters.rdbms;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.rtu.heavenhr.recruiting.bff.adapters.rdbms.RdmbsRepositories.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase
@Import(RdmbsConfig.class)
class CreateOfferOutboundAdapterTest {
    private static final String JOB_TITLE = "Software developer";
    private static final Instant START_DATE = Instant.now();

    @Autowired
    private OfferRepository offerRepository;

    @Test
    void shouldCreateOffer() {
        var createOfferOutboundAdapter = new CreateOfferOutboundAdapter(offerRepository);
        var offerId = createOfferOutboundAdapter.apply(JOB_TITLE, START_DATE);

        var createdOffer = offerRepository.findById(offerId.getValue()).orElseThrow();
        assertThat(createdOffer.getJobTitle()).isEqualTo(JOB_TITLE);
        assertThat(createdOffer.getStartDate()).isEqualTo(START_DATE);
    }
}
