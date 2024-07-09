package com.tsk.todo.enumsController;

import com.tsk.todo.enums.PayAccountEnum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author Tsk
 * @date 2024/7/9 0009
 */
@RestController
@RequestMapping("enums")
public class enumController {
    @GetMapping("payAccountEnum")
    public List<Map<String, Object>> payAccountEnum(){
        return PayAccountEnum.getAll();
    }
}
