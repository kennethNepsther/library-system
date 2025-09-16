package it.nepsther.library_system.repository;

import it.nepsther.library_system.entity.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILoanRepository extends JpaRepository<LoanEntity, Long> {
}
