package com.xy.elasticsearch.core.model;


import com.xy.elasticsearch.core.annotation.FieldType;
import com.xy.elasticsearch.core.annotation.document.Field;
import com.xy.elasticsearch.core.annotation.document.Id;
import com.xy.elasticsearch.core.annotation.document.Join;
import com.xy.elasticsearch.core.annotation.document.JoinType;
import com.xy.elasticsearch.core.annotation.index.Index;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author zoubo
 * @version 1.0.0
 * @date 2019/12/29 21:37
 */
@Index(name = "demo", type = "demo", created = true)
//@Setting(path = "demoSetting.json")
public class Demo implements Serializable {

    @Id(routing = "#city#id#name")
    private String id;

    @Field(type = FieldType.TEXT, analyzers = {"ik_max_word", "english"})
    private String name;

    private String sex;

    private Integer age;

    private String address;

    private String code;

    @Field(type = FieldType.JOIN, join = {
            @Join(type = "grandfather", childType = {"father", "uncle"}, level = 0),
            @Join(type = "father", childType = "sun", level = 1),
            @Join(type = "uncle", childType = "cousin", level = 2)})
    @JoinType("grandfather")
    private Map<String, Object> myJoinField;

    @Field(type = FieldType.GEO_POINT)
    private List<Double> location;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Map<String, Object> getMyJoinField() {
        return myJoinField;
    }

    public void setMyJoinField(Map<String, Object> myJoinField) {
        this.myJoinField = myJoinField;
    }

    public List<Double> getLocation() {
        return location;
    }

    public void setLocation(List<Double> location) {
        this.location = location;
    }
}
