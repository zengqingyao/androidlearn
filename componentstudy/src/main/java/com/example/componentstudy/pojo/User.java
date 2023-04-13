package com.example.componentstudy.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @包名: com.example.componentstudy.pojo
 * @author: zengqy
 * @DATE: 2022/12/8 18:39
 * @描述:
 */
public class User implements Parcelable {
    private String mName;
    private int mAge;

    public User(String name, int age) {
        mName = name;
        this.mAge = age;
    }

    protected User(Parcel in) {
        mName = in.readString();
        mAge = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeInt(mAge);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getAge() {
        return mAge;
    }

    public void setAge(int age) {
        this.mAge = age;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
