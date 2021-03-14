package com.ironhack.business.repositories;

import com.ironhack.business.models.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferInscriptionsRepository extends JpaRepository<Inscription, Long> {
}
