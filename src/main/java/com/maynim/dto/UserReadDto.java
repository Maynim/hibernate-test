package com.maynim.dto;


import com.maynim.entity.PersonalInfo;
import com.maynim.entity.Role;

public class UserReadDto {
    private final Long id;
    private final PersonalInfo personalInfo;
    private final String username;
    private final String info;
    private final Role role;
    private final CompanyReadDto company;

    public UserReadDto(Long id, PersonalInfo personalInfo, String username, String info, Role role, CompanyReadDto company) {
        this.id = id;
        this.personalInfo = personalInfo;
        this.username = username;
        this.info = info;
        this.role = role;
        this.company = company;
    }
}
