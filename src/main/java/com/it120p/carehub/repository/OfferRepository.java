package com.it120p.carehub.repository;

import com.it120p.carehub.model.entity.Offer;
import com.it120p.carehub.model.entity.Request;
import com.it120p.carehub.model.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends CrudRepository<Offer, Long> {

    @Query("SELECT o FROM Offer o WHERE o.serviceProvider = ?1")
    List<Offer> findOffersByServiceProvider(User serviceProvider);

    @Query("SELECT o FROM Offer o WHERE o.request = ?1")
    List<Offer> findOffersByRequestId(Request request);
}
