package org.rtu.heavenhr.recruiting.bff.adapters.api;

import org.rtu.heavenhr.recruiting.bff.adapters.api.RecruitingProtocol.CreateOfferRequest;
import org.rtu.heavenhr.recruiting.bff.adapters.api.RecruitingProtocol.CreateOfferResponse;
import org.rtu.heavenhr.recruiting.bff.operations.OfferOperations;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
class CreateOfferController {
    private final OfferOperations offerOperations;

    CreateOfferController(OfferOperations offerOperations) {
        this.offerOperations = offerOperations;
    }

    @PostMapping("/offers")
    @ResponseStatus(code = HttpStatus.CREATED)
    CreateOfferResponse apply(@RequestBody CreateOfferRequest createOfferRequest) {
        var offerId = offerOperations.createOffer(createOfferRequest.getJobTitle(), createOfferRequest.getStartDate());
        return CreateOfferResponse.builder().id(offerId.getValue()).build();
    }
}
