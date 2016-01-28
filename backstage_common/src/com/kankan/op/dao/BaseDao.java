package com.kankan.op.dao;

import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import com.kankan.op.util.SeparatedTableUtil;
import com.xunlei.jdbc.JdbcTemplate;
import com.xunlei.jedis.JedisTemplate;
import com.xunlei.jedis.ShardedJedisTemplate;
import com.xunlei.spring.Config;

/**
 * 基础DAO用于继承
 * 
 * @since 2013-6-9
 * @author hujiachao
 */
public abstract class BaseDao {

    /** 新付费频道图片地址 **/
    @Config(resetable = true)
    protected String imgUrlRoot = "http://img.vip.kankan.kanimg.com/";

    /**
     * 管理后台数据源
     */
    @Resource(name = "jdbcTemplateBackstage")
    protected JdbcTemplate jdbcTemplateBackstage;
    


    @Autowired
    protected SeparatedTableUtil separatedTableUtil; // 为了能在Spring优先加载赋值num

    public enum Table {
        balancepayrecord("balancepayrecord"),
        gatewaypayrecord("gatewaypayrecord"),
        scoregrowthrecord("scoregrowthrecord"),
        userinfo("userinfo"),
        userorder("userorder"),
        userproduct("userproduct"),
        userticket("userticket"),
        userstatus("userstatus"),
        vippayrecord("vippayrecord"),
        userinfodetail("userinfodetail"),
        mobilemessagelog("mobilemessagelog"),
        usermessage("usermessage"),
        thundermessage("thundermessage"),
        act_user_result("act_user_result"),
        user_marketing_info("user_marketing_info"),
        dynamics("dynamics"),
        fandom_members("fandom_members"),
        fans_joined_fandom("fans_joined_fandom"),
        topic("topic"),
        user_topic("user_topic"),
        topic_comment("topic_comment"),
        user_topic_comment("user_topic_comment"),
        dynamic_comments("dynamic_comments"),
        user_dynamic_comments("user_dynamic_comments"),
        star_fan_relation("star_fan_relation"),
        fan_star_relation("fan_star_relation"),
        user_topic_likes("user_topic_likes"),
        topic_user_likes("topic_user_likes"),
        user_dynamic_likes("user_dynamic_likes"),
        dynamic_user_likes("dynamic_user_likes"),
        message_notify("message_notify"),
        handsup("handsup");

        /** 表名前缀 */
        public String prefix;
        /** 表个数 */
        public int num;

        private Table(String prefix) {
            this.prefix = prefix;
            this.num = -1; // 默认赋值-1，以便没有赋值时能发现报错;
        }
    }

    public String getTable(Table table, long key) {
        return "`" + table.prefix + (key % table.num) + "`";
    }

    public int getTableIndex(Table table, long key) {
        return (int) (key % table.num);
    }

    public static class Paging {

        public int pageNo;
        public int pagesize;
        public int totalCount;
        public int pageCount;
        public int startIndex;

        private Paging(int pageNo, int pagesize, int totalCount) {
            this.totalCount = totalCount;
            this.pagesize = pagesize <= 0 ? 20 : pagesize;
            this.pageCount = totalCount / pagesize + (totalCount % pagesize > 0 ? 1 : 0);
            this.pageNo = pageNo > pageCount ? pageCount : pageNo;
            this.pageNo = this.pageNo <= 0 ? 1 : this.pageNo;
            this.startIndex = (pageNo - 1) * pagesize;
        }
    }

    /**
     * 获取分页信息，用于分页查询，内部做了容错处理
     */
    public static Paging getPaging(int pageNo, int pagesize, int totalCount) {
        return new Paging(pageNo, pagesize, totalCount);
    }

    /**
     * 专用于分页返回的数据结构
     */
    public static class PagedData<T> {

        /** 当前页码 */
        public int pageNo;
        /** 总页数 */
        public int pageCount;
        /** 总记录数 */
        public int totalCount;
        /** 本页的数据 */
        public List<T> page;

        public PagedData(Paging p, List<T> page) {
            this.pageNo = p.pageNo;
            this.pageCount = p.pageCount;
            this.totalCount = p.totalCount;
            this.page = page;
        }

        public <U> PagedData(PagedData<U> other) {
            this.pageNo = other.pageNo;
            this.pageCount = other.pageCount;
            this.totalCount = other.totalCount;
            this.page = Collections.emptyList();
        }
    }
}
