// pages/login/index.js
const app=getApp();
Page({
  data: {
    users: {
      phone:"",
      password:"",
    },
  },
  //获取用户名
  getName(event) {
    this.setData({
      users: {
        phone: event.detail.value,
        password: this.data.users.password
      }
    })
  },
  // 获取密码
  getMiMa(event) {

    this.setData({
      users: {
        phone: this.data.users.phone,
        password: event.detail.value
      }
    })
  },
  checkTelephone(phone) {
    var reg=/^[1][3,4,5,7,8][0-9]{9}$/;
    if (!reg.test(phone)) {
        return false;
    } else {
        return true;
    }
  },
  denglu(e){
    let phone = this.data.users.phone
    let password = this.data.users.password 
    if (!this.checkTelephone(phone)) {
      wx.showToast({
        icon: 'none',
        title: '请输入正确的手机号',
      })
      return
    }
    //校验密码
    if (password.length < 4) {
      wx.showToast({
        icon: 'none',
        title: '密码至少4位',
      })
      return
    }
          wx.request({
            url: 'http://localhost:8082/rkoShop/users/login',
            data: {
                phone: this.data.users.phone,
                password: this.data.users.password,
            },
            header: {'content-type':'application/json'},
            method: 'POST',
            dataType: 'json',
            responseType: 'text',
            success: (res) => {
              //var token=wx.getStorageSync('token')
              wx.setStorageSync('token',res.data.data)
             
              if(res.data.flag){
              wx.showToast({
                title:res.data.msg,
              })
              wx.switchTab({
                url: '../user/index',
              })
              //console.log('res='+res.data)
              //app.globalData.token = res.data.data;
            }else{
              wx.showToast({
                title:res.data.msg,
                icon:'none'
              })
            }
        },
      })
},
})