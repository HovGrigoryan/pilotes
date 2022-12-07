package com.shop.pilotes.repository;

import com.shop.pilotes.model.Pilotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PilotesRepository extends JpaRepository<Pilotes, Long> {

}
