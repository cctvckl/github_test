package com.kankan.op.util;

public enum ErrorCodeEnum {
	SUCCESS(0, "成功"),
	NOT_LOGIN(-1, "用户未登陆"),
	DB_QUERY_NO_RECORDS(-2, "数据库无记录"),
	DB_QUERY_FAILED(-3, "数据库查询时出错"),
	NULLPOINTER(-4, "空指针"),
	//以下错误码来自于RtnConstant.java
	PARAM_ILLEGAL(9, "参数获取失败(如格式非法,无此参数等,对应 IllegalParameterError)"),
	VCODE_INVALID(10, "验证码无效"),
	SESSIONID_INVALID(11, "登录态验证失败"),
	PARAM_INVALID(13, "参数业务验证无效"),
	OPERATION_FORBIDDEN(14, "操作被禁止，如请求过于频繁等"),
	INTERNAL_SERVER_ERROR(500, "默认系统内部未知错误");
	
    //错误码
    private int code;
    //错误码描述
    private String desc;

    private ErrorCodeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    public int code(){
    	return getCode();
    }
	private int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
