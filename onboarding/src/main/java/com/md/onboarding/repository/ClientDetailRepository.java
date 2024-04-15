package com.md.onboarding.repository;

import com.md.onboarding.model.entity.ClientDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientDetailRepository extends JpaRepository<ClientDetail, Long> {

    Optional<ClientDetail> findByCuil(Long cuil);
}
