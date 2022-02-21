package com.twogenesis.shoppingmall_service.data;

import lombok.Data;

@Data
public class CategoryVO {
    private Integer cate_seq;
    private String cate_name;
    private Integer cate_parent;
}
