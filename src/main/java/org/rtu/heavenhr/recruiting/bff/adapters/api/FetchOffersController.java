package org.rtu.heavenhr.recruiting.bff.adapters.api;

import java.util.stream.Collectors;
import org.rtu.heavenhr.recruiting.bff.adapters.api.RecruitingProtocol.FetchOfferResponse;
import org.rtu.heavenhr.recruiting.bff.adapters.api.RecruitingProtocol.FetchOffersResponse;
import org.rtu.heavenhr.recruiting.bff.operations.OfferOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class FetchOffersController {
    private final OfferOperations offerOperations;

    FetchOffersController(OfferOperations offerOperations) {
        this.offerOperations = offerOperations;
    }

    @GetMapping("/offers")
    FetchOffersResponse apply() {
        var offers = offerOperations.fetchOffers();

        return FetchOffersResponse.builder()
                .offers(offers.stream()
                        .map(offer -> FetchOfferResponse.builder()
                                .id(offer.getId().getValue())
                                .jobTitle(offer.getJobTitle())
                                .numberOfApplications(offer.getNumberOfApplications())
                                .startDate(offer.getStartDate())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
