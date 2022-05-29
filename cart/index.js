// pages/cart/index.js
Page({
  /**
   * 页面的初始数据
   */
  data: {
    allprice: "",
    allcart:[],
    data:[],
    defaultadd:{}
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad() {
    var token = wx.getStorageSync("token");
    if (!token) {
     this.setData({
      allcart:"",
      data:"",
      defaultadd:""
     })
    }
  },
  onShow: function (options) {
    var token = wx.getStorageSync("token");
    if (token) {
      var address = wx.getStorageSync("address") || [];
      var i = 0;
      var defaultadd = "";
      for (i = 0; i < address.length; i++) {
        if (address[i].priority == 1) {
          defaultadd = address[i].address;
        }
      }
      var data = wx.getStorageSync("data");
      if (data == null || data == "") {
        wx.showToast({
          icon: "none",
          title: "请先登录",
        });
        wx.setStorageSync("allcart", null);
        return;
      } else {
        var userPhone = data.phone;
        this.setData({
          data,
          defaultadd,
        });
        //获取所有购物车内商品
        wx.request({
          url: "http://localhost:8082/rkoShop/shopping/" + userPhone,
          data: {},
          method: "GET",
          dataType: "json",
          responseType: "text",
          success: (res) => {
            var token = wx.getStorageSync("token");
            wx.setStorageSync("allcart", res.data.data);
            var allcart = wx.getStorageSync("allcart");
            //console.log(allcart)
            var detailsList = null;
            if (detailsList == null && allcart == null) {
              return;
            }
            var detailsList = allcart.detailsList;
            this.setData({ allcart });
            //计算总价格
            var allprice = 0;
            let i = 0;
            if (detailsList == null) {
              allprice = 0;
            } else {
              for (i = 0; i < detailsList.length; i++) {
                allprice = allprice + detailsList[i].num * detailsList[i].price;
              }
            }
            var allprice = allprice.toFixed(2);
            this.setData({ allprice });
            var allprice = wx.setStorageSync("allprice", allprice);
            //console.log(allprice)
            console.log(allcart);
          },
        });
      }
    }
    this.onLoad();
    // }
  },
  handleChooseAddress() {
    wx.navigateTo({
      url: "../selectaddress/index",
    });
  },
  //减
  jian(res) {
    var allcart = wx.getStorageSync("allcart");
    var num = res.currentTarget.dataset.num;
    var detailsId = res.currentTarget.dataset.id;
    if (num > 1) {
      num = num - 1;
    } else {
      wx.showToast({
        icon: "none",
        title: "商品数量不能为0",
      });
      return;
    }
    wx.request({
      url: "http://localhost:8082/rkoShop/shopping/" + detailsId,
      data: {
        num: num,
      },
      header: {
        "content-type": "application/json",
      },
      method: "PUT",
      dataType: "json",
      responseType: "text",
      success: (res) => {
        //刷新页面
        this.onShow();
      },
    });
    //console.log(res)
  },
  //加
  jia(res) {
    var allcart = wx.getStorageSync("allcart");
    var num = res.currentTarget.dataset.num;
    var detailsId = res.currentTarget.dataset.id;
    num = num + 1;
    wx.request({
      url: "http://localhost:8082/rkoShop/shopping/" + detailsId,
      data: {
        num: num,
      },
      header: {
        "content-type": "application/json",
      },
      method: "PUT",
      dataType: "json",
      responseType: "text",
      success: (res) => {
        //刷新页面
        this.onShow();
      },
    });
    //console.log(res)
  },
  //删除商品
  remove(res) {
    var detailsId = res.currentTarget.dataset.id;
    wx.request({
      url: "http://localhost:8082/rkoShop/shopping/" + detailsId,
      data: {},
      header: {
        "content-type": "application/json",
      },
      method: "delete",
      dataType: "json",
      responseType: "text",
      success: (res) => {
        //console.log(res)
        wx.showToast({
          title: "已移出购物车",
          icon: "success",
          //防止用户多点击按钮
          mask: true,
        });
        this.onShow();
      },
    });
  },
  //清空购物车
  cleancart(res) {
    var data = wx.getStorageSync("data");
    var userPhone = data.phone;
    wx.request({
      url: "http://localhost:8082/rkoShop/shopping/" + userPhone + "/all",
      data: {},
      header: {
        "content-type": "application/json",
      },
      method: "delete",
      dataType: "json",
      responseType: "text",
      success: (res) => {
        //console.log(res)
        wx.showToast({
          //title: "已清空购物车",
          icon: "success",
          //防止用户多点击按钮
          mask: true,
        });
        this.onShow();
      },
    });
  },
  //结算
  jiesuan(res) {
    //console.log(res)
    var allcart = wx.getStorageSync("allcart");
    var data = wx.getStorageSync("data");
    var userPhone = data.phone;
    var detailsList = null;
    var detailsList = allcart.detailsList;
    var addressId = null;
    var addressId = wx.getStorageSync("addressId");
    //console.log(detailsList)
    //console.log(addressId)
    //console.log(data)
    if (detailsList == null || addressId == null) {
      wx.showToast({
        icon: "none",
        title: "地址与商品不能为空",
      });
      return;
    }
    var balance = data.balance;
    var allprice = wx.getStorageSync("allprice");
    if (allprice <= balance) {
      wx.request({
        url: "http://localhost:8082/rkoShop/orders/" + userPhone + "/shopping",
        data: {
          addressId: addressId,
          detailsList: detailsList,
        },
        header: {
          "content-type": "application/json",
        },
        method: "POST",
        dataType: "json",
        responseType: "text",
        success: (res) => {
          //console.log(res)
          if (!res.data.flag) {
            wx.showToast({
              icon: "none",
              title: "地址与商品不能为空",
            });
            return;
          } else
            wx.showToast({
              title: "购买成功",
              icon: "success",
              //防止用户多点击按钮
              mask: true,
            });
          this.cleancart();
        },
      });
    } else {
      wx.showToast({
        title: "余额不足",
        icon: "none",
        //防止用户多点击按钮
        mask: true,
      });
      return;
    }
  },
});
