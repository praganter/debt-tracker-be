package dev.batuhanyetgin.debt.entity;

import dev.batuhanyetgin.debt.types.CycleType;
import dev.batuhanyetgin.debt.types.DebtType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "debt")
public class DebtEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "cycle")
    private CycleType cycle;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "type")
    private DebtType type;

    @OneToMany(mappedBy = "debtEntity", cascade = CascadeType.ALL)
    private List<DebtHistoryEntity> debtHistoryEntityList;
}