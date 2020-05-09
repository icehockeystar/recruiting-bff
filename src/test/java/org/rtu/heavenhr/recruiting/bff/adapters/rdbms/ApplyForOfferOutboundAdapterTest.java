package org.rtu.heavenhr.recruiting.bff.adapters.rdbms;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.rtu.heavenhr.recruiting.bff.adapters.rdbms.RdmbsRepositories.JobApplicationRepository;
import org.rtu.heavenhr.recruiting.bff.adapters.rdbms.RdmbsRepositories.OfferRepository;
import org.rtu.heavenhr.recruiting.bff.adapters.rdbms.Values.OfferId;
import org.rtu.heavenhr.recruiting.bff.operations.ApplyForOfferCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase
@Import(RdmbsConfig.class)
class ApplyForOfferOutboundAdapterTest {
    private static final String CANDIDATE_EMAIL = "some@email.com";
    private static final String RESUME_TEXT = "New candidate. Experience.";
    @Autowired
    private JobApplicationRepository jobApplicationRepository;
    @Autowired
    private OfferRepository offerRepository;

    private ApplyForOfferOutboundAdapter applyForOfferOutboundAdapter;
    private OfferId offerId;

    @BeforeEach
    void setUp() {
        applyForOfferOutboundAdapter = new ApplyForOfferOutboundAdapter(jobApplicationRepository, offerRepository);
    }

    @Test
    void shouldCreateApplicationForOffer() {
        givenOffer();

        var jobApplicationId = applyForOfferOutboundAdapter.apply(ApplyForOfferCommand.builder()
                .offerId(offerId)
                .candidateEmail(CANDIDATE_EMAIL)
                .resumeText(RESUME_TEXT)
                .build());

        var jobApplication = jobApplicationRepository.findById(jobApplicationId.getValue()).orElseThrow();
        assertThat(jobApplication.getCandidateEmail()).isEqualTo(CANDIDATE_EMAIL);
        assertThat(jobApplication.getResumeText()).isEqualTo(RESUME_TEXT);
        assertThat(jobApplication.getOffer().getId()).isEqualTo(offerId.getValue());
    }

    private void givenOffer() {
        var offer = offerRepository
                .save(new RdbmsProtocol.OfferEntity(UUID.randomUUID(), "title", Instant.now(), List.of()));
        offerId = OfferId.of(offer.getId());
    }
}
