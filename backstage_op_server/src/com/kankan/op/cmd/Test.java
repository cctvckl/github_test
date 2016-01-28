package com.kankan.op.cmd;

import org.springframework.stereotype.Service;
import com.xunlei.httptool.login.DefaultCmd;
import com.xunlei.httptool.util.JsonObjectUtil;
import com.xunlei.netty.httpserver.cmd.annotation.Cmd;
import com.xunlei.netty.httpserver.cmd.annotation.CmdAuthor;
import com.xunlei.netty.httpserver.cmd.annotation.CmdContentType;
import com.xunlei.netty.httpserver.cmd.annotation.CmdDescr;
import com.xunlei.netty.httpserver.cmd.annotation.CmdParam;
import com.xunlei.netty.httpserver.cmd.annotation.CmdParams;
import com.xunlei.netty.httpserver.cmd.annotation.CmdReturn;
import com.xunlei.netty.httpserver.cmd.annotation.CmdSession;
import com.xunlei.netty.httpserver.component.XLHttpRequest;
import com.xunlei.netty.httpserver.component.XLHttpResponse;
import com.xunlei.netty.httpserver.component.XLHttpResponse.ContentType;
import com.xunlei.netty.httpserver.util.CmdSessionType;
import com.xunlei.netty.httpserver.util.IPAuthenticator;

@Service
@CmdDescr("管理后台专用接口")
public class Test extends DefaultCmd{

    @Cmd("删除动态,管理后台删除或屏蔽明星动态后需要调用该接口")
    @CmdSession(type = CmdSessionType.COMPELLED)
    @CmdAuthor({
        "yaowenyu"
    })
    @CmdParams({
        @CmdParam(name = "dynamicId", desc = "动态id"),
        @CmdParam(name = "userId", desc = "明星的userid"),
        @CmdParam(name = "type", desc = "2-屏蔽明星动态 3-删除明星动态"),
    })
    @CmdReturn({
        "{",
        "'rtn':0,                               // 0 清理缓存成功",
        "                                          -1 动态不是删除或屏蔽状态，不能清理缓存",
        "                                          38 传入的type值不合法",
        "}",
    })
    @CmdContentType({
        ContentType.json
    })
    public Object deleteStarDynamic(XLHttpRequest request, XLHttpResponse response) {
        IPAuthenticator.auth(request.getRemoteIP());
        long userId = request.getParameterLong("userId");
        long dynamicId = request.getParameterLong("dynamicId");
        int type = request.getParameterInteger("type");

        return JsonObjectUtil.getOnlyRtnJson(0);
    }
}
