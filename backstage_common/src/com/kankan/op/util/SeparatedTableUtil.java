package com.kankan.op.util;

import org.springframework.stereotype.Service;
import com.kankan.op.dao.BaseDao.Table;
import com.xunlei.spring.AfterConfig;
import com.xunlei.spring.Config;

/**
 * 数据库分表规则工具
 * 
 * @since 2013-6-27
 * @author hujiachao
 */
@Service
public class SeparatedTableUtil {

    /** 数据库分表数 */
    @Config
    protected int separatedTableNum = 1;

    @AfterConfig
    public void init() {
        for (Table t : Table.values()) {
            t.num = separatedTableNum;
        }
    }

    public int getSeparatedTableNum() {
        return separatedTableNum;
    }

    /**
     * 返回当前是否是测试环境
     */
    public boolean isTestEnv() {
        return separatedTableNum == 1;
    }
}
