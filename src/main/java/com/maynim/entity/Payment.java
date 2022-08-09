package com.maynim.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
@Builder
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
//@OptimisticLocking(type = OptimisticLockType.ALL)
//@DynamicUpdate
public class Payment extends AuditableEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @Column(nullable = false)
    private Integer amount;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private User receiver;


}
