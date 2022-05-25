// pages/user/index.js
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    loginOK: false,
    data: {}
  },
  onShow() {
    var token = wx.getStorageSync('token')
    var data = null
    wx.request({
      url: 'http://localhost:8082/rkoShop/users/data?token=',
      data: {
          token: token,
      },
      header: {
          'content-type': 'application/json'
      },
      method: 'GET',
      dataType: 'json',
      responseType: 'text',
      success: (res) => {
          wx.setStorageSync('data', res.data.data)
          var data = wx.getStorageSync('data')
          this.setData({data})
      }
  })
    if (token) {
      this.setData({
        loginOK: true,
      })
    } else {
      this.setData({
        loginOK: false
      })
    }
  },
  tuichu() {
    wx.setStorageSync('token', null)
    var token = wx.getStorageSync('token')
    wx.clearStorageSync()
    wx.clearStorage()
    app.onShow()
    if (token) {
      this.setData({
        loginOK: true,
      })
    } else {
      this.setData({
        loginOK: false
      })
    }
  },
  zhuce() {
    wx.navigateTo({
      url: '../register/index',
    })
  },
  denglu() {
    wx.navigateTo({
      url: '../login/index',
    })
  },
  onLoad() {
    
  },
})