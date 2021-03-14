package com.ironhack.business.repositories;

import com.ironhack.business.models.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;


@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Long> {
    List<Inscription> findAllByUserId(@NotNull Integer userId);

    @Query(
            value = "SELECT i FROM Inscription i "
                    + "WHERE i.offer.id = :offerId AND i.userId = :userId"
    )
    Inscription findByOfferIdAndUserId(
            @Param("offerId") Long offerId,
            @Param("userId") Integer userId
    );
}
