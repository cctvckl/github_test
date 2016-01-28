package com.kankan.op.util;

public class StarConstant {

    /** -1 用户不存在 */
    public static final int USER_NOT_EXIST = -1;
    /** 0 未知 */
    public static final int AUDIT_STATUS_UNKNOW = 0;
    /** 1 明星上传 */
    public static final byte STAR_UPLOAD = 1;
    /** 2 粉丝上传 */
    public static final byte FANS_UPLOAD = 2;
    /** 3 其他各种类型的视频上传 */
    public static final byte OTHER_UPLOAD = 3;
    /** -1 视频上传失败 */
    public static final byte VIDEO_UPLOAD_FAIL = -1;
    /** 0 视频上传中 */
    public static final byte VIDEO_UPLOAD_UPLOADING = 0;
    /** 1 视频上传成功 */
    public static final byte VIDEO_UPLOAD_SUCCESS = 1;
    /** 2 视频转码成功 */
    public static final byte VIDEO_ENCODE_SUCCESS = 2;
    /** 1 需要剪辑预告片 */
    public static final byte VIDEO_NEED_PRE = 1;
    /** 2 剪辑预告片成功 */
    public static final byte VIDEO_ADDPRE_SUCCESS = 2;
    /** 1 视频码率 320p */
    public static final byte VIDEO_BYTETYPE_320P = 1;
    /** 2 视频码率 480p */
    public static final byte VIDEO_BYTETYPE_480P = 2;
    /** 3 视频码率 720p */
    public static final byte VIDEO_BYTETYPE_720P = 3;
    /** 4 视频码率 1080p */
    public static final byte VIDEO_BYTETYPE_1080P = 4;
    /** 0 发表动态或话题类型 -纯文字 */
    public static final byte RESOURCE_TYPE_WORD = 0;
    /** 1 发表动态或话题类型 -图片 */
    public static final byte RESOURCE_TYPE_IMG = 1;
    /** 2 发表动态或话题类型 -视频 */
    public static final byte RESOURCE_TYPE_VIDEO = 2;
    /** 4 发表动态或话题类型 -新闻 */
    public static final byte RESOURCE_TYPE_NEWS = 4;
    /** 0 动态状态--发布中 */
    public static final byte DYNAMIC_STATUS_PUBLISHING = 0;
    /** 1 动态状态--发布成功 */
    public static final byte DYNAMIC_STATUS_PUBLISHED = 1;
    /** 2 动态状态--屏蔽 */
    public static final byte DYNAMIC_STATUS_SHIELD = 2;
    /** 3 动态状态--删除 */
    public static final byte DYNAMIC_STATUS_DELETE = 3;

    /** 0 话题状态--发布中 */
    public static final byte TOPIC_STATUS_PUBLISHING = 0;
    /** 1 话题状态--发布成功 */
    public static final byte TOPIC_STATUS_PUBLISHED = 1;
    /** 2 话题状态--已屏蔽 */
    public static final byte TOPIC_STATUS_CLOSE = 2;
    /** 3 话题状态--已删除 */
    public static final byte TOPIC_STATUS_DELETE = 3;

    /** 0 评论状态--正常 */
    public static final byte COMMENT_STATUS_PUBLISHED = 1;
    /** 1 评论状态--屏蔽 */
    public static final byte COMMENT_STATUS_CLOSE = 2;
    /** 2 评论状态--删除 */
    public static final byte COMMENT_STATUS_DELETE = 3;

    /** 1 举报话题 */
    public static final byte REOPRT_TOPIC = 1;
    /** 2 举报评论 */
    public static final byte REPORT_COMMENT = 2;

    /** 11 ugc视频转码格式 mp4 */
    public static final int VIDEO_MP4 = 11;
    /** 13 ugc视频转码格式 flv */
    public static final int VIDEO_FLV = 13;

    /**
     ** 校验-----------------------------------------------------------------------------------------------------------
     */
    /** 25 提交的E-Mail地址不合法 */
    public static final int EMAIL_INCORRECT = 25;// TODO:合并到34
    /** 26 签名校验失败 */
    public static final int SIGN_INVALID = 26;// 为了兼容，所以返回码数字不能动
    /** 33 提交参数个数不匹配 */
    public static final int PARAMETER_NUMBER_NOT_MATCH = 33;
    /** 34 提交参数格式不合法 */
    public static final int PARMAETER_FORMAT_INCORRECT = 34;
    /** 37 用户提交的分数不合法 */
    public static final int INCORRECT_SCORE = 37;// TODO:合并到34
    /** 38 用户提交的参数值不合法 */
    public static final int PARMAETER_VALUE_INCORRECT = 38;

    /** 10 白（基本没有问题） */
    public static final int AUDIT_STATUS_WHITE = 10;
    /** 粉丝圈开放 */
    public static final int FORUM_OPEN = 100;
    /** 101 版分（版权有问题,但进行分流） */
    public static final int AUDIT_STATUS_COPYRIGHT_DISTRIBUTE = 101;
    /** 150 脏（进脏话库） */
    public static final int AUDIT_STATUS_DIRTY = 150;
    /** 200 黄（黄色资源）-非图片 */
    public static final int AUDIT_STATUS_PORN = 200;
    /** 210 黄（黄色资源）-图片 */
    public static final int AUDIT_STATUS_PORN_IMG = 210;
    /** 300 黄反 - 非图片 */
    public static final int AUDIT_STATUS_PORNREACTION = 300;
    /** 310 黄反 - 图片 */
    public static final int AUDIT_STATUS_PORNREACTION_IMG = 310;
    /** 400 反（反动资源） */
    public static final int AUDIT_STATUS_REACTION = 400;

    /**
     * 消息主题-明星-浏览
     */
    public static final String MESSAGE_TOPIC_STAR_VIEW = "star_dynamic_view";

    /**
     * 消息主题-明星-点赞
     */
    public static final String MESSAGE_TOPIC_STAR_LIKE = "star_dynamic_like";

    /**
     * 消息主题-明星-评论
     */
    public static final String MESSAGE_TOPIC_STAR_COMMENT = "star_dynamic_comment";
    /**
     * 插入首页动态主题-明星-动态
     */
    public static final String MESSAGE_TOPIC_STAR_HOME_DYNAMICS = "star_home_dynamic";

    /**
     * 消息主题-明星-关注
     */
    public static final String MESSAGE_TOPIC_FANS_COUNT = "fans_count";
    /**
     * 消息主题 用户-关注
     */
    public static final String MESSAGE_TOPIC_STAR_COUNT = "star_count";

    /**
     * 消息主题-粉丝圈-浏览
     */
    public static final String MESSAGE_TOPIC_FANDOM_VIEW = "fandom_topic_view";

    /**
     * 消息主题-粉丝圈-点赞
     */
    public static final String MESSAGE_TOPIC_FANDOM_LIKE = "fandom_topic_like";

    /**
     * 消息主题-粉丝圈-评论
     */
    public static final String MESSAGE_TOPIC_FANDOM_COMMENT = "fandom_topic_comment";

    /**
     * 消息主题-粉丝圈-加入
     */
    public static final String MESSAGE_TOPIC_FANDOM_JOIN = "fandom_join";

    /**
     * 消息主题-消息推送
     */
    public static final String MESSAGE_JPUSH_ALERT = "message_jpush_alert";

    /**
     * 消息主题-关注明星的同时加入粉丝圈
     */
    public static final String MESSAGE_TOPIC_STAR_FOLLOW_FANDOM_JOIN = "star_follow_fandom_join";

    /**
     * 消息通知状态-未读
     */
    public static final int MESSAGE_NOTIFY_STATUS_1 = 1;

    /**
     * 消息通知状态-已读
     */
    public static final int MESSAGE_NOTIFY_STATUS_2 = 2;

    /**
     * 消息通知状态-已删除
     */
    public static final int MESSAGE_NOTIFY_STATUS_3 = 3;

    /**
     * 消息通知类型-新的粉丝（明星）
     */
    public static final int MESSAGE_NOTIFY_TYPE_1 = 1;

    /**
     * 消息通知类型-新的粉丝（粉丝圈）
     */
    public static final int MESSAGE_NOTIFY_TYPE_2 = 2;

    /**
     * 消息通知类型-赞（动态）
     */
    public static final int MESSAGE_NOTIFY_TYPE_3 = 3;

    /**
     * 消息通知类型-赞（话题）
     */
    public static final int MESSAGE_NOTIFY_TYPE_4 = 4;

    /**
     * 消息通知类型-评论（动态）
     */
    public static final int MESSAGE_NOTIFY_TYPE_5 = 5;

    /**
     * 消息通知类型-评论（话题）
     */
    public static final int MESSAGE_NOTIFY_TYPE_6 = 6;

    /**
     * 消息通知类型-系统消息
     */
    public static final int MESSAGE_NOTIFY_TYPE_7 = 7;
    /**
     * 删除话题提示语
     */
    public static final String DEL_FANS_TOPIC_MSG = "您发表的话题不符合明星空间的相关要求，已被删除";
    /**
     * 删除评论提示语
     */
    public static final String DEL_FANS_COMMENT_MSG = "您发表的评论不符合明星空间的相关要求，已被删除";
    /**
     * 明星空间小助手userId
     */
    public static final long SYSTEM_ASSITANT_USERID = 0L;

    /**
     * 动态
     */
    public static final int DYNAMIC_TYPE_ALL = 0;
    /**
     * 动态 分类
     */
    public static final int DYNAMIC_TYPE_RESOURCETYPE = 1;

    /**
     * 动态 来源
     */
    public static final int DYNAMIC_TYPE_FROM = 2;
    /**
     * 动态 关注页面
     */
    public static final int DYNAMIC_TYPE_FOCUS = 3;

    /**
     * 数据字典类别 from
     */
    public static final String STAR_DICT_INFO_FROM = "FROM";
    /**
     * 评论数
     */
    public static final int COMMENTNUM = 1;
    /**
     * 阅读数
     */
    public static final int VIEWSNUM = 2;
    /**
     * 点赞数
     */
    public static final int LIKESNUM = 3;
    /**
     * 数据字典类别 from
     */
    public static final String STAR_DICT_INFO = "star_dict_info_";

    /**
     * 粉丝圈话题评论加载上限
     */
    public static final int FANS_TOPIC_COMMENT_MAX_CACHE = 100;

    /**
     * 粉丝圈话题加载上限
     */
    public static final int FANS_TOPIC_MAX_CACHE = 100;

    /**
     * 当某个话题评论数达到50时推送
     */
    public static final int PUSH_OCCUR_MAX = 50;

    /**
     * 关联的ID类型-动态
     */
    public static final int RELATE_ID_TYPE_DYNAMIC = 1;

    /**
     * 关联的ID类型-话题
     */
    public static final int RELATE_ID_TYPE_TOPIC = 2;

    public static final int SEND_METAQ_MAX = 10;
    // public static final int SEND_METAQ_MAX = 100;

    // 关注的明星发布原创动态
    public static final int JPUSH_EVENT_STAR_DYNAMIC = 1;

    // 关注的粉丝圈有话题置顶
    public static final int JPUSH_EVENT_MY_FANS_HOT_TOPIC = 2;

    // 粉丝圈发表的话题被置顶
    public static final int JPUSH_EVENT_FANS_HOT_TOPIC = 3;

    // 话题评论超过50条
    public static final int JPUSH_EVENT_TOPIC_HOT_COMMENT = 4;

    // 官方活动
    public static final int JPUSH_EVENT_KANKAN_ACTIVITY = 5;

    // 新签约明星
    public static final int JPUSH_EVENT_NEW_STAR = 6;

    // 某明星发了动态
    public static final int JPUSH_EVENT_MY_STAR_DYNAMIC = 7;

    public static final int F_TOPIC_VIEWS_MAX_CACHE = 5000;

    public static final int DICT_INFO_EDITOR = 1;

    public static final int JEDIS_SINGLENODE = 0;

    public static final int JEDIS_SHARDED = 1;

    public static final int ANNOUNCETYPE = 1;

    public static final int BANNERTYPE = 2;
    
    //视频转码失败
    public static final int VIDEO_ENCODING_FAILED = 4;

}
