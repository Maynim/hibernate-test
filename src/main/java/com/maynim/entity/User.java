package com.maynim.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Embedded
    @AttributeOverride(name = "birthDate", column = @Column(name = "birth_date"))
    private PersonalInfo personalInfo;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Type(type = "com.vladmihalcea.hibernate.type.json.JsonBinaryType")
    @Column(name = "info")
    private String info;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}
