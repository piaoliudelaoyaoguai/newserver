package com.centit.server.dingtalk.manager.po;

/**
 * <p>通讯录-M2回调注册 需要监听的事件类型<p>
 * @version 1.0
 * @author li_hao
 * @date 2017年12月15日
 */
public class CallBackRegister{
	
	/**通讯录用户增加 */
	public static final String USER_ADD_ORG = "user_add_org";
	/**通讯录用户更改*/
	public static final String USER_MODIFY_ORG = "user_modify_org";
	/** 通讯录用户离职 */
	public static final String USER_LEAVE_ORG = "user_leave_org";
	/** 通讯录用户被设为管理员 */
	public static final String ORG_ADMIN_ADD = "org_admin_add";
	/** 通讯录用户被取消设置管理员 */
	public static final String ORG_ADMIN_REMOVE = "org_admin_remove";
	/**通讯录企业部门创建*/
	public static final String ORG_DEPT_CREATE = "org_dept_create";
	/** 通讯录企业部门修改 */
	public static final String ORG_DEPT_MODIFY = "org_dept_modify";
	/**通讯录企业部门删除*/
	public static final String ORG_DEPT_REMOVE = "org_dept_remove";
	/**企业被解散*/
	public static final String ORG_REMOVE = "org_remove";
	/**企业信息发生变更*/
	public static final String ORG_CHANGE = "org_change";
	/**员工角色信息发生变更*/
	public static final String LABEL_USER_CHANGE = "label_user_change";
	/**增加角色或者角色组*/
	public static final String LABEL_CONF_ADD = "label_conf_add";
	/**删除角色或者角色组*/
	public static final String LABEL_CONF_DEL = "label_conf_del";
	/**修改角色或者角色组*/
	public static final String LABEL_CONF_MODIFY = "label_conf_modify";
	/**m2识别回调*/
	public static final String FACE_RECOGNIZE = "face_recognize";
	/**测试回调接口事件类型*/
	public static final String CHECK_URL = "check_url";
	 
	 
	

	 
	
}
