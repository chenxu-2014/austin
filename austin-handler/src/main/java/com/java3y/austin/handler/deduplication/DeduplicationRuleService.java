package com.java3y.austin.handler.deduplication;

import com.java3y.austin.common.constant.CommonConstant;
import com.java3y.austin.common.domain.TaskInfo;
import com.java3y.austin.common.enums.DeduplicationType;
import com.java3y.austin.common.enums.EnumUtil;
import com.java3y.austin.support.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author 3y.
 * @date 2021/12/12
 * 去重服务
 */
@Service
public class DeduplicationRuleService {

    public static final String DEDUPLICATION_RULE_KEY = "deduplicationRule";

    @Autowired
    private ConfigService config;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private DeduplicationHolder deduplicationHolder;

    private  void sendRedisShuai() {
        for(int i=0;i<10;i++){
            redisTemplate.opsForValue().set("shuai"+String.valueOf(i),"chenxu"+String.valueOf(i),1, TimeUnit.HOURS);
        }
        redisTemplate.opsForValue().append("shuai7","append=====");
        System.out.println("sendRedisShuai success============================");
    }
    public void duplication(TaskInfo taskInfo) {
        // 配置样例：{"deduplication_10":{"num":1,"time":300},"deduplication_20":{"num":5}}
        //config.getProperty 启动applo nacos 优先读取远程配置，否则读取local.properties
        String deduplicationConfig = config.getProperty(DEDUPLICATION_RULE_KEY, CommonConstant.EMPTY_JSON_OBJECT);
        System.out.println("deduplicationConfig(去重读取配置)======================"+deduplicationConfig);
        sendRedisShuai();
        //System.out.println(redisTemplate.opsForValue().get("shuai12"));

        // 去重
        // EnumUtil 枚举工具类
        List<Integer> deduplicationList = EnumUtil.getCodeList(DeduplicationType.class);
        for (Integer deduplicationType : deduplicationList) {
            DeduplicationParam deduplicationParam = deduplicationHolder.selectBuilder(deduplicationType).build(deduplicationConfig, taskInfo);
            System.out.println("deduplicationParam======================"+deduplicationParam);
            System.out.println("deduplicationConfig======================"+deduplicationConfig);
            if (Objects.nonNull(deduplicationParam)) {
                deduplicationHolder.selectService(deduplicationType).deduplication(deduplicationParam);
            }
        }
    }


}
