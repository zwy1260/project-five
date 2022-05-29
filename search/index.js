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
  },
  //执行点击事件
  formSubmit: function (e) {
    //声明当天执行的
    var that = this;
    //获取表单所有name=keyword的值
    var title = e.detail.value.keyword;
    var token = wx.getStorageSync("token");
    var phone;
    if (token) {
      phone = wx.getStorageSync("data").phone;
    } else {
      phone = 0;
    }
    if(title.replace(/(^\s*)|(\s*$)/g, "") == ""){
      title=null
    }
    //显示搜索中的提示
    wx.showLoading({
      title: "搜索中",
      icon: "loading",
    });
    wx.request({
      url: "http://localhost:8082/rkoShop/commoditys/select/" + title,
      data: {
        phone: phone,
      },
      header: {
        "content-type": "application/json",
      },
      method: "POST",
      dataType: "json",
      responseType: "text",
      success: (res) => {
        that.setData({
          infoList: res.data.data,
        });
        wx.hideLoading();
        // console.log(that.data.infoList.length);
        var infoList = that.data.infoList;
        if (that.data.infoList.length==0) {
          setTimeout(function () {
            wx.showToast({
              title: "没有此商品",
              icon: "none",
            });
          }, 1000);
        } else {
          setTimeout(function () {
          wx.showToast({
            title: that.data.msg,
            icon: "none",
          });
        },1000)
        }
        console.log(infoList);
      },
    });
  },
  onLoad: function (options) {},
  onShow() {},
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
