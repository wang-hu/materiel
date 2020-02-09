package com.nordson.materiel.common.lang;

import java.io.Serializable;
import java.util.Map;

public class CommonResult implements Serializable {


    private static final String SUCCESS_CODE = "0000";

    /** 结果码,默认成功 */
    protected String responseCode = SUCCESS_CODE;

    /** 结果信息 */
    protected String responseMsg;

    public CommonResult() {
        //Empty constructor
    }

    public CommonResult(Map<String, Object> map) {
        this.responseCode = (String) map.get("responseCode");
        this.responseMsg = (String) map.get("responseMsg");
    }

    /**
     * @return the responseCode
     */
    public String getResponseCode() {
        return responseCode;
    }

    /**
     * @param responseCode the responseCode to set
     */
    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    /**
     * @return the responseMsg
     */
    public String getResponseMsg() {
        return responseMsg;
    }

    /**
     * @param responseMsg the responseMsg to set
     */
    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    /** 成功 */
    public void success(String msg) {
        this.responseCode = SUCCESS_CODE;
        this.responseMsg = msg;
    }

    /** 失败 */
    public void fail(String code, String msg) {
        this.responseCode = code;
        this.responseMsg = msg;
    }

    /** 判断是否成功 */
    public boolean isSuccess() {
        return SUCCESS_CODE.equals(this.responseCode);
    }

    /** 复制执行结果 */
    public void copy(CommonResult result) {
        this.responseCode = result.getResponseCode();
        this.responseMsg = result.getResponseMsg();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder sbd = new StringBuilder();
        sbd.append("CommonResult [responseCode=").append(this.responseCode)
           .append(", responseMsg=").append(this.responseMsg).append("]");
        return sbd.toString();
    }
}
