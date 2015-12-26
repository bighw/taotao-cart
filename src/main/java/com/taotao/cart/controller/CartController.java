package com.taotao.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.cart.pojo.Cart;
import com.taotao.cart.service.CartService;
import com.taotao.cart.vo.TaotaoResult;

@RequestMapping("cart")
@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * 添加商品到购物车
     * 
     * @param cart
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult save(Cart cart) {
        return this.cartService.save(cart);
    }

    /**
     * 根据用户ID查询购物车数据
     * 
     * @param userId
     * @return
     */
    @RequestMapping(value = "query/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public TaotaoResult query(@PathVariable("userId") Long userId) {
        return this.cartService.query(userId);
    }

    /**
     * 根据用户ID以及商品ID查询购物车数据
     * 
     * @param userId
     * @return
     */
    @RequestMapping(value = "query/{userId}", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult query(@PathVariable("userId") Long userId, @RequestParam("itemIds") Long[] itemIds) {
        return this.cartService.query(userId, itemIds);
    }

    /**
     * 更新商品数量
     * 
     * @param userId
     * @param itemId
     * @param num 最终购买数量，非增量购买
     * @return
     */
    @RequestMapping(value = "update/num/{userId}/{itemId}/{num}", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult updateNum(@PathVariable("userId") Long userId, @PathVariable("itemId") Long itemId,
            @PathVariable("num") Integer num) {
        return this.cartService.updateNum(userId, itemId, num);
    }

    /**
     * 删除购物车信息
     * 
     * @param userId
     * @param itemId
     * @return
     */
    @RequestMapping(value = "delete/{userId}/{itemId}", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult deleteCart(@PathVariable("userId") Long userId, @PathVariable("itemId") Long itemId) {
        return this.cartService.deleteCart(userId, itemId);
    }

}
