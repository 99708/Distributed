package com.xyq.mapper;

import org.apache.ibatis.annotations.Select;

import com.xyq.entity.User;

public interface UserMapper {
	@Select("select * from t_user where uname = #{0} and pwd = #{1}")
	User getUser(String uname, String pwd);
}
