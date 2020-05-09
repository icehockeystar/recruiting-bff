package org.rtu.heavenhr.recruiting.bff.adapters.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.rtu.heavenhr.recruiting.bff.adapters.rdbms.Values;
import org.rtu.heavenhr.recruiting.bff.operations.JobApplicationOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ApplyForOfferController.class)
class ApplyForOfferControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JobApplicationOperations jobApplicationOperations;

    private String payload;

    @Test
    void shouldReturn201AndJobApplicationId() throws Exception {
        givenPayload();
        given(jobApplicationOperations.applyForOffer(any())).willReturn(Values.JobApplicationId.of(UUID.randomUUID()));

        var resultActions = sendApplyForOfferRequest();
        resultActions.andExpect(status().isCreated());

    }

    private ResultActions sendApplyForOfferRequest() throws Exception {
        var request = post("/offers/4c81b5b0-eb9a-4427-8f68-0ab83ea11a2a/applications")
                .content(payload).contentType(MediaType.APPLICATION_JSON);
        return mockMvc.perform(request);
    }

    private void givenPayload() {
        payload = "{\"candidateEmail\":\"test@test.org\"}, " +
                "\"resumeText\":\"resume text\"}";
    }
}
