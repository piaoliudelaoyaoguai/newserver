package com.centit.server.dingtalk.manager.uitls;/**
 * Created by li_hao on 2017/6/10.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @version : 1.0
 * @Author : li_hao
 * @Description : 钉钉接口地址类
 * @Date : 2017-06-10 17:47
 **/
public class CommomUrl {
    private static Logger log = LoggerFactory.getLogger(CommomUrl.class);


    /** 获取Access_Token 接口调用请求地址（请求方式：get） */
    public static final String ACCESS_TOKEN_URL = "https://oapi.dingtalk.com/gettoken?corpid=CORPID&corpsecret=CORPSECRET";
    
    /** 获取SsoToken 接口调用请求地址（请求方式：get）*/
    public static final String SSO_TOKEN_URL = "https://oapi.dingtalk.com/sso/gettoken?corpid=CORPID&corpsecret=SSOSECRET";
    
    /** 获取通讯录权限 接口调用请求地址（请求方式：get）*/
    public static final String AUTH_SCOPES_URL = "https://oapi.dingtalk.com/auth/scopes?access_token=ACCESS_TOKEN";
    
    /** 获取部门列表  接口调用请求地址（请求方式：get）*/
    public static final String DEPARTMENTS_URL = "https://oapi.dingtalk.com/department/list?access_token=ACCESS_TOKEN&id=PARENTDEPTID&fetch_child=FETCH_CHILD";
    
    /** 获取部门详情  接口调用请求地址（请求方式：get）通讯录语言(默认zh_CN另外支持en_US) */
    public static final String DEPARTMENT_DETAIL_URL = "https://oapi.dingtalk.com/department/get?access_token=ACCESS_TOKEN&id=DEPTID&lang=zh_CN";
    
    /** 获取部门成员列表  接口调用请求地址（请求方式：get） */
    public static final String DEPARTMENT_USERS_URL = "https://oapi.dingtalk.com/user/simplelist?access_token=ACCESS_TOKEN&department_id=DEPTID";
    
    /** 获取部门成员(详情)列表  接口调用请求地址（请求方式：get） */
    public static final String DEPARTMENT_DETAIL_USERS_URL = "https://oapi.dingtalk.com/user/list?access_token=ACCESS_TOKEN&department_id=DEPTID";
    
    /** 获取管理员列表  接口调用请求地址（请求方式：get）*/
    public static final String GRT_ADMINS_URL = "https://oapi.dingtalk.com/user/get_admin?access_token=ACCESS_TOKEN";
    
    /** 获取成员详情  接口调用请求地址（请求方式：get）*/
    public static final String USER_DETAIL_URL = "https://oapi.dingtalk.com/user/get?access_token=ACCESS_TOKEN&userid=USERID";

    /** 创建部门  接口调用请求地址（请求方式：post）*/
    public static final String DEPARTMENT_CREATE = "https://oapi.dingtalk.com/department/create?access_token=ACCESS_TOKEN";

    /** 更新部门  接口调用请求地址（请求方式：post）*/
    public static final String DEPARTMENT_UPDATE = "https://oapi.dingtalk.com/department/update?access_token=ACCESS_TOKEN";

    /** 删除部门  接口调用请求地址（请求方式：get）*/
    public static final String DEPARTMENT_DELETE = "https://oapi.dingtalk.com/department/delete?access_token=ACCESS_TOKEN&id=ID";

    /** 创建人员  接口调用请求地址（请求方式：post）*/
    public static final String USER_CREATE = "https://oapi.dingtalk.com/user/create?access_token=ACCESS_TOKEN";

    /** 更新人员  接口调用请求地址（请求方式：post）*/
    public static final String USER_UPDATE = "https://oapi.dingtalk.com/user/update?access_token=ACCESS_TOKEN";

    /** 删除人员  接口调用请求地址（请求方式：get）*/
    public static final String USER_DELETE = "https://oapi.dingtalk.com/user/delete?access_token=ACCESS_TOKEN&userid=ID";
    
    /** 获取套件访问Token（suite_access_token）  接口调用请求地址（请求方式：post）*/
    public static final String SUITE_ACCESS_TOKEN_URL = "https://oapi.dingtalk.com/service/get_suite_token";
    
    /** 获取企业的永久授权（包含永久授权码和企业corpid等信息）  接口调用请求地址（请求方式：post）*/
    public static final String GET_PERMANENT_CODE_URL = "https://oapi.dingtalk.com/service/get_permanent_code?suite_access_token=SUITE_ACCESS_TOKEN";
    
    /** 激活授权套件  接口调用请求地址（请求方式：post）*/
    public static final String ACTIVATE_SUITE_URL = "https://oapi.dingtalk.com/service/activate_suite?suite_access_token=SUITE_ACCESS_TOKEN";
    
    /** 获取企业授权的access_token  接口调用请求地址（请求方式：post）*/
    public static final String GET_CORP_TOKEN_URL = "https://oapi.dingtalk.com/service/get_corp_token?suite_access_token=SUITE_ACCESS_TOKEN";
    
    /** 获取jsapi_ticket  接口调用请求地址（请求方式：post）*/
    public static final String GET_JSAPI_TICKET_URL = "https://oapi.dingtalk.com/get_jsapi_ticket?access_token=ACCESS_TOKE";
    
    /** ISV为授权方的企业单独设置IP白名单  接口调用请求地址（请求方式：post）*/
    public static final String SET_CORP_IPWHITELIST = "https://oapi.dingtalk.com/service/set_corp_ipwhitelist?suite_access_token=SUITE_ACCESS_TOKEN";
    
    /** 获取企业授权的授权数据  接口调用请求地址（请求方式：post）*/
    public static final String GET_AUTH_INFO = "https://oapi.dingtalk.com/service/get_auth_info?suite_access_token=SUITE_ACCESS_TOKEN";
    
    /** 获取企业的应用信息  接口调用请求地址（请求方式：post）*/
    public static final String GET_AGENT = "https://oapi.dingtalk.com/service/get_agent?suite_access_token=SUITE_ACCESS_TOKEN";
    
    /** 上传媒体文件  接口调用请求地址（请求方式：post）*/
    public static final String MEDIA_UPLOAD = "https://oapi.dingtalk.com/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
    
    /** 获取媒体文件  接口调用请求地址（请求方式：get）*/
    public static final String MEDIA_DOWNLOAD = "https://oapi.dingtalk.com/media/downloadFile?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";

    /** 新增文件到用户钉盘  接口调用请求地址（请求方式：get）*/
    public static final String DINGSPACE_FILE_ADD = "https://oapi.dingtalk.com/cspace/add?access_token=ACCESS_TOKEN&agent_id=AGENT_ID&code=CODE&media_id=MEDIA_ID&space_id=SPACE_ID&folder_id=FOLDER_ID&name=NAME&overwrite=OVERWRITE";
    
    /** 获取企业下的自定义空间  接口调用请求地址（请求方式：get）*/
    public static final String GET_CUSTOM_SPACE = "https://oapi.dingtalk.com/cspace/get_custom_space?access_token=ACCESS_TOKEN&domain=DOMAIN&agent_id=AGENT_ID";
    
    /** 授权用户访问企业下的自定义空间  接口调用请求地址（请求方式：get）*/
    public static final String GRANT_CUSTOM_SPACE = "https://oapi.dingtalk.com/cspace/grant_custom_space?access_token=ACCESS_TOKEN&domain=DOMAIN&agent_id=AGENT_ID&type=TYPE&userid=USERID&path=PATH&fileids=FILEIDS&duration=DURATION";
    
    /** 企业会话消息接口 （企业发消息） */
    public static final String ECO_ROUTE_REST = "https://eco.taobao.com/router/rest";

    /**企业会话消息异步发送接口V2 （企业发消息） */
    public static final String MESSAGE_ASYNCSEND_V2 = "https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2";
    
    /** 企业发送群消息 */
    public static final String CORP_SEND_CHAT_NEWS = "https://oapi.dingtalk.com/chat/send?access_token=ACCESS_TOKEN";
    
    /** 企业发送普通消息 */
    public static final String CORP_SEND_CONVERSATION_NEWS = "https://oapi.dingtalk.com/message/send_to_conversation?access_token=ACCESS_TOKEN";
    
    /** 注册事件回调接口（请求方式：post） */
    public static final String REGISTER_CALL_BACK = "https://oapi.dingtalk.com/call_back/register_call_back?access_token=ACCESS_TOKEN";
    
    /** 查询事件回调接口（请求方式：get） */
    public static final String GET_CALL_BACK = "https://oapi.dingtalk.com/call_back/get_call_back?access_token=ACCESS_TOKEN";
    
    /** 更新事件回调接口（请求方式：post） */
    public static final String UPDATE_CALL_BACK = "https://oapi.dingtalk.com/call_back/update_call_back?access_token=ACCESS_TOKEN";
    
    /** 删除事件回调接口（请求方式：get） */
    public static final String DELETE_CALL_BACK = "https://oapi.dingtalk.com/call_back/delete_call_back?access_token=ACCESS_TOKEN";
    
    /** 获取回调失败的结果 （请求方式：get）*/
    public static final String GET_CALL_BACK_FAILED_RESULT = "https://oapi.dingtalk.com/call_back/get_call_back_failed_result?access_token=ACCESS_TOKEN";
    
    
    
}
