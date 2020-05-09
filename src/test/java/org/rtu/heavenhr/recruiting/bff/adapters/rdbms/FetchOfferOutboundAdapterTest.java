package org.rtu.heavenhr.recruiting.bff.adapters.rdbms;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.rtu.heavenhr.recruiting.bff.adapters.rdbms.RdmbsRepositories.JobApplicationRepository;
import org.rtu.heavenhr.recruiting.bff.adapters.rdbms.RdmbsRepositories.OfferRepository;
import org.rtu.heavenhr.recruiting.bff.adapters.rdbms.Values.OfferId;
import org.rtu.heavenhr.recruiting.bff.domain.Errors.OfferNotFound;
import org.rtu.heavenhr.recruiting.bff.operations.ApplyForOfferCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase
@Import(RdmbsConfig.class)
class FetchOfferOutboundAdapterTest {
    private static final OfferId OFFER_ID_THAT_DOESNT_EXIST = OfferId.of(UUID.randomUUID());
    private static final String JOB_TITLE = "Software engineer";
    private static final Instant START_DATE = Instant.now();
    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    private FetchOfferOutboundAdapter fetchOfferOutboundAdapter;
    private CreateOfferOutboundAdapter createOfferOutboundAdapter;
    private ApplyForOfferOutboundAdapter applyForOfferOutboundAdapter;

    private OfferId offerId;

    @BeforeEach
    void setUp() {
        fetchOfferOutboundAdapter = new FetchOfferOutboundAdapter(offerRepository);
        createOfferOutboundAdapter = new CreateOfferOutboundAdapter(offerRepository);
        applyForOfferOutboundAdapter = new ApplyForOfferOutboundAdapter(jobApplicationRepository, offerRepository);
    }

    @Test
    void shouldThrowOfferNotFoundException() {
        assertThatExceptionOfType(OfferNotFound.class)
                .isThrownBy(() -> fetchOfferOutboundAdapter.apply(OFFER_ID_THAT_DOESNT_EXIST));
    }

    @Test
    void shouldFetchOfferWithNoApplications() {
        givenOffer();

        var offer = fetchOfferOutboundAdapter.apply(offerId);
        assertThat(offer.getJobTitle()).isEqualTo(JOB_TITLE);
        assertThat(offer.getStartDate()).isEqualTo(START_DATE);
        assertThat(offer.getNumberOfApplications()).isEqualTo(0);
    }

    private void givenApplications() {
        applyForOfferOutboundAdapter.apply(ApplyForOfferCommand.builder()
                .offerId(offerId)
                .candidateEmail("test1@test.org")
                .resumeText("resume text 1")
                .build());

        applyForOfferOutboundAdapter.apply(ApplyForOfferCommand.builder()
                .offerId(offerId)
                .candidateEmail("test2@test.org")
                .resumeText("resume text 2")
                .build());
    }

    private void givenOffer() {
        offerId = createOfferOutboundAdapter.apply(JOB_TITLE, START_DATE);
    }
}
