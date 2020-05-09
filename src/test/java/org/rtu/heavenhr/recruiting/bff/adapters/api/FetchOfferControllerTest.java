package org.rtu.heavenhr.recruiting.bff.adapters.api;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.rtu.heavenhr.recruiting.bff.adapters.rdbms.Values.OfferId;
import org.rtu.heavenhr.recruiting.bff.domain.Errors.OfferNotFound;
import org.rtu.heavenhr.recruiting.bff.domain.Offer;
import org.rtu.heavenhr.recruiting.bff.operations.OfferOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FetchOfferController.class)
class FetchOfferControllerTest {
    private static final UUID NOT_EXISTENT_OFFER_ID = UUID.randomUUID();
    private static final UUID EXISTENT_OFFER_ID = UUID.fromString("074e9dbc-02df-4c0f-8c97-70db3a5ecb08");
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OfferOperations offerOperations;

    @Test
    void shouldReturn404() throws Exception {
        given(offerOperations.fetchOffer(NOT_EXISTENT_OFFER_ID)).willThrow(new OfferNotFound());

        var resultActions = sendFetchOfferRequest(NOT_EXISTENT_OFFER_ID.toString());
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn200AndOffer() throws Exception {
        given(offerOperations.fetchOffer(eq(EXISTENT_OFFER_ID))).willReturn(Offer.builder()
                .id(OfferId.of(EXISTENT_OFFER_ID))
                .jobTitle("Job title")
                .numberOfApplications(2)
                .startDate(Instant.ofEpochSecond(10000000))
                .build());

        var resultActions = sendFetchOfferRequest(EXISTENT_OFFER_ID.toString());
        resultActions.andExpect(status().isOk()).andExpect(content()
                .json("{\"id\":\"074e9dbc-02df-4c0f-8c97-70db3a5ecb08\",\"jobTitle\":\"Job title\"," +
                        "\"startDate\":\"1970-04-26T17:46:40Z\",\"numberOfApplications\":2}"));
    }

    private ResultActions sendFetchOfferRequest(String offerId) throws Exception {
        var request = get("/offers/" + offerId).contentType(MediaType.APPLICATION_JSON);
        return mockMvc.perform(request);
    }
}