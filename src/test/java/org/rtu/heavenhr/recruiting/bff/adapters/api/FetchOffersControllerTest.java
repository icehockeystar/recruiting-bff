package org.rtu.heavenhr.recruiting.bff.adapters.api;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.rtu.heavenhr.recruiting.bff.adapters.rdbms.Values.OfferId;
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
@WebMvcTest(FetchOffersController.class)
class FetchOffersControllerTest {
    private static final UUID EXISTENT_OFFER_ID = UUID.fromString("074e9dbc-02df-4c0f-8c97-70db3a5ecb09");

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OfferOperations offerOperations;

    @Test
    void shouldReturn200AndOffers() throws Exception {
        given(offerOperations.fetchOffers()).willReturn(
                List.of(Offer.builder()
                        .id(OfferId.of(EXISTENT_OFFER_ID))
                        .jobTitle("Job title")
                        .numberOfApplications(2)
                        .startDate(Instant.ofEpochSecond(10000000))
                        .build())
        );

        var resultActions = sendFetchOffersRequest();
        resultActions.andExpect(status().isOk()).andExpect(content()
                .json("{\"offers\":[{\"id\":\"074e9dbc-02df-4c0f-8c97-70db3a5ecb09\"," +
                        "\"jobTitle\":\"Job title\"," +
                        "\"startDate\":\"1970-04-26T17:46:40Z\"," +
                        "\"numberOfApplications\":2}]}"));
    }

    private ResultActions sendFetchOffersRequest() throws Exception {
        var request = get("/offers").contentType(MediaType.APPLICATION_JSON);
        return mockMvc.perform(request);
    }
}