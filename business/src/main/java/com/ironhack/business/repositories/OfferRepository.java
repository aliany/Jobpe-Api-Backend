package com.ironhack.business.repositories;

import com.ironhack.business.models.Business;
import com.ironhack.business.models.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    List<Offer> findAllByBusiness(Business business);
}
