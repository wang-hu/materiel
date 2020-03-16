package com.demo.springboot.common.factory;


import com.demo.springboot.common.Constants;
import com.demo.springboot.common.lang.MyCommonResult;
import org.springframework.stereotype.Component;

@Component
public class CommonResultFactory {

    /**默认错误码*/
    private static final String ERROR_CODE = Constants.DEFAULT_ERROR_CODE;

    public MyCommonResult successResult(String message) {
        return new MyCommonResult(message);
    }

    public MyCommonResult successResult(String message, Object backParam) {
        return new MyCommonResult(message, backParam);
    }

    public MyCommonResult failResult(String message) {
        return new MyCommonResult(ERROR_CODE, message, null);
    }

    public MyCommonResult failResult(String message, Object backParam) {
        return new MyCommonResult(ERROR_CODE, message, backParam);
    }

    public MyCommonResult failResult(String code, String message) {
        return new MyCommonResult(code, message, null);
    }

    public MyCommonResult failResult(String code, String message, Object backParam) {
        return new MyCommonResult(code, message, backParam);
    }

}
