package edu.ucsb.cs156.example.repositories;

import edu.ucsb.cs156.example.entities.Recommendation;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RecommendationRepository extends CrudRepository<Recommendation, Long> {
}