package com.kankan.op.util;

import com.xunlei.httptool.util.ParamInvalidError;
import com.xunlei.httptool.util.RtnConfig;
import com.xunlei.httptool.util.RtnConstants;

/**
 * 抛出的提示前端的消息，配置了不会回滚事务
 * 
 * @since 2013-6-24
 * @author hujiachao
 */
@RtnConfig(RtnConstants.PARAM_INVALID)
public class NoRollbackMsg extends ParamInvalidError {

    private static final long serialVersionUID = 2835214224129065263L;

    public NoRollbackMsg() {
        super();
    }

    public NoRollbackMsg(String msg) {
        super(msg);
    }
}
