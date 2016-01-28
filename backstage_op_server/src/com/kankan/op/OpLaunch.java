package com.kankan.op;

import java.io.IOException;
import org.springframework.stereotype.Service;
import com.xunlei.netty.httpserver.Bootstrap;

@Service
public class OpLaunch {

    public static void main(String[] args) throws IOException {
        Bootstrap.main(args, "classpath:opApplicationContext.xml");
    }
}
