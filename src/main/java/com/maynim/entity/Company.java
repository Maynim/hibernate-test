package com.maynim.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "users")
@EqualsAndHashCode(exclude = "users")
@Builder
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "company")
    private List<User> users;
}
