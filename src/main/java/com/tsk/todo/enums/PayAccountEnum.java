package com.tsk.todo.enums;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Tsk
 * @date 2024/7/9 0009
 */
@Getter
public enum PayAccountEnum  {
    ALIPAY("支付宝",0),
    WECHATPAY("微信支付",1),
    BANKCARD("银行卡",2);

    private final String name;
    private final Integer code;

    PayAccountEnum(String name,Integer code) {
        this.name = name;
        this.code = code;
    }


    public static List<Map<String, Object>> getAll() {
        List<Map<String, Object>> list = new ArrayList<>();
        for(PayAccountEnum c : PayAccountEnum.values()){
            list.add(Map.of("label", c.name, "code", c.code));
        }
        return list;
    }
}
