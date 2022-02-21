package com.twogenesis.shoppingmall_service.mapper;

import java.util.List;

import com.twogenesis.shoppingmall_service.data.CartInfoVO;
import com.twogenesis.shoppingmall_service.data.ProductDescImageVO;
import com.twogenesis.shoppingmall_service.data.ProductDescVO;
import com.twogenesis.shoppingmall_service.data.ProductImageVO;
import com.twogenesis.shoppingmall_service.data.ProductVO;
import com.twogenesis.shoppingmall_service.data.ShoppingCartVO;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper {
    List<ProductVO> selectProductsAll();
    List<ProductVO> selectRecommendProducts();
    List<ProductVO> selectProductByCateSeq(Integer seq);
    List<Integer> selectProductCategories();

    ProductVO selectProductBySeq(Integer seq);
    List<ProductImageVO> selectProductImages(Integer seq);
    List<ProductDescImageVO> selectProductDescImages(Integer seq);
    List<ProductDescVO> selectProductDesc(Integer seq);

    void insertProductToCart(ShoppingCartVO data);
    ShoppingCartVO selectShoppingCartItem(ShoppingCartVO data);
    void updateProductCountToCart(ShoppingCartVO data);
    void updateCartItemStatus(ShoppingCartVO data);
    void updateCartItemCount(Integer seq, Integer count);

    Integer selectShoppingCartItemCount(Integer member_seq);

    List<CartInfoVO> selectcartInfo(Integer seq);
    void updateCartItemStatusToDelete(Integer seq);
}
