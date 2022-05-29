// pages/category/index.js
Page({
  /**
   * 页面的初始数据
   */
  data: {
    type: [
      { id: 1, name: "零食" },
      { id: 2, name: "书籍" },
      { id: 3, name: "其他" },
    ],
    curIndex: 0,
    productList: [],
    productAll: [],
  },

  getProductAll() {
    let that = this;
    var data = wx.getStorageSync("data");
    if (data) {
      var phone = wx.getStorageSync("data").data;
    } else {
      var phone = "";
    }
    wx.request({
      url: "http://localhost:8082/rkoShop/commoditys/" + phone,
      header: {
        "content-type": "application/json",
      },
      method: "GET",
      dataType: "json",
      responseType: "text",
      success: (res) => {
        let list = res.data.data.filter((item) => {
          return item.type == 1;
        });
        this.setData({
          productAll: res.data.data,
          productList: list,
        });
        var infoList = that.data.productList;
        console.log(list);
      },
    });
  },

  chooseType(e) {
    let id = e.currentTarget.id;
    let list = [];
    let p = this.data.productAll;
    let len = p.length;
    for (let i = 0; i < len; i++) {
      if (p[i].type == id) {
        list.push(p[i]);
      }
    }
    this.setData({
      productList: list,
      curIndex: id - 1,
    });
  },
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.onLoad();
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getProductAll();
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {},

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {},

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {},

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {},

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {},

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {},
});
