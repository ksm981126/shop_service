package com.twogenesis.shoppingmall_service.api;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.twogenesis.shoppingmall_service.controller.OrderController;
import com.twogenesis.shoppingmall_service.data.OrderInfoVO;
import com.twogenesis.shoppingmall_service.data.ShoppingCartVO;
import com.twogenesis.shoppingmall_service.mapper.OrderMapper;
import com.twogenesis.shoppingmall_service.mapper.ProductMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderAPIController {
    @Autowired OrderMapper order_mapper;
    @Autowired ProductMapper prod_mapper;
    @PutMapping("/add")
    public ResponseEntity<Object> putOrderItems(@RequestBody List<OrderInfoVO> itemList){
        Map<String,Object> resultMap = new LinkedHashMap<String,Object>();
        // order_mapper.insertOrderInfo(itemList);

        int total_price=0;
        int total_d_price=0;
        for(OrderInfoVO item : itemList) {
            total_price +=item.getOi_price() * item.getOi_count();
            total_d_price +=item.getDelivery_price();
        }
        System.out.println(total_price);
        System.out.println(total_d_price);
        System.out.println(total_price+total_d_price);
        System.out.println(OrderController.order_price);
        if(total_price+total_d_price != OrderController.order_price){
            resultMap.put("status",false);
            resultMap.put("message", "정상적인 요청이 아닙니다.");
            return new ResponseEntity<Object>(resultMap, HttpStatus.FORBIDDEN);
        }
        order_mapper.insertOrderInfo(itemList);
        for(OrderInfoVO item : itemList){
            ShoppingCartVO cart_item = new ShoppingCartVO();
            cart_item.setScd_mi_seq(item.getOi_mi_seq());
            cart_item.setScd_pi_seq(item.getOi_pi_seq());
            cart_item.setScd_status(1);
            prod_mapper.updateCartItemStatus(cart_item);
        }
        resultMap.put("status",true);
        resultMap.put("message", "주문이 완료되었습니다.");
        
        return new ResponseEntity<Object>(resultMap, HttpStatus.OK);
    }
    
}
