package com.zcf.controller;


import com.zcf.pojo.Emp;
import com.zcf.pojo.LoginInfo;
import com.zcf.pojo.Result;
import com.zcf.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private EmpService empService;

    /**
     * 员工登录
     * @param emp
     * @return
     */
    @PostMapping
    public Result login(@RequestBody Emp  emp){
        log.info("登录：{}", emp);
        LoginInfo loginInfo =empService.login(emp);
        return loginInfo == null ? Result.error("用户名或密码错误") : Result.success(loginInfo);
    }

}
