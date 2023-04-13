package com.example.constraintlayouttest;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @包名: com.example.constraintlayouttest
 * @author: zengqy
 * @DATE: 2022/12/6 23:09
 * @描述:
 */
public class SerializableTest implements Serializable {

    // 有了UID后，类添加新的值也能正常读出
    private static final long serialVersionUID = -6230553994448493166L;

    // gson把对象转为json的时候的value的值，就不是默认的mName了
    @SerializedName("TestName")
    private String mName;
    private int mAge;
    private Score mScore;

    public SerializableTest(String name, int age, Score score) {
        mName = name;
        mAge = age;
        mScore = score;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getAge() {
        return mAge;
    }

    public void setAge(int age) {
        mAge = age;
    }

    public Score getScore() {
        return mScore;
    }

    public void setScore(Score score) {
        mScore = score;
    }


    @Override
    public String toString() {
        return "SerializableTest{" +
                "mName='" + mName + '\'' +
                ", mAge=" + mAge +
                ", mScore=" + mScore +
                '}';
    }
}

class Score implements Serializable
{

    private static final long serialVersionUID = -6131098475279514573L;

    private int math;
    private int english;

    public Score(int math, int english) {
        this.math = math;
        this.english = english;
    }

    public int getEnglish() {
        return english;
    }

    public void setEnglish(int english) {
        this.english = english;
    }

    @Override
    public String toString() {
        return "Score{" +
                "math=" + math +
                ", english=" + english +
                '}';
    }
}