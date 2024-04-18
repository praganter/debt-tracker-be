package dev.batuhanyetgin.debt.repository;

import dev.batuhanyetgin.debt.dto.DebtListRequestDto;
import dev.batuhanyetgin.debt.entity.DebtEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DebtRepository extends JpaRepository<DebtEntity, Long> {

    @Query(value = """
            SELECT d.* FROM debt AS d JOIN debt_history AS dh \
            ON d.id = dh.debt_id \
            WHERE d.id = :#{#param.userId} \
            AND d.user_id = :#{#param.userId} \
            AND d.type = :#{#param.typeOrdinal} \
            AND dh.start_date <= :#{#param.date} \
            AND dh.end_date >= :#{#param.date} \
            OR dh.end_date is NULL""", nativeQuery = true)
    List<DebtEntity> getDebtList(@Param("param") DebtListRequestDto debtListRequestDto);
}