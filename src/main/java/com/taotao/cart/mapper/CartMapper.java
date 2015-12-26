package com.taotao.cart.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.taotao.cart.pojo.Cart;

public interface CartMapper {

    Cart queryCart(@Param("userId") Long userId, @Param("itemId") Long itemId);

    void save(Cart cart);

    Integer updateNum(Cart cart);

    List<Cart> queryByUserId(Long userId);

    Integer updateNumByUserIdAndItemId(@Param("userId") Long userId, @Param("itemId") Long itemId,
            @Param("num") Integer num);

    Integer deleteByUserIdAndItemId(@Param("userId") Long userId, @Param("itemId") Long itemId);

    List<Cart> queryByUserIdAndItemIds(@Param("userId") Long userId, @Param("itemIds") Long[] itemIds);

}
