package com.maynim.entity;

import com.maynim.converter.BirthdayConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class PersonalInfo {

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Convert(converter = BirthdayConverter.class)
    private Birthday birthDate;
}
