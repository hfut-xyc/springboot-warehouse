package org.server;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import org.server.entity.OrderStatus;

public class OrderStatusTest {
    @Test
    void Test() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        OrderStatus s = OrderStatus.NORMAL;
        System.out.println(objectMapper.writeValueAsString(s));
        System.out.println(s.getStatus());

        // Java 没有运算符重载，所以不可以这样搞
        // OrderStatus s1 = "正常";

        try {
            s.setStatus("哈哈");
        }
        catch (EnumConstantNotPresentException e){
            System.out.println(e.getMessage());
        }
    }

}