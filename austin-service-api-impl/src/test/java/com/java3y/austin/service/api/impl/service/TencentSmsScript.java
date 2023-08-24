package com.java3y.austin.service.api.impl.service;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Throwables;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author 3y
 * @date 2022年06月26日16:30:33
 * 1. 发送短信接入文档：https://cloud.tencent.com/document/api/382/55981
 * 2. 推荐直接使用SDK调用
 * 3. 推荐使用API Explorer 生成代码
 */
@Slf4j
public class TencentSmsScript {



    public static void send(String phone, String content,String secretId,String secretKey,String sdkAppId) {

        try {

            /**
             * 初始化
             */
            Credential cred = new Credential(secretId, secretKey);
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("sms.tencentcloudapi.com");
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            SmsClient client = new SmsClient(cred, "ap-guangzhou", clientProfile);

            /**
             * 组装入参
             */
            SendSmsRequest req = new SendSmsRequest();
            String[] phoneNumberSet1 = new String[]{phone};
            req.setPhoneNumberSet(phoneNumberSet1);
            req.setSmsSdkAppId(sdkAppId);
            req.setSignName("Naizikou公众号");
            req.setTemplateId("1854942");
            String[] templateParamSet1 = {content};
            req.setTemplateParamSet(templateParamSet1);
            req.setSessionContext(IdUtil.fastSimpleUUID());

            /**
             * 发送
             */
            SendSmsResponse response = client.SendSms(req);

            log.info(JSON.toJSONString(response));
        } catch (Exception e) {
            log.error(Throwables.getStackTraceAsString(e));
        }
    }

    public static void main(String[] args) {
        try {
            // 创建URL对象，并指定要请求的接口地址
            URL url = new URL("https://v0.yiketianqi.com/api?unescape=1&version=v62&appid=38422471&appsecret=hfNk6wIx&city=西安");

            // 打开连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // 发送请求并获取响应
            int responseCode = connection.getResponseCode();
            BufferedReader reader;
            if (responseCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // 处理响应数据
            System.out.println("Response Code: " + responseCode);
            System.out.println("Response Body: " + response.toString());
            weatherResult wea=JSON.parseObject(response.toString(),weatherResult.class);
            StringBuilder sb=new StringBuilder();
            sb.append(wea.getCity()+",");
            sb.append(wea.getWea()+",");
            sb.append("空气质量"+wea.getAir_level());
            send("181---", sb.toString(), "AKIDPqBLEvPxLZbOyOcePyMXTDj273mKgood", "iwAE6lCQQEtRuJZdVCi2DNWknh5LQN9l", "1400836295");

        } catch (Exception e) {
            e.printStackTrace();
        }


        /**
         * 手机号填自己的
         * 账号信息填自己的
         */
    }

}

