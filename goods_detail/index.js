// pages/goods_detail/index.js
Page({
  /**
   * 页面的初始数据
   */
  data: {
    detail: [],
    commodityId: {},
  },
  onShow() {
    
    this.onLoad();
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!options) {
      return;
    }
    const { commodityId } = options;
    // console.log("ccccc"+commodityId)
    this.getGoodsDetail(commodityId);
  },
  //获取商品详情数据
  async getGoodsDetail(commodityId) {
    var that = this;
    wx.request({
      url: "http://localhost:8082/rkoShop/commoditys/layer/" + commodityId,
      method: "GET",
      success(res) {
        // console.log(commodityId)
        wx.setStorageSync("detail", res.data.data);
        // const detail = wx.getStorageSync("detail");
        var detail=res.data.data;
        console.log(detail);
        that.setData({
          detail,
        });
        //console.log(res.data.data)
      },
    });
  },
  //点击加入购物车
  handleCartAdd() {
    //this.onShow()
    var detail = wx.getStorageSync("detail");
    var commodityId = detail.commodityId;
    var data = wx.getStorageSync("data");
    if (data == null) {
      wx.showToast({
        icon: "none",
        title: "请先登录",
      });
      return;
    } else {
      var userPhone = data.phone;
    }
    //console.log(userPhone)
    //console.log(detail.commodityId)
    //获取缓存中的购物车
    let cart = wx.getStorageSync("cart");
    wx.request({
      url: "http://localhost:8082/rkoShop/shopping/" + userPhone,
      data: {
        commodityId: commodityId,
        num: 1,
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
            title: "用户未登录",
          });
          return;
        } else
          wx.showToast({
            title: "添加成功",
            icon: "success",
            //防止用户多点击按钮
            mask: true,
          });
      },
    });
  },
  //立即购买商品
  buy(res) {
    var that = this;
    var data = wx.getStorageSync("data");
    if (data == null || data.length == 0) {
      wx.showToast({
        icon: "none",
        title: "请先登录",
      });
    } else {
      wx.navigateTo({
        url: "../buyadd/index",
      });
    }
  },
});
