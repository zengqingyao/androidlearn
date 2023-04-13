package com.zengqy.contentprovider.pojo;

/**
 * @包名: com.zengqy.contentprovider.pojo
 * @USER: zengqy
 * @DATE: 2022/4/8 13:26
 * @描述:
 */
public class User {
    private int _id;

    private String userName;

    private String userPassowrd;

    private int age;

    public User() {
        this.age = -1;
        this.userName = null;
        this.userPassowrd = null;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassowrd() {
        return userPassowrd;
    }

    public void setUserPassowrd(String userPassowrd) {
        this.userPassowrd = userPassowrd;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    @Override
    public String toString() {
        return "User{" +
                "_id=" + _id +
                ", userName='" + userName + '\'' +
                ", userPassowrd='" + userPassowrd + '\'' +
                ", age=" + age +
                '}';
    }
}
