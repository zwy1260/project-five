//Page Object
Page({
  data: {
    //轮播图数组
    indicatorDots: true,
    interval: 3000,
    duration: 1000,
    infoList: [],
    loadCount: 0,
    showBackTop: false,
    top: 0,
    images: [
      "../../icons/lunbo1.jpg",
      "../../icons/lunbo2.jpg",
      "../../icons/lunbo3.jpg",
    ],
  },
  getCommodityListByPhone() {
    let that = this;
    var phone = wx.getStorageSync("data").phone;
    wx.request({
      url: "http://localhost:8082/rkoShop/commoditys/" + phone,
      header: {
        "content-type": "application/json",
      },
      method: "GET",
      dataType: "json",
      responseType: "text",
      success: (res) => {
        that.setData({
          infoList: res.data.data,
        });
        var infoList = that.data.infoList;
        console.log(infoList);
      },
    });
  },
  getCommodityList() {
    let that = this;
    wx.request({
      url: "http://localhost:8082/rkoShop/commoditys",
      header: {
        "content-type": "application/json",
      },
      method: "GET",
      dataType: "json",
      responseType: "text",
      success: (res) => {
        that.setData({
          infoList: res.data.data,
        });
        var infoList = that.data.infoList;
        console.log(infoList);
      },
    });
  },
  //options(Object)
  onLoad: function (options) {
    var token = wx.getStorageSync("token");
    if (!token) {
    this.getCommodityList();
    }
  },
  onShow() {
    var token = wx.getStorageSync("token");
    if (token) {
      this.getCommodityListByPhone();
    }
    this.onLoad()
  },
  backTop() {
    wx.pageScrollTo({
      scrollTop: 0,
      duration: 150,
    });
  },
  /**
   * 监听页面滚动
   */

  onPageScroll(e) {
    let scrollTop = e.scrollTop;
    let flag = scrollTop > this.data.top;
    this.setData({
      showBackTop: flag,
    });
  },
});
