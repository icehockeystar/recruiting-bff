package org.rtu.heavenhr.recruiting.bff.adapters.rdbms;

import org.rtu.heavenhr.recruiting.bff.adapters.rdbms.RdbmsProtocol.JobApplicationEntity;
import org.rtu.heavenhr.recruiting.bff.adapters.rdbms.RdbmsProtocol.OfferEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

class RdmbsRepositories {
    private RdmbsRepositories() {}

    interface OfferRepository extends CrudRepository<OfferEntity, UUID> {}
    interface JobApplicationRepository extends CrudRepository<JobApplicationEntity, UUID> {}
}
