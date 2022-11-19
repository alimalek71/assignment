package ir.alimalek.assignment.repository;

import ir.alimalek.assignment.domain.Information;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InformationRepository extends JpaRepository<Information, Long> {

    Optional<Information> findByCode(String code);
}
