/*
 * @Copyright: Copyright (c) 2015-2018 此代码属于智慧享联，在未经允许的情况下禁止复制传播
 * @Company:智慧享联
 * @filename SmsNotifyer
 * @author guguihe
 * @date 2018-09-26 11:52
 */

package org.leoly.lightingrod.notifyer;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.leoly.lightingrod.guard.mysql.MySqlSlaveStatus;
import org.leoly.lightingrod.support.CodingUtil;
import org.leoly.lightingrod.support.ReflactUtils;
import org.leoly.lightingrod.support.gson.GsonUtils;
import org.leoly.lightingrod.support.okhttp.OKHttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO:
 *
 * @author guguihe
 * @Date 2018-09-26 11:52
 */
@Service("smsNotifyer")
public class ErrorNotifyer {

    private Logger logger = LoggerFactory.getLogger("SmsNotifyer_Log");

    @Value("${lightingrod.sms.api.url}")
    private String smsApi;

    @Value("${lightingrod.sms.api.systemcode}")
    private String systemCode;

    @Value("${lightingrod.sms.api.3deskey}")
    private String des3Key;

    @Value("${lightingrod.sms.api.templateCode}")
    private String templateCode;

    @Value("${lightingrod.sms.api.mobiles}")
    private String mobiles;

    @Value("${lightingrod.mail.api.url}")
    private String mailApi;

    @Value("${lightingrod.email.from}")
    private String mailFrom;

    @Value("${lightingrod.email.tos}")
    private String mailTo;

    @Value("${lightingrod.proxy.use}")
    private String useProxy;

    @Value("${lightingrod.proxy.host}")
    private String proxyHost;

    @Value("${lightingrod.proxy.port}")
    private String proxyPort;

    @Async
    public void sendEmail(Object target, String msg) {
        // 构建请求参数对象
        Map<String, Object> paramDatas = new HashMap<>();
        // 数据来源，即系统编码
        paramDatas.put("source", systemCode);
        paramDatas.put("mailFrom", mailFrom);
        paramDatas.put("mailTo", mailTo);
        paramDatas.put("mailTitle", "！！重要！！系统异常邮件！！");
        StringBuilder content = new StringBuilder();
        content.append("<p>各位领导：</p>");
        content.append("<p style=\"padding-left:10px;\">Lighting Rod系统监控发生异常情况，请及时进行处理！</p>");
        content.append("<p style=\"padding-left:10px;\">异常描述：").append(msg).append("，异常信息如下：</p>");
        content.append("<table border=\"0\" cellspacing=\"1\" cellpadding=\"0\" style=\"font-family: verdana, arial, sans-serif;font-size: 12px;color: #333333;border-width: 1px;border-color: #EDEDED;border-collapse: collapse;font-family: verdana, arial, sans-serif;\">");
        if (target instanceof List) {
            List<?> objects = (List<?>) target;
            Object obj = objects.get(0);
            List<Method> methods = ReflactUtils.getDeclaredMethods(obj.getClass(), new ReflactUtils.ReflactFilter() {
                @Override
                public boolean accept(String name) {
                    return StringUtils.startsWith(name, ReflactUtils.GETTER);
                }
            });

            appendHeader(content, methods, obj);
            objects.forEach(tmpobj -> {
                appendBody(content, methods, tmpobj);
            });
        } else {
            List<Method> methods = ReflactUtils.getDeclaredMethods(target.getClass(), new ReflactUtils.ReflactFilter() {
                @Override
                public boolean accept(String name) {
                    return StringUtils.startsWith(name, ReflactUtils.GETTER);
                }
            });
            appendHeader(content, methods, target);
            appendBody(content, methods, target);
        }

        content.append("</table>");
        paramDatas.put("mailContent", content.toString());
        sendMessage(paramDatas, mailApi);
    }


    private void appendHeader(StringBuilder content, List<Method> methods, Object target) {
        content.append("<tr>");
        methods.forEach(method -> {
            content.append("<td style=\"border-width: 1px;padding: 8px;border-style: solid;border-color: #EDEDED;max-width : 30px;background-color: #f7f7f7;\">").append(ReflactUtils.getFieldFromMethod(method.getName())).append("<td>");
        });

        content.append("</tr>");
    }

    private void appendBody(StringBuilder content, List<Method> methods, Object target) {
        content.append("<tr>");
        methods.forEach(method -> {
            try {
                Object obj = method.invoke(target);
                content.append("<td style=\"border-width: 1px;padding: 8px;text-align: center;border-style: solid;border-color: #EDEDED;\">").append(ObjectUtils.defaultIfNull(obj, StringUtils.EMPTY).toString()).append("<td>");
            } catch (IllegalAccessException e) {
                logger.error("方法无法执行！", e);
            } catch (InvocationTargetException e) {
                logger.error("方法调用失败！", e);
            }
        });

        content.append("</tr>");
    }

    private void sendMessage(Object paramDatas, String apiUrl) {
        try {
            // 请求数据体经过3DES加密
            String requestData = CodingUtil.des3Encode(GsonUtils.toString(paramDatas), des3Key);
            // 请求数据体签名
            String sign = CodingUtil.md5(systemCode + requestData + des3Key);

            // 实际请求的参数体为一个JSON对象
            Map<String, Object> requestParams = new HashMap<>();
            requestParams.put("systemCode", systemCode);
            requestParams.put("data", requestData);
            requestParams.put("sign", sign);

            // POST实际请求参数JSON字符串到服务器中
            String requestParamsJson = GsonUtils.toString(requestParams);
            String sendResult = StringUtils.EMPTY;
            if("YES".equalsIgnoreCase(useProxy)) {
                sendResult = OKHttpUtils.getHttpUtils().setProxy(proxyHost, proxyPort).proxyPost(apiUrl, requestParamsJson);
            }else{
                sendResult = OKHttpUtils.getHttpUtils().httpPost(apiUrl, requestParamsJson);
            }
            logger.info("调用接口结果：{}", sendResult);
        } catch (Exception e) {
            logger.error("调用接口异常！！", e);
        }
    }

    @Async
    public void sendSms(String msg, String serverInfo) {
        // 构建请求参数对象
        Map<String, Object> paramDatas = new HashMap<>();
        // 数据来源，即系统编码
        paramDatas.put("source", systemCode);
        // 短信模板编码，通过在短信服务系统中注册模板后获取，SMS_135395108即为模板“您的验证码是：${validCode}，有效时间5分钟。请勿向任何人提供您收到的验证码。”的编码
        paramDatas.put("templateCode", templateCode);
        // 短信发送手机号，多个手机号使用英文逗号分隔
        paramDatas.put("mobiles", mobiles);

        // 构建短信模板中使用的参数对象，
        // 如模板“您的验证码是：${validCode}，有效时间5分钟。请勿向任何人提供您收到的验证码。”中有参数${validCode}，需要添加validCode属性和值到对象中
        Map<String, Object> contentParams = new HashMap<>();
        contentParams.put("errorMsg", msg);
        contentParams.put("database", serverInfo);
        // 转换成JSON字符串放到请求参数对象的contentParams属性中
        paramDatas.put("contentParams", GsonUtils.toString(contentParams));

        sendMessage(paramDatas, smsApi);
    }

}
