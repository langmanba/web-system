package com.web.portal.web.pojo;

import lombok.Data;

@Data
public class Result<T> {

    private String code = "-1";
    private String desc = "desc";
    private T data;

    public Result() {

    }

    public Result(Integer integer, String str) {
        this.code = integer.toString();
        this.desc = str;

    }

    public Result(String v1, String v2) {
        this.code = v1;
        this.desc = v2;
    }
}
