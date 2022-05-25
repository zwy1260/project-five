package com.abc.rkodemo.controller;

import com.abc.rkodemo.domain.User;
import com.abc.rkodemo.exception.DataAccessException;
import com.abc.rkodemo.exception.DataStorageException;
import com.abc.rkodemo.service.UserService;
import com.abc.rkodemo.utils.MessageConstant;
import com.abc.rkodemo.utils.Result;
import com.abc.rkodemo.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登录
     * 访问http://localhost:8082/rkoShop/users/login
     * post提交
     * <p>
     * json数据
     * {
     * "phone": "13706008960",
     * "password": "123456"
     * }
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        try {

            // 验证密码,如果账号不存在也会抛出数据访问异常
            userService.verifyPassword(user);

            // 生成token，存储手机号
            String token = TokenUtil.getToken(user.getPhone());

            //返回token的信息
            return new Result(true, MessageConstant.LOGIN_SUCCESS, token);

        } catch (DataAccessException e) {
            return new Result(false, e.getMessage());
        } catch (DataStorageException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            //返回错误信息	{账号不存在	或	密码错误}
            return new Result(false, MessageConstant.LOGIN_FAIL);
        }
    }

    /**
     * 注册
     * 访问http://localhost:8082/rkoShop/users
     * post提交
     * <p>
     * json数据
     * {
     * "phone": "13706008960",
     * "password": "123456"
     * }
     */
    @PostMapping("")
    public Result registered(@RequestBody User user) {

        try {
            //注册账号
            userService.registered(user);
            //返回注册成功
            return new Result(true, MessageConstant.REGISTER_SUCCESS);
        } catch (DataAccessException e) {
            return new Result(false, e.getMessage());
        } catch (DataStorageException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            //返回错误信息
            return new Result(false, MessageConstant.REGISTER_FAIL);
        }
    }

    /**
     * 获取用户信息
     * http://localhost:8082/rkoShop/users/data?token=
     * get提交
     * <p>
     * {phone}: 要查询的电话号码
     * token:	  验证的token
     * <p>
     * 查询账号所有的信息：{ 手机号、用户名、账户余额、头像地址、性别、年龄、爱好 }
     */
    @GetMapping("/data")
    public Result queryUserAllData(@RequestParam String token, HttpServletRequest request) {

        if (!TokenUtil.verify(token))
            return new Result(false, MessageConstant.UNLOGIN_RIGSTER);

        //获取该程序的访问网址，例如http://127.0.0.1:8082/rkoShop/
        StringBuffer url = new StringBuffer();
        url.append("http://")
                .append(request.getServerName())
                .append(":")
                .append(request.getServerPort())
                .append(request.getContextPath());

        try {
            User user = userService.queryUserAllData(TokenUtil.get(token, "phone"));

            //拼接上url后，就是真正图片的网址
            user.setUserImage(url + user.getUserImage());

            return new Result(true, MessageConstant.QUERY_USER_SUCCESS, user);
        } catch (DataAccessException e) {
            return new Result(false, e.getMessage());
        } catch (DataStorageException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            //返回错误信息
            return new Result(false, MessageConstant.QUERY_USER_FAIL);
        }
    }

    /**
     * 修改用户信息
     * 访问 http://localhost:8082/rkoShop/users/{phone}
     * put提交
     * {
     * "token": "",			(必填)
     * "phone": "13706008960",	(必填)
     * "userName": "小张",
     * "password": "666666",
     * "sex": "男",
     * "age": 10,
     * "hobby": "爱喝奶茶"
     * }
     */
    @PutMapping("/{phone}")
    public Result updateUserData(
            @PathVariable String phone,    //获取url路径上的{phone}
            @RequestBody Map<String, String> data    //所有的数据
    ) {

        if (!TokenUtil.verify(data.get("token")))
            return new Result(false, MessageConstant.UNLOGIN_RIGSTER);

        try {

            User user = new User();
            // 将传递过来的数据封装到user中
            dataSetUser(data, user);
            //存储电话
            user.setPhone(phone);
            //修改信息
            userService.modifyUser(user);
            return new Result(true, MessageConstant.EDIT_USER_SUCCESS);
        } catch (DataAccessException e) {
            return new Result(false, e.getMessage());
        } catch (DataStorageException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            //返回错误信息
            return new Result(false, MessageConstant.EDIT_USER_FAIL);
        }

    }


    /**
     * http://localhost:8082/rkoShop/users/verification
     */
    @PostMapping("/verification")
    public Result verificationStatus(@RequestBody Map<String, String> data) {

        String token = data.get("token");

        //验证token是否有效
        if (!TokenUtil.verify(token))
            return new Result(false, "请重新登录", false);

        return new Result(true, "已登录", true);
    }

    /**
     * 将map中的数据封装到user中
     */
    private void dataSetUser(Map<String, String> data, User user) {

        user.setPhone(data.get("phone"));
        user.setUserName(data.get("userName"));
        user.setPassword(data.get("password"));
        user.setSex(data.get("sex"));
        if (data.get("age") != null)
            user.setAge(Integer.valueOf(data.get("age")));
        if (data.get("balance") != null)
            user.setBalance(Double.valueOf(data.get("balance")));
        user.setHobby(data.get("hobby"));

    }

}
