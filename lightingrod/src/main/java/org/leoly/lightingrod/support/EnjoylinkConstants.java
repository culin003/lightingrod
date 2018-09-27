/**
 * copywrite 2015-2020 智慧享联
 * 不能修改和删除上面的版权声明
 * 此代码属于智慧享联编写，在未经允许的情况下不得传播复制
 * EnjoylinkConstants.java
 * @Date 2018年5月24日 上午10:44:05
 * guguihe
 */
package org.leoly.lightingrod.support;

/**
 * TODO:
 * 
 * @author guguihe
 * @Date 2018年5月24日 上午10:44:05
 */
public interface EnjoylinkConstants {

	String DEFAULT_CHARSET = "UTF-8";

	/**
	 * 1分钟标前缀,1个手机1分钟不得超过1次验证码。
	 */
	String MSGMT = "MSGMT_";

	/**
	 * 1小时标识前缀,1个手机1小时不得超过3次验证码。
	 */
	String MSGHR = "MSGHR_";

	/**
	 * 24小时间标识前缀,1个手机24小时不得超过6次验证码
	 */
	String MSGDT = "MSGDT_";
	/**
	 * 发送次数过多描述
	 */
	String TOO_MANY_TIMES_DESCRIBE="发送短信频率太高";
	/**
	 * 用户不存在
	 */
	String USER_DOES_NOT_EXIST="USER_DOES_NOT_EXIST";
	/**
	 * 密码不存在
	 */
	String USER_PASSWORD_ERROR="USER_PASSWORD_ERROR";
	/**
	 * 验证码错误
	 */
	String VERIFYCODE_ERROR="VERIFYCODE_ERROR";
	
	
	String VERIFYCODE="SESSIONVERIFYCODE";
	
	/**
	 * 
	 */
	String LOGIN_SUCCESS="LOGIN_SUCCESS";
	/**
	 * 接入系统对象前缀
	 */
	String ACCESS="ACCESS_";

	/**
	 * 通道信息
	 */
	String CHANNEL="CHANNEL_";
	/**
	 * 模板信息提示语
	 */
	String TEMPLATE_ILLEGAL="不存在模板信息或模板未审核通过";
	/**
	 * 短信列表前缀
	 */
	String MESSAGE_SMS_TNAME="message_sms_";
	/**
	 * 删除成功
	 */
	String DELETE_SUCCESS="DELETE_SUCCESS";
	/**
	 * 删除失败
	 */
	String DELETE_FAIL="DELETE_FAIL";
	/**
	 * 不能删除该记录
	 */
	String DELETE_NO="DELETE_NO";
	
	/**
	 * 通道列表
	 */
	String SERVER_LIST="server_list";
	/**
	 * 策略列表
	 */
	String STRATEGY_LIST="strategy_list";
	/**
	 * 存在记录
	 */
	String EXISTENCE="EXISTENCE";
	/**
	 * 不存在记录
	 */
	String NONEXISTENT="NONEXISTENT";
	
	/**
	 * 异系异常
	 */
	String ERROR="ERROR";
	
	/**
	 * token放到header
	 */
	String TOKENHEAD="TOKENHEAD";
	/**
	 * token通过参数
	 */
	String TOKENPARAMETER="TOKENPARAMETER";
}
