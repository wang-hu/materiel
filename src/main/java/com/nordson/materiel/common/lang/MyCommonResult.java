package com.nordson.materiel.common.lang;

public class MyCommonResult extends CommonResult {

    /**
     * 返回参数
     */
    private Object backParam;

    public MyCommonResult() {
        //empty constructor
    }

    public MyCommonResult(String message) {
        this(message, null);
    }

    public MyCommonResult(String message, Object backParam) {
        success(message);
        this.backParam = backParam;
    }

    public MyCommonResult(String code, String message, Object backParam) {
        this(message, backParam);
        this.responseCode = code;
    }


    public Object getBackParam() {
        return backParam;
    }

    public void setBackParam(Object backParam) {
        this.backParam = backParam;
    }
}
