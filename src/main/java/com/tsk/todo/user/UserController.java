package com.tsk.todo.user;

import com.tsk.todo.exception.ResultResponse;
import com.tsk.todo.pojo.User;
import com.tsk.todo.req.LoginReq;
import com.tsk.todo.req.RegisterReq;
import com.tsk.todo.resp.CommonResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
@CrossOrigin(origins = "*")
public class UserController {

  @Resource()
  private  IUserService userService;

  @GetMapping()
  public CommonResp<List<User>> getUser(){
    List<User> list = userService.list();
    return  new CommonResp<>(list);
  }

  @PostMapping("login")
  public ResultResponse<String> login(@Valid @RequestBody LoginReq login){
    return userService.login(login);
  }

  @PostMapping("/register")
  public ResultResponse<String> register(@Valid @RequestBody RegisterReq user){
    return userService.register(user);
  }

  @GetMapping("/detail")
  public ResultResponse<User> getUserById(){
    return userService.getUserDetail();
  }

}
