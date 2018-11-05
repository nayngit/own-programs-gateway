package com.own.base.vo;

public class Result<T> {
	
	private boolean success;
	
	private String errCode;
	
	private String msg;
	
	private T body;

	private String noneField;
	
	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getNoneField() {
		return noneField;
	}

	public void setNoneField(String noneField) {
		this.noneField = noneField;
	}
	
	
}
