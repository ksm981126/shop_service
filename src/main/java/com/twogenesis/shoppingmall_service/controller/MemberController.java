package com.twogenesis.shoppingmall_service.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.twogenesis.shoppingmall_service.data.CartInfoVO;
import com.twogenesis.shoppingmall_service.data.MemberVO;
import com.twogenesis.shoppingmall_service.data.ProductVO;
import com.twogenesis.shoppingmall_service.mapper.MemberMapper;
import com.twogenesis.shoppingmall_service.mapper.OrderMapper;
import com.twogenesis.shoppingmall_service.mapper.ProductMapper;
import com.twogenesis.shoppingmall_service.util.AESAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/member")
public class MemberController {
    @Autowired OrderMapper order_mapper;
    @Autowired MemberMapper mapper;
    @Autowired ProductMapper prod_mapper;
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
    public String postMemberLogin(HttpSession session, String user_email, String user_pwd,String prev_url)throws Exception{
        System.out.println(prev_url);
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

        // String url =(String)session.getAttribute("prev_url");
        // System.out.println((String)session.getAttribute("prev_url"));

        return "redirect:"+prev_url;
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
    @GetMapping("/cart")
    public String getMemberCart(Model model, HttpSession session){
        MemberVO login_user =(MemberVO)session.getAttribute("login_user");
        if(login_user == null) return "redirect:/member/login";

        List<CartInfoVO> list =prod_mapper.selectcartInfo(login_user.getMi_seq());
        Double total_price = 0.0;
        Double total_d_price = 0.0;

        for(CartInfoVO item : list){
            total_price += item.getDiscounted_price() * item.getScd_count();
            total_d_price += item.getDi_price();
        }

        model.addAttribute("list",list);
        model.addAttribute("total_price",total_price);
        model.addAttribute("total_d_price",total_d_price);
        return "/member/cart";
    }
    @GetMapping("/order_list")
    public String getMemberOrderList(Model model, @RequestParam @Nullable Integer offset, HttpSession session){
        MemberVO login_user = (MemberVO)session.getAttribute("login_user");
        if(login_user == null) return "redirect:/member/login";

        if(offset == null) offset =0;
        Integer cnt =order_mapper.selectOrderSummaryCount(login_user.getMi_seq());
        Integer page = (cnt/10) + (cnt%10 >0 ? 1:0);

        model.addAttribute("list",order_mapper.selectOrderSummary(login_user.getMi_seq(), offset));
        model.addAttribute("page",page);
        model.addAttribute("offset",offset);
        return "/member/order_list";
    }
    @GetMapping("/review")
    public String getMemberReview(
        @RequestParam Integer product, 
        @RequestParam Integer order, 
        HttpSession session,Model model
        ){
        MemberVO login_user = (MemberVO)session.getAttribute("login_user");
        if(login_user == null) return "redirect:/member/login";

        if(mapper.isExistReview(login_user.getMi_seq(), product, order) == 1){
            return "redirect:/member/order_list";
        }

        model.addAttribute("product",product);
        model.addAttribute("order",order);
        model.addAttribute("prod",prod_mapper.selectProductBySeq(product));
        return "/member/review";
    }
}
