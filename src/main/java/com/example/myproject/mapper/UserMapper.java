package com.example.myproject.mapper;

import com.example.myproject.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface UserMapper {
    List<User> getUsers(@Param("text") String text);
}
