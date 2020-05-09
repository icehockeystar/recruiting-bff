package org.rtu.heavenhr.recruiting.bff.adapters.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.rtu.heavenhr.recruiting.bff.adapters.rdbms.Values.OfferId;
import org.rtu.heavenhr.recruiting.bff.operations.OfferOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CreateOfferController.class)
class CreateOfferControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OfferOperations offerOperations;

    private String payload;

    @Test
    void shouldReturn201() throws Exception {
        givenPayload();
        given(offerOperations.createOffer(any(), any())).willReturn(OfferId.of(UUID.randomUUID()));

        var resultActions = sendCreateOfferRequest();
        resultActions.andExpect(status().isCreated());
    }

    private void givenPayload() {
        payload = "{\"jobTitle\": \"Software developer\", \"startDate\":\"2019-11-09T00:00:00Z\"}";
    }

    private ResultActions sendCreateOfferRequest() throws Exception {
        var request = post("/offers").content(payload).contentType(MediaType.APPLICATION_JSON);
        return mockMvc.perform(request);
    }
}
