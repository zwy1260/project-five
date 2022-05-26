package com.abc.rkodemo.controller;

import com.abc.rkodemo.domain.Commodity;
import com.abc.rkodemo.domain.UserCommodity;
import com.abc.rkodemo.exception.DataAccessException;
import com.abc.rkodemo.exception.DataStorageException;
import com.abc.rkodemo.service.CommodityService;
import com.abc.rkodemo.service.UserService;
import com.abc.rkodemo.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/commoditys")
public class CommodityController {
    @Autowired
    private UserService userService;
    @Autowired
    private CommodityService commodityService;
    @Autowired
    private JedisPool jedisPool;

    /**
     * 获取所有商品信息
     * http://localhost:8082/rkoShop/commoditys
     * get提交
     */
    @GetMapping("")
    public Result getAll(HttpServletRequest request) {
        try {
            //获取该程序的访问网址，例如http://127.0.0.1:8082/rkoShop/
            StringBuffer url = new StringBuffer();
            url.append("http://")
                    .append(request.getServerName())
                    .append(":")
                    .append(request.getServerPort())
                    .append(request.getContextPath());

            //获取所有商品
            List<Commodity> commodities = commodityService.getAllCommodities();
            for (Commodity commodity : commodities) {
                commodity.getUser().setUserImage(url + commodity.getUser().getUserImage());
            }
            return new Result(true, MessageConstant.QUERY_COMMODITY_SUCCESS, commodities);
        } catch (DataAccessException e) {
            return new Result(false, e.getMessage());
        } catch (DataStorageException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            return new Result(false, MessageConstant.QUERY_COMMODITY_FAIL);
        }
    }
    /**
     * 获取除了指定手机号外所有商品信息
     * http://localhost:8082/rkoShop/commoditys/11111111111
     * get提交
     */
    @GetMapping("/{phone}")
    public Result getAllByPhone(@PathVariable String phone, HttpServletRequest request) {
        try {
            //获取该程序的访问网址，例如http://127.0.0.1:8082/rkoShop/
            StringBuffer url = new StringBuffer();
            url.append("http://")
                    .append(request.getServerName())
                    .append(":")
                    .append(request.getServerPort())
                    .append(request.getContextPath());

            //获取所有商品
            List<Commodity> commodities = commodityService.getAllByPhone(phone);
            for (Commodity commodity : commodities) {
                commodity.getUser().setUserImage(url + commodity.getUser().getUserImage());
            }
            return new Result(true, MessageConstant.QUERY_COMMODITY_SUCCESS, commodities);
        } catch (DataAccessException e) {
            return new Result(false, e.getMessage());
        } catch (DataStorageException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            return new Result(false, MessageConstant.QUERY_COMMODITY_FAIL);
        }
    }
    /**
     * 获取除了指定手机号外满足名称条件所有商品信息
     * http://localhost:8082/rkoShop/commoditys/select/qq糖
     * post提交
     * {
     *     phone: "",
     * }
     */
    @PostMapping("/select/{title}")
    public Result getAllByString(@PathVariable String title,@RequestBody String phone, HttpServletRequest request) {
        try {
            //获取该程序的访问网址，例如http://127.0.0.1:8082/rkoShop/
            StringBuffer url = new StringBuffer();
            url.append("http://")
                    .append(request.getServerName())
                    .append(":")
                    .append(request.getServerPort())
                    .append(request.getContextPath());

            if (title.equals("null")){
                title="";
            }
            //获取所有商品
            List<Commodity> commodities = commodityService.getAllByString(title.trim(),phone);
            for (Commodity commodity : commodities) {
                commodity.getUser().setUserImage(url + commodity.getUser().getUserImage());
            }
            return new Result(true, MessageConstant.QUERY_COMMODITY_SUCCESS, commodities);
        } catch (DataAccessException e) {
            return new Result(false, e.getMessage());
        } catch (DataStorageException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            return new Result(false, MessageConstant.QUERY_COMMODITY_FAIL);
        }
    }
    /**
     * 获取指定手机号的所有商品信息
     * http://localhost:8082/rkoShop/commoditys/all/11111111111
     * get提交
     */
    @GetMapping("/all/{phone}")
    public Result getAllCommodity(@PathVariable String phone, HttpServletRequest request) {
        try {
            //获取该程序的访问网址，例如http://127.0.0.1:8082/rkoShop/
            StringBuffer url = new StringBuffer();
            url.append("http://")
                    .append(request.getServerName())
                    .append(":")
                    .append(request.getServerPort())
                    .append(request.getContextPath());

            //获取所有商品
            List<Commodity> commodities = commodityService.getAllCommodity(phone);
            for (Commodity commodity : commodities) {
                commodity.getUser().setUserImage(url + commodity.getUser().getUserImage());
            }
            return new Result(true, MessageConstant.QUERY_COMMODITY_SUCCESS, commodities);
        } catch (DataAccessException e) {
            return new Result(false, e.getMessage());
        } catch (DataStorageException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_COMMODITY_FAIL);
        }
    }

    /**
     * 获取单个商品信息
     * http://localhost:8082/rkoShop/commoditys/layer/{commodityId}
     * get提交
     */
    @GetMapping("/layer/{commodityId}")
    public Result getCommodityById(@PathVariable int commodityId, HttpServletRequest request) {
        try {
            //根据id获取商品
            Commodity commodity = commodityService.getCommodityById(commodityId);

            return new Result(true, MessageConstant.QUERY_COMMODITY_SUCCESS, commodity);
        } catch (DataAccessException e) {
            return new Result(false, e.getMessage());
        } catch (DataStorageException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            return new Result(false, MessageConstant.QUERY_COMMODITY_FAIL);
        }
    }

    /**
     * 发布商品
     * 访问 http://localhost:8082/rkoShop/commoditys/{phone}
     * post提交
     * {
     * "token": "",			(必填)
     * "title": "QQ糖",
     * "image": "/images/commoditys/default.png",
     * "details": "我觉得还不错，你也来一口吧",
     * "price": 10,
     * "num": 10
     * "type": 1
     * }
     */
    @PostMapping("/{phone}")
    public Result addCommodity(
            @PathVariable String phone,    //获取url路径上的{phone}
            @RequestBody Map<String, String> data    //所有的数据
    ) {

        if (!TokenUtil.verify(data.get("token")))
            return new Result(false, MessageConstant.UNLOGIN_RIGSTER);

        try {

            Commodity commodity = new Commodity();
            // 将传递过来的数据封装到user中
            dataSetCommodity(data, commodity);
            //发布商品信息
            commodityService.addCommodity(commodity);
            int commodityId = commodity.getCommodityId();
            String imageName = commodity.getImage();
            //将图片保存到redis中
            jedisPool.getResource().sadd(RedisConstant.COMMODITY_PIC_DB_RESOURCES, imageName);

            UserCommodity userCommodity = new UserCommodity();
            userCommodity.setCommodityId(commodityId);
            userCommodity.setUserPhone(phone);
            //存储用户电话和商品id
            commodityService.addUserCommodity(userCommodity);
            return new Result(true, MessageConstant.ADD_COMMODITY_SUCCESS);
        } catch (DataAccessException e) {
            return new Result(false, e.getMessage());
        } catch (DataStorageException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            //返回错误信息
            return new Result(false, MessageConstant.ADD_COMMODITY_FAIL);
        }

    }

    /**
     * 修改商品信息
         * 访问 http://localhost:8082/rkoShop/commoditys/{commodityId}
     * put提交
     * {
     * "token": "",			(必填)
     * "title": "QQ糖",
     * "image": "/images/commoditys/default.png",
     * "details": "我觉得还不错，你也来一口吧",
     * "price": 10,
     * "num": 10
     * "type": 1
     * }
     */
    @PutMapping("/{commodityId}")
    public Result editCommodity(
            @PathVariable int commodityId, //获取url路径上的{commodityId}
            @RequestBody Map<String, String> data    //所有的数据
    ) {

        if (!TokenUtil.verify(data.get("token")))
            return new Result(false, MessageConstant.UNLOGIN_RIGSTER);

        try {
            Commodity commodity = new Commodity();
            // 将传递过来的数据封装到commodity中
            dataSetCommodity(data, commodity);
            //存储商品Id
            commodity.setCommodityId(commodityId);
            //更新商品信息
            commodityService.editCommodity(commodity);
            return new Result(true, MessageConstant.EDIT_COMMODITY_SUCCESS);
        } catch (DataAccessException e) {
            return new Result(false, e.getMessage());
        } catch (DataStorageException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            //返回错误信息
            return new Result(false, MessageConstant.EDIT_COMMODITY_FAIL);
        }

    }

    /**
     * 删除商品信息
     * http://localhost:8082/rkoShop/commoditys/{commodityId}
     * delete提交
     */
    @DeleteMapping("/{commodityId}")
    public Result DelCommodityById(@PathVariable int commodityId) {
        try {
            //根据id删除用户商品
            commodityService.delUserCommodityById(commodityId);
            //根据id删除商品
            commodityService.delCommodityById(commodityId);
            return new Result(true, MessageConstant.DELETE_COMMODITY_SUCCESS);
        } catch (DataAccessException e) {
            return new Result(false, e.getMessage());
        } catch (DataStorageException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            return new Result(false, MessageConstant.DELETE_COMMODITY_FAIL);
        }
    }

    //文件上传http://localhost:8082/rkoShop/commoditys/upload
    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile) {
        String originalFilename = imgFile.getOriginalFilename();//图片原文件名
        System.out.println(originalFilename);
        int index = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(index - 1);//.jpg
        String fileName = UUID.randomUUID().toString() + ext;//随机文件名

        try {
            //上传文件到七牛云服务器
            QiniuUtils.upload2Qiniu(imgFile.getBytes(), fileName);
            //将上传图片名称存入Redis，基于Redis的Set集合存储
            jedisPool.getResource().sadd(RedisConstant.COMMODITY_PIC_RESOURCES, fileName);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    /**
     * 将map中的数据封装到commodity中
     */
    private void dataSetCommodity(Map<String, String> data, Commodity commodity) {
        if (data.get("image") != null)
            commodity.setImage(data.get("image"));
        if (data.get("num") != null)
            commodity.setNum(Integer.valueOf(data.get("num")));
        if (data.get("price") != null)
            commodity.setPrice(Double.valueOf(data.get("price")));
        if (data.get("details") != null)
            commodity.setDetails(data.get("details"));
        if (data.get("title") != null)
            commodity.setTitle(data.get("title"));
        if (data.get("type") != null)
            commodity.setType(data.get("type"));

    }
}
