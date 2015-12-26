package com.taotao.cart.handler;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.cart.pojo.Cart;
import com.taotao.cart.service.CartService;
import com.taotao.common.service.RedisService;

/**
 * 
 * 接收登录后的消息，完成Redis和数据库的购物车数据合并
 * 
 */
@Component
public class LoginCartHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginCartHandler.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private CartService cartService;

    @Autowired
    private RedisService redisService;

    /**
     * 接收消息，完成合并业务逻辑
     * 
     * @param msg 消息内容，格式{"userId":1001,"data":"xxxxx"}
     * @throws Exception
     */
    public void handler(String msg) throws Exception {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("接收到消息，内容为：{}", msg);
        }
        JsonNode jsonNode = MAPPER.readTree(msg);
        Long userId = jsonNode.get("userId").asLong();
        String cartKey = jsonNode.get("data").asText();

        // 完成合并业务
        String key = "CART_" + cartKey;
        Map<String, String> map = this.redisService.hgetAll(key);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if ("updated".equals(entry.getKey())) {
                continue;
            }
            Cart cart = MAPPER.readValue(entry.getValue(), Cart.class);
            cart.setUserId(userId);
            //保存到数据库
            this.cartService.save(cart);
        }
        //删除Redis中的数据
        this.redisService.del(key);
    }

}
