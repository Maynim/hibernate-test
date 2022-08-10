package com.maynim.entity;

import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "users")
@EqualsAndHashCode(of = "name")
@Builder
@Entity
@BatchSize(size = 3)
@Audited
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "Companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotAudited
    @Builder.Default
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    @OrderBy("username DESC, personalInfo.lastName ASC")
    private Set<User> users = new HashSet<>();

    @NotAudited
    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "company_locale")
    private List<LocaleInfo> locales = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
        user.setCompany(this);
    }

}
