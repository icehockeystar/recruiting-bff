package org.rtu.heavenhr.recruiting.bff.adapters.api;

import java.util.UUID;
import org.rtu.heavenhr.recruiting.bff.adapters.api.RecruitingProtocol.FetchOfferResponse;
import org.rtu.heavenhr.recruiting.bff.domain.Errors.OfferNotFound;
import org.rtu.heavenhr.recruiting.bff.operations.OfferOperations;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
class FetchOfferController {
    private final OfferOperations offerOperations;

    FetchOfferController(OfferOperations offerOperations) {
        this.offerOperations = offerOperations;
    }

    @GetMapping("/offers/{offerId}")
    FetchOfferResponse apply(@PathVariable("offerId") UUID offerId) {
        var offer = offerOperations.fetchOffer(offerId);
        return FetchOfferResponse.builder().id(offer.getId().getValue()).jobTitle(offer.getJobTitle())
                .startDate(offer.getStartDate()).numberOfApplications(offer.getNumberOfApplications()).build();
    }

    @ExceptionHandler(value = OfferNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void handleOfferNotFound(OfferNotFound exception) {
        //TODO handle appropriately (convert to a specific response payload)
    }
}
