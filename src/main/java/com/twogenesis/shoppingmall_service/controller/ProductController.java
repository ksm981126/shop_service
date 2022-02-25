package com.twogenesis.shoppingmall_service.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.twogenesis.shoppingmall_service.data.CategoryVO;
import com.twogenesis.shoppingmall_service.data.MemberVO;
import com.twogenesis.shoppingmall_service.mapper.CategoryMapper;
import com.twogenesis.shoppingmall_service.mapper.ProductMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired ProductMapper prod_mapper;
    @Autowired CategoryMapper cate_mapper;
    @GetMapping("/detail")
    public String getProductDetail(@RequestParam Integer index, Model model,HttpSession session){
        MemberVO login_user = (MemberVO)session.getAttribute("login_user");
        if(login_user != null){
            Calendar c = Calendar.getInstance();
            model.addAttribute("item_seq",index);
            model.addAttribute("conn_time",c.getTimeInMillis());
        }

        model.addAttribute("item",prod_mapper.selectProductBySeq(index));
        model.addAttribute("item_img",prod_mapper.selectProductImages(index));
        model.addAttribute("item_desc_img",prod_mapper.selectProductDescImages(index));
        model.addAttribute("item_desc",prod_mapper.selectProductDesc(index));
        model.addAttribute("score",prod_mapper.selectProductScore(index));
        return "/product/detail";
    }
    @GetMapping("/list")
    public String getProductList(@RequestParam Integer category,@RequestParam@Nullable Integer offset, Model model){
        if(offset == null) offset=0;
        //최 하위 카테고리 (끝) -가져온 child 카테고리가 0개라면 , 마지막 단계
        List<CategoryVO> sub_cate_list =cate_mapper.selectSubCategories(category);
        List<Integer> cate_seq_list = new ArrayList<Integer>();
        cate_seq_list.add(category);
        for(CategoryVO cate : sub_cate_list){
            cate_seq_list.add(cate.getCate_seq());
            List<CategoryVO> sub_sub_cate_list = cate_mapper.selectSubCategories(cate.getCate_seq());
            for(CategoryVO cate2 : sub_sub_cate_list){
                cate_seq_list.add(cate2.getCate_seq());
            }
        }
        Integer cnt =prod_mapper.selectProductsCntByCateSeqList(cate_seq_list);
        Integer page = (cnt/12)+(cnt%12 >0 ? 1 : 0);

        model.addAttribute("cate_name",cate_mapper.selectCategoryName(category));
        model.addAttribute("list",prod_mapper.selectProductByCateSeqList(cate_seq_list,offset));
        model.addAttribute("page",page);
        model.addAttribute("category",category);
        model.addAttribute("page_type","category");
        return"/product/list";
    }
    @GetMapping("/search")
    public String getProductSearch(
        @RequestParam String type,
        @RequestParam String keyword,
        @RequestParam@Nullable Integer offset,
        Model model
    ){
        if(offset == null) offset=0;

        model.addAttribute("type",type);
        model.addAttribute("keyword",keyword);

        keyword ="%"+keyword+"%";

        Integer cnt =prod_mapper.selectProductSearchCnt(keyword, type);
        Integer page = (cnt/12)+(cnt%12 >0 ? 1 : 0);

        model.addAttribute("list",prod_mapper.selectProductSearch(keyword, offset, type));
        model.addAttribute("page_type","search");
        model.addAttribute("page",page);
        return "/product/list";
    }
}
