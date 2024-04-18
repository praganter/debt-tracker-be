package dev.batuhanyetgin.debt.repository;

import dev.batuhanyetgin.debt.entity.DebtHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DebtHistoryRepository extends JpaRepository<DebtHistoryEntity, Long> {
}