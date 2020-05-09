package org.rtu.heavenhr.recruiting.bff.domain;

public class Errors {
    public static class OfferNotFound extends RuntimeException {
    }

    public static class JobApplicationNotFound extends RuntimeException {

    }
}
