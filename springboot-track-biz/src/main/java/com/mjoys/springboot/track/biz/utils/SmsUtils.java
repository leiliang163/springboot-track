/**
 * Project Name:media-biz<br>
 * File Name:MsgUtils.java<br>
 * Package Name:cn.com.duiba.tuia.media.utils<br>
 * Date:2016年9月26日下午1:51:11<br>
 * Copyright (c) 2016, duiba.com.cn All Rights Reserved.<br>
 */

package com.mjoys.springboot.track.biz.utils;

import com.mjoys.springboot.track.common.constants.ErrorCode;
import com.mjoys.springboot.track.common.exception.InnerException;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: MsgUtils <br/>
 * Function: 发送短信验证码. <br/>
 * date: 2016年9月26日 下午1:51:11 <br/>
 *
 * @author leiliang
 * @version
 * @since JDK 1.6
 */
public class SmsUtils {

    /** The logger. */
    private static Logger logger = LoggerFactory.getLogger(SmsUtils.class);

    private SmsUtils      sms;

    public SmsUtils getSmsUtils() {
        if (sms == null) {
            sms = new SmsUtils();
        }
        return sms;
    }

    /**
     * sendCodeSms:(发送短信验证码). <br/>
     *
     * @author ZFZ
     * @param phone
     * @param text
     * @return
     * @since JDK 1.6
     */
    public static String sendCodeSms(String phone, String text){
        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost("http://yunpian.com/v1/sms/send.json");
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("mobile", phone));
            params.add(new BasicNameValuePair("text", text));
            params.add(new BasicNameValuePair("apikey", "dd66615d922f2519ff134c0df909e505"));
            post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            CloseableHttpResponse response = client.execute(post);
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            logger.error("send sms error, because of ", e);
            throw new InnerException(ErrorCode.E0002005);
        }
    }
}
