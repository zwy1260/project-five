package com.abc.rkodemo.controller;

import com.abc.rkodemo.domain.Shoppingcart;
import com.abc.rkodemo.domain.ShoppingcartDetails;
import com.abc.rkodemo.exception.DataAccessException;
import com.abc.rkodemo.exception.DataStorageException;
import com.abc.rkodemo.service.ShoppingcartService;
import com.abc.rkodemo.utils.MessageConstant;
import com.abc.rkodemo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/shopping")
public class ShoppingcartController {

    @Autowired
    private ShoppingcartService shoppingcartService;

    /**
     * 向购物车添加商品
     * post提交
     * http://localhost:8082/rkoShop/shopping/{userPhone}
     * {
     * "commodityId":
     * "num":
     *
     * }
     */
    @PostMapping("/{userPhone}")
    public Result addShoppingcartDetails(@PathVariable String userPhone, @RequestBody ShoppingcartDetails details) {

        try {
            // 将商品添加进购物车
            shoppingcartService.addShoppingcartDetails(details, userPhone);

            return new Result(true, MessageConstant.ADD_SHOPPINGCART_SUCCESS);
        } catch (DataAccessException e) {
            return new Result(false, e.getMessage());
        } catch (DataStorageException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            // 返回错误信息
            return new Result(false, MessageConstant.ADD_SHOPPINGCART_FAIL);
        }
    }

    /**
     * 删除购物车的商品
     * delete提交
     * http://localhost:8082/rkoShop/shopping/{detailsId}
     * 例如：http://localhost:8082/rkoShop/shopping/3
     */
    @DeleteMapping("/{detailsId}")
    public Result removeShoppingcartDetails(@PathVariable int detailsId) {

        try {
            // 删除购物车的某个商品
            shoppingcartService.removeShoppingcartDetails(detailsId);

            return new Result(true, MessageConstant.DELETE_SHOPPINGCART_SUCCESS);
        } catch (DataAccessException e) {
            return new Result(false, e.getMessage());
        } catch (DataStorageException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            // 返回错误信息
            return new Result(false, MessageConstant.DELETE_SHOPPINGCART_FAIL);
        }
    }

    /**
     * 清空购物车 DELET提交
     */
    @DeleteMapping("/{userPhone}/all")
    public Result removeAllShoppingcartDetails(@PathVariable String userPhone) {

        try {
            // 删除购物车的某个商品
            shoppingcartService.removeAllShoppingcartDetails(userPhone);

            return new Result(true, MessageConstant.DELETE_SHOPPINGCART_SUCCESS);
        } catch (DataAccessException e) {
            return new Result(false, e.getMessage());
        } catch (DataStorageException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            // 返回错误信息
            return new Result(false, MessageConstant.DELETE_SHOPPINGCART_FAIL);
        }
    }

    /**
     * 修改购物车的商品数量
     * PUT提交
     * http://localhost:8082/rkoShop/shopping/{detailsId}
     * {
     * "num":
     * }
     */
    @PutMapping("/{detailsId}")
    public Result modifyShoppingcartDetailsNum(@PathVariable int detailsId,
                                               @RequestBody ShoppingcartDetails details) {

        try {
            // 存入商品在购物车中的id
            details.setDetailsId(detailsId);

            // 修改商品数量
            shoppingcartService.modifyShoppingcartDetailsNum(details);

            return new Result(true, MessageConstant.EDIT_SHOPPINGCART_SUCCESS);
        } catch (DataAccessException e) {
            return new Result(false, e.getMessage());
        } catch (DataStorageException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            // 返回错误信息
            return new Result(false, MessageConstant.EDIT_SHOPPINGCART_FAIL);
        }
    }

    /**
     * 查询购物车所有的商品
     * GET提交
     * http://localhost:8082/rkoShop/shopping/{userPhone}
     */
    @GetMapping("/{userPhone}")
    public Result queryShoppingcart(@PathVariable String userPhone,HttpServletRequest request) {
        try {

            Shoppingcart shoppingcart = shoppingcartService.queryShoppingcart(userPhone);

            List<ShoppingcartDetails> detailsList = shoppingcart.getDetailsList();
            return new Result(true, MessageConstant.QUERY_SHOPPINGCART_SUCCESS, shoppingcart);
        } catch (DataAccessException e) {
            return new Result(false, e.getMessage());
        } catch (DataStorageException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            // 返回错误信息
            return new Result(false, MessageConstant.QUERY_SHOPPINGCART_FAIL);
        }
    }

}
