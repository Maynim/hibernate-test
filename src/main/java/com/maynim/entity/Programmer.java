package com.maynim.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Programmer  extends  User{

    @Enumerated(EnumType.STRING)
    private Language language;

    @Builder
    public Programmer(Long id, String username, PersonalInfo personalInfo, Role role, String info, Company company, Profile profile, List<UserChat> userChats, Language language) {
        super(id, username, personalInfo, role, info, company, profile, userChats);
        this.language = language;
    }
}
