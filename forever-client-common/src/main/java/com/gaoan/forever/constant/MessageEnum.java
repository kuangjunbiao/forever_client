package com.gaoan.forever.constant;

public enum MessageEnum {

    LOGIN_MSG(MessageInfoConstant.LOGIN_SUCCESS, MessageInfoConstant.LOGIN_FAIL), 
    REGISTER_MSG(MessageInfoConstant.REGISTER_SUCCESS, MessageInfoConstant.REGISTER_FAIL),
    SEARCH_MSG(MessageInfoConstant.SEARCH_SUCCESS, MessageInfoConstant.SEARCH_FAIL);

    private String successMsg;
    private String failMsg;

    MessageEnum(String successMsg, String failMsg) {
	this.successMsg = successMsg;
	this.failMsg = failMsg;
    }

    public String getSuccessMsg() {
	return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
	this.successMsg = successMsg;
    }

    public String getFailMsg() {
	return failMsg;
    }

    public void setFailMsg(String failMsg) {
	this.failMsg = failMsg;
    }

}
