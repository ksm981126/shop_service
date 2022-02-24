package com.twogenesis.shoppingmall_service.api;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.twogenesis.shoppingmall_service.data.MemberVO;
import com.twogenesis.shoppingmall_service.data.ReviewReportHistoryVO;
import com.twogenesis.shoppingmall_service.data.ShoppingCartVO;
import com.twogenesis.shoppingmall_service.mapper.HistoryMapper;
import com.twogenesis.shoppingmall_service.mapper.ProductMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
public class ProductAPIController {
    @Autowired ProductMapper prod_mapper;
    @Autowired HistoryMapper history_mapper;
    @PutMapping("/cart")
    public Map<String, Object> putProductAsCart(@RequestBody ShoppingCartVO data){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

        ShoppingCartVO cart_data = prod_mapper.selectShoppingCartItem(data);
        if(cart_data != null){
            prod_mapper.updateProductCountToCart(data);
            resultMap.put("status", true);
            resultMap.put("operation", "increase");
            resultMap.put("message", "제품의 수량이 변경되었습니다.");
            return resultMap;
        }
        prod_mapper.insertProductToCart(data);
        resultMap.put("status", true);
        resultMap.put("operation", "add");
        resultMap.put("message", "제품이 장바구니에 추가되었습니다.");
        return resultMap;
    }
    @PatchMapping("/cart")
    public Map<String,Object> patchProductCartItem(@RequestBody ShoppingCartVO data){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

        prod_mapper.updateCartItemStatus(data);
        resultMap.put("status", true);
        if(data.getScd_status() == 1){
            resultMap.put("operation", "buy");
            resultMap.put("message", "제품 구매");
        }
        else{
            resultMap.put("operation", "cancel");
            resultMap.put("message", "장바구니에서 제품을 삭제했습니다.");
        }
        return resultMap;
    }

    @PatchMapping("/cart/count")
    public Map<String,Object> patchCartCount(@RequestParam Integer seq, @RequestParam Integer count){
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        prod_mapper.updateCartItemCount(seq, count);
        resultMap.put("status", true);
        resultMap.put("message", "수량이 변경 되었습니다.");

        return resultMap ;
    }

    @GetMapping("/cart/count")
    public Integer getCartCount(@RequestParam Integer member_seq){
        return prod_mapper.selectShoppingCartItemCount(member_seq);
    }

    @PatchMapping("cart/delete")
    public String patchCartStatusToDelete(@RequestParam Integer seq){
        prod_mapper.updateCartItemStatusToDelete(seq);
        return "제품이 장바구니에서 제거되었습니다.";
    }
    @GetMapping("/review")
    public Map<String,Object> getProductReview(
        @RequestParam Integer pi_seq, @RequestParam Integer offset, HttpSession session
        ){
        MemberVO login_user =(MemberVO)session.getAttribute("login_user");
        Integer member =0;
        if(login_user != null) member =login_user.getMi_seq();

        Map<String,Object> resultMap = new LinkedHashMap<String,Object>();
        resultMap.put("list", prod_mapper.selectReviewList(pi_seq, offset, member));
        resultMap.put("count", prod_mapper.selectReviewCnt(pi_seq));
        return resultMap;
    }
    @PatchMapping("/review/report")
    public Map<String,Object> patchProductReviewReport(@RequestParam Integer ri_seq, HttpSession session){
        Map<String,Object> resultMap = new LinkedHashMap<String,Object>();
        MemberVO login_user =(MemberVO)session.getAttribute("login_user");
        if(login_user == null){
            resultMap.put("status", false);
            resultMap.put("msg", "리뷰 신고는 로그인 후 할 수있습니다.");
            return resultMap;
        }
        prod_mapper.updateReviewReport(ri_seq);
        ReviewReportHistoryVO data = new ReviewReportHistoryVO();
        data.setRrh_ri_seq(ri_seq);
        data.setRrh_mi_seq(login_user.getMi_seq());
        
        history_mapper.insertReviewReportHistory(data);
        
        resultMap.put("status", true);
        resultMap.put("msg", "신고가 접수되었습니다.");
        return resultMap;
    }
}
