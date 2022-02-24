package com.twogenesis.shoppingmall_service.mapper;

import java.util.List;

import com.twogenesis.shoppingmall_service.data.CategoryVO;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {
    public List<CategoryVO> selectCategories(Integer parent);
    public String selectCategoryName(Integer seq);
    public List<CategoryVO> selectSubCategories(Integer seq);
}
