package org.rtu.heavenhr.recruiting.bff.adapters.rdbms;

import lombok.Data;

import java.util.UUID;

public class Values {
    private Values() {}

    @Data(staticConstructor = "of")
    public static class OfferId {
        private final UUID value;
    }

    @Data(staticConstructor = "of")
    public static class JobApplicationId {
        private final UUID value;
    }
}
