package com.twogenesis.shoppingmall_service.controller;

import javax.servlet.http.HttpSession;

import com.twogenesis.shoppingmall_service.data.MemberVO;
import com.twogenesis.shoppingmall_service.mapper.MemberMapper;
import com.twogenesis.shoppingmall_service.util.AESAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberController {
    @Autowired MemberMapper mapper;
    @GetMapping("/login")
    public String getMemberLogin(HttpSession session){
        Boolean try_login = (Boolean)session.getAttribute("try_login");
        if(try_login == null || try_login == false){
            session.setAttribute("login_status", null);
            session.setAttribute("login_msg", null);
        }
        session.setAttribute("try_login", false);
        return "/member/login";
    }
    @PostMapping("/login")
    public String postMemberLogin(HttpSession session, String user_email, String user_pwd)throws Exception{
        session.setAttribute("try_login", true);
        MemberVO login_user = mapper.loginMember(user_email, AESAlgorithm.Encrypt(user_pwd));
        if(login_user ==null){
            session.setAttribute("login_user", null);
            session.setAttribute("login_status", "incorrect");
            session.setAttribute("login_msg", "아이디 혹은 비밀번호 오류입니다.");
            return "redirect:/member/login";
        }
        if(login_user.getMi_status() != 1){
            session.setAttribute("login_user", null);
        }
        if(login_user.getMi_status() == 2){
            session.setAttribute("login_status", "pause");
            session.setAttribute("login_msg", "계정이 사용 정지 상태입니다.");
            return "redirect:/member/login";
        }
        if(login_user.getMi_status() == 3){
            session.setAttribute("login_status", "waiting");
            session.setAttribute("login_msg", "가입 대기 상태의 사용자입니다.");
            return "redirect:/member/login";
        }
        if(login_user.getMi_status() == 4){
            session.setAttribute("login_status", "leaving");
            session.setAttribute("login_msg", "계정이 탈퇴 대기 상태입니다.");
            return "redirect:/member/login";
        }
        if(login_user.getMi_status() == 5){
            session.setAttribute("login_status", "banned");
            session.setAttribute("login_msg", "영구 정지된 사용자입니다.");
            return "redirect:/member/login";
        }
        session.setAttribute("login_status", "success");
        session.setAttribute("login_msg", null);
        session.setAttribute("login_user", login_user);
        return "redirect:/";
    }
    @GetMapping("/join")
    public String getMemberJoin(){
        return "/member/join";
    }
    @GetMapping("/logout")
    public String getMemberLogout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }
}
