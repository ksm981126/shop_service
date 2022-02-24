package com.twogenesis.shoppingmall_service.mapper;

import com.twogenesis.shoppingmall_service.data.MemberVO;
import com.twogenesis.shoppingmall_service.data.ReviewVO;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    MemberVO loginMember(String user_email,String user_pwd);
    Integer checkEmail(String email);
    void insertMember(MemberVO data);
    void insertMemberReview(ReviewVO data);
    Integer isExistReview(Integer mi_seq ,Integer pi_seq,Integer oi_seq);
} 
