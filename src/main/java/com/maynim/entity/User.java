package com.maynim.entity;

import lombok.*;
import org.hibernate.annotations.*;

import org.hibernate.annotations.Cache;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@NamedEntityGraph(
        name = "WithCompanyAndChat",
        attributeNodes = {
                @NamedAttributeNode("company"),
                @NamedAttributeNode(value = "userChats",subgraph = "chats")
        },
        subgraphs = {
                @NamedSubgraph(name ="chats", attributeNodes = @NamedAttributeNode("chat"))
        }
)
@FetchProfile(name = "withCompanyAndPayment", fetchOverrides = {
        @FetchProfile.FetchOverride(
                entity = User.class, association = "company", mode = FetchMode.JOIN
        ),
        @FetchProfile.FetchOverride(
                entity = User.class, association = "payments", mode = FetchMode.JOIN
        )
})
@NamedQuery(name = "findUserByName", query = "SELECT u FROM User u WHERE u.personalInfo.firstName = :firstname")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "username")
@ToString(exclude = {"company", "userChats", "payments"})
@Builder
@Entity
@Table(name = "users")
@Audited
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE,region = "Users")
public class User implements BaseEntity<Long> {

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @Builder.Default
    @OneToMany(mappedBy = "user")
    @NotAudited
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<UserChat> userChats = new ArrayList<>();

    @Builder.Default
//    @BatchSize(size = 3)
//    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "receiver")
    @NotAudited
    private List<Payment> payments = new ArrayList<>();

    public String fullName() {
        return getPersonalInfo().getFirstName() + " " + getPersonalInfo().getLastName();
    }
}
