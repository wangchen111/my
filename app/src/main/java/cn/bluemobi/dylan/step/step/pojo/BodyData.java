package cn.bluemobi.dylan.step.step.pojo;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.enums.AssignType;

/**
 * Created by wangchen on 2017/3/2.
 */

public class BodyData {

    // 指定自增，每个对象需要有一个主键
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int id;
    @Column("stature")
    private String stature;
    @Column("height")
    private String height;
    @Column("birthday")
    private String birthday;
    @Column("sex")
    private String sex;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getStature() {
        return stature;
    }
    public void setStature(String stature) {
        this.stature = stature;
    }
    public String getHeight() {
        return height;
    }
    public void setHeight(String height) {
        this.height = height;
    }
    public String getBirthday() {
        return birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    @Override
    public String toString() {
        return "BodyData{" +
                "id=" + id +
                ", birthday='" + birthday + '\'' +
                ", height='" + height + '\'' +
                ",stature='" + stature + '\'' +
                '}';
    }
}
