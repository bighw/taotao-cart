package com.taotao.cart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.cart.mapper.CartMapper;
import com.taotao.cart.pojo.Cart;
import com.taotao.cart.vo.TaotaoResult;

@Service
public class CartService {

    @Autowired
    private CartMapper cartMapper;

    public TaotaoResult save(Cart cart) {
        // 判断该商品是否存在
        try {
            Cart cartData = this.cartMapper.queryCart(cart.getUserId(), cart.getItemId());
            if (cartData == null) {
                this.cartMapper.save(cart);
                return TaotaoResult.ok();
            } else {
                // 如果存在，商品数量加num
                cartData.setNum(cartData.getNum() + cart.getNum());
                this.cartMapper.updateNum(cartData);
                return TaotaoResult.build(202, "该商品已经存在，数据已增加!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(201, "添加商品到购物车失败!");
        }
    }

    public TaotaoResult query(Long userId) {
        List<Cart> carts = this.cartMapper.queryByUserId(userId);
        return TaotaoResult.ok(carts);
    }

    public TaotaoResult updateNum(Long userId, Long itemId, Integer num) {
        return TaotaoResult.ok(this.cartMapper.updateNumByUserIdAndItemId(userId, itemId, num));
    }

    public TaotaoResult deleteCart(Long userId, Long itemId) {
        return TaotaoResult.ok(this.cartMapper.deleteByUserIdAndItemId(userId, itemId));
    }

    public TaotaoResult query(Long userId, Long[] itemIds) {
        return TaotaoResult.ok(this.cartMapper.queryByUserIdAndItemIds(userId, itemIds));
    }

}
