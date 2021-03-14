package com.ironhack.business.repositories;

import com.ironhack.business.models.YearsOfExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YearsOfExperienceRepository extends JpaRepository<YearsOfExperience, Long> {
}
