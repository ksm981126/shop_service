package com.twogenesis.shoppingmall_service.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.twogenesis.shoppingmall_service.data.CartInfoVO;
import com.twogenesis.shoppingmall_service.data.MemberVO;
import com.twogenesis.shoppingmall_service.mapper.ProductMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class OrderController {
    public static Integer order_price =0;
    @Autowired ProductMapper prod_mapper;
    @GetMapping("/checkout")
    public String getOrderCheckout(Model model, HttpSession session){
        MemberVO login_user =(MemberVO)session.getAttribute("login_user");
        if(login_user == null) return "redirect:/member/login";
        
        List<CartInfoVO> item_list = prod_mapper.selectcartInfo(login_user.getMi_seq());
        model.addAttribute("order_items",item_list);
        int prod_price =0;
        int delivery_price =0;

        for(CartInfoVO item : item_list){
            prod_price += item.getDiscounted_price() *item.getScd_count();
            delivery_price += item.getDi_price();
        }
        order_price=prod_price+delivery_price;

        model.addAttribute("prod_price",prod_price);
        model.addAttribute("delivery_price",delivery_price);
        model.addAttribute("total_price",prod_price+delivery_price);
        return "/order/checkout";
    }
}
