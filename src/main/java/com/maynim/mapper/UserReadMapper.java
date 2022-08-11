package com.maynim.mapper;

import com.maynim.dto.UserReadDto;
import com.maynim.entity.User;

public class UserReadMapper implements Mapper<User, UserReadDto> {

    @Override
    public UserReadDto mapFrom(User object) {
        return null;
    }
}
