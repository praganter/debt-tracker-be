package dev.batuhanyetgin.auth.repository;


import dev.batuhanyetgin.auth.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity,Long> {

    RefreshTokenEntity getByUserId(Long userId);
}
