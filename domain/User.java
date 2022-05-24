package com.abc.rkodemo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends ValueObjet {

    private String phone;
    private String userName;
    private String password;
    private double balance = -99;
    private String userImage;
    private String sex;
    private int age = -99;
    private String hobby;
    private List<Commodity> commodityList;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        if (sex == null)
            this.sex = "未知";
        else if (sex.equals("男") || sex.equals("女"))
            this.sex = sex;
        else
            this.sex = "未知";
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age >= 0)
            this.age = age;
        else
            this.age = 0;
    }

}
