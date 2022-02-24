package com.twogenesis.shoppingmall_service.api;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.twogenesis.shoppingmall_service.data.MemberVO;
import com.twogenesis.shoppingmall_service.data.ReviewVO;
import com.twogenesis.shoppingmall_service.mapper.MemberMapper;
import com.twogenesis.shoppingmall_service.util.AESAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberAPIController {
    @Autowired MemberMapper mapper;
    @PostMapping("/join")
    public Map<String,Object> postMemberJoin(@RequestBody MemberVO data)throws Exception{
        Map<String, Object> resultMap = new LinkedHashMap<String,Object>();
        if(mapper.checkEmail(data.getMi_email()) == 1){
            resultMap.put("status", false);
            resultMap.put("err_type", "duplicate");
            resultMap.put("message", "["+data.getMi_email()+"] 이미 가입된 이메일 입니다.");
            return resultMap;
        }
        data.setMi_pwd(AESAlgorithm.Encrypt(data.getMi_pwd()));
        mapper.insertMember(data);
        resultMap.put("status", true);
        resultMap.put("message", "회원 등록이 완료되었습니다.");
        return resultMap;
    }
    @PutMapping("/review")
    public String putMemberReview(@RequestBody ReviewVO data,HttpSession session){
        MemberVO login_user = (MemberVO)session.getAttribute("login_user");
        if(login_user == null) return "사용자 로그인 정보가 없습니다.";
        data.setRi_mi_seq(login_user.getMi_seq());
        mapper.insertMemberReview(data);
        
        return "리뷰가 등록되었습니다.";
    }
}
