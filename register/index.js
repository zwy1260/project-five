// pages/register/index.js
Page({


  data: {
    users: {
      phone:"",
      password:"",
    },
  },
  //获取用户名
  getName(event) {
    //console.log('获取输入用户名', event.detail.value)
    this.setData({
      users: {
        phone: event.detail.value,
        password: this.data.users.password
      }
    })
    //console.log(this.data)
  },
  // 获取密码
  getMiMa(event) {
    //console.log('获取输入密码', event.detail.value)
    this.setData({
      users: {
        phone: this.data.users.phone,
        password: event.detail.value
      }
    })
    //console.log(this.data)
  },
/**
 * 手机号校验
 1--以1为开头；
 2--第二位可为3,4,5,7,8,中的任意一位；
 3--最后以0-9的9个整数结尾。
 */
checkTelephone(phone) {
  var reg=/^[1][3,4,5,7,8][0-9]{9}$/;
  if (!reg.test(phone)) {
      return false;
  } else {
      return true;
  }
},
  //注册
  zhuce() {
    let phone = this.data.users.phone
    let password = this.data.users.password
    console.log("点击了注册")
    console.log("phone", phone)
    console.log("password", password)
    //校验用户名
    if (!this.checkTelephone(phone)) {
      wx.showToast({
        icon: 'none',
        title: '请输入正确的手机号',
      })
      return
    }
    // if (phone.length > 11) {
    //   wx.showToast({
    //     icon: 'none',
    //     title: '用户名最多11位',
    //   })
    //   return
    // }
    //校验密码
    if (password.length < 4) {
      wx.showToast({
        icon: 'none',
        title: '密码至少4位',
      })
      return
    }
    //注册功能的实现
    wx.request({
      url: 'http://localhost:8082/rkoShop/users',
      data: {
          phone: this.data.users.phone,
          password: this.data.users.password
      },
      header: {'content-type':'application/json'},
      method: 'POST',
      dataType: 'json',
      responseType: 'text',
      success: (res) => {
        console.log('注册成功', res)
        wx.showToast({
          title: '注册成功',
        })
        wx.navigateTo({
          url: '../login/index',
        })
      },
      fail: (res) => {
        console.log('注册失败', res)
      },
      complete: () => {}
    });
  }
})