package com.twogenesis.shoppingmall_service.mapper;

import com.twogenesis.shoppingmall_service.data.MemberVO;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    MemberVO loginMember(String user_email,String user_pwd);
    Integer checkEmail(String email);
    void insertMember(MemberVO data);
} 
