package com.gaoan.forever.constant;

public enum MessageEnum {

    LOGIN_MSG(MessageInfoConstant.LOGIN_SUCCESS, MessageInfoConstant.LOGIN_FAIL), 
    REGISTER_MSG(MessageInfoConstant.REGISTER_SUCCESS, MessageInfoConstant.REGISTER_FAIL),
    SEARCH_MSG(MessageInfoConstant.SEARCH_SUCCESS, MessageInfoConstant.SEARCH_FAIL),
	INSERT_MSG(MessageInfoConstant.INSERT_SUCCESS, MessageInfoConstant.INSERT_FAIL),
	UPDATE_MSG(MessageInfoConstant.UPDATE_SUCCESS, MessageInfoConstant.UPDATE_FAIL),
	DELETE_MSG(MessageInfoConstant.DELETE_SUCCESS, MessageInfoConstant.DELETE_FAIL),
	RESET_PASSWORD_MSG(MessageInfoConstant.RESET_PASSWORD_SUCCESS, MessageInfoConstant.RESET_PASSWORD_FAIL),
	SET_NEW_PASSWORD_MSG(MessageInfoConstant.SET_NEW_PASSWORD_SUCCESS, MessageInfoConstant.SET_NEW_PASSWORD_FAIL),
	EXPORT_MSG(MessageInfoConstant.EXPORT_SUCCESS, MessageInfoConstant.EXPORT_FAIL),
	LOGIN_OUT_MSG(MessageInfoConstant.LOGIN_OUT_SUCCESS, MessageInfoConstant.LOGIN_OUT_FAIL);

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
