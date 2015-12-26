package com.taotao.cart.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.taotao.cart.service.CartService;

@Component
public class OrderSuccessHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderSuccessHandler.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    @Autowired
    private CartService cartService;

    /**
     * 下单成功后删除对应的购物车操作
     * 
     * @param msg
     * @throws Exception
     */
    public void handler(String msg) throws Exception {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("接收到消息，内容为：{}", msg);
        }

        JsonNode jsonNode = MAPPER.readTree(msg);
        Long userId = jsonNode.get("userId").asLong();
        ArrayNode itemIds = (ArrayNode) jsonNode.get("itemIds");
        for (JsonNode itemId : itemIds) {
            this.cartService.deleteCart(userId, itemId.asLong());
        }
    }

}
