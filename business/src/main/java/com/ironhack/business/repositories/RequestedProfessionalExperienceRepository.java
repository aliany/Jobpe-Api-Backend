package com.ironhack.business.repositories;

import com.ironhack.business.models.RequestedProfessionalExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestedProfessionalExperienceRepository extends JpaRepository<RequestedProfessionalExperience, Long> {
}
