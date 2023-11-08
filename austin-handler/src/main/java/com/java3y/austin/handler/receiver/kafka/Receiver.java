package com.java3y.austin.handler.receiver.kafka;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.java3y.austin.common.domain.TaskInfo;
import com.java3y.austin.handler.receiver.service.ConsumeService;
import com.java3y.austin.handler.utils.GroupIdMappingUtils;
import com.java3y.austin.support.constans.MessageQueuePipeline;
import com.java3y.austin.support.domain.MessageTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Scope;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;

/**
 * @author 3y
 * 消费MQ的消息
 */
@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@ConditionalOnProperty(name = "austin.mq.pipeline", havingValue = MessageQueuePipeline.KAFKA)
public class Receiver {
    @Autowired
    private ConsumeService consumeService;

    /**
     * 发送消息
     *
     * @param consumerRecord
     * @param topicGroupId  austinBusiness
     */
    @KafkaListener(topics = "#{'${austin.business.topic.name}'}", containerFactory = "filterContainerFactory")
    public void consumer(ConsumerRecord<?, String> consumerRecord, @Header(KafkaHeaders.GROUP_ID) String topicGroupId) {
        Optional<String> kafkaMessage = Optional.ofNullable(consumerRecord.value());
        if (kafkaMessage.isPresent()) {

            List<TaskInfo> taskInfoLists = JSON.parseArray(kafkaMessage.get(), TaskInfo.class);
            String messageGroupId = GroupIdMappingUtils.getGroupIdByTaskInfo(CollUtil.getFirst(taskInfoLists.iterator()));
            /**
             * 每个消费者组 只消费 他们自身关心的消息
             */
            if (topicGroupId.equals(messageGroupId)) {
                consumeService.consume2Send(taskInfoLists);
            }
        }
    }

    /**
     * 撤回消息
     *
     * @param consumerRecord
     */
    @KafkaListener(topics = "#{'${austin.business.recall.topic.name}'}", groupId = "#{'${austin.business.recall.group.name}'}", containerFactory = "filterContainerFactory")
    public void recall(ConsumerRecord<?, String> consumerRecord) {
        Optional<String> kafkaMessage = Optional.ofNullable(consumerRecord.value());
        if (kafkaMessage.isPresent()) {
            MessageTemplate messageTemplate = JSON.parseObject(kafkaMessage.get(), MessageTemplate.class);
            consumeService.consume2recall(messageTemplate);
        }
    }

    /**
     * kafka test shuaiTopic austin.business.test.group.name
     */

    @KafkaListener(topics = "#{'${austin.business.test.topic.name}'}", groupId = "#{'${austin.business.test.group.name}'}", containerFactory = "filterContainerFactory")
    public void getShuaiTopic(ConsumerRecord<?, String> consumerRecord) {
        Optional<String> kafkaMessage = Optional.ofNullable(consumerRecord.value());
        System.out.println("shuaiTopic==================NaiziKou==========success==");
        System.out.println(kafkaMessage);
    }

    public static void main(String[] args) {
            Properties props= new Properties();
            //props.put("bootstrap.servers","192.168.44.161:9093,192.168.44.161:9094,192.168.44.161:9095");
            props.put("bootstrap.servers","8.137.23.240:9092");
            props.put("group.id","shuaiTopicGroupId");
            // 是否自动提交偏移量，只有commit之后才更新消费组的 offset
            props.put("enable.auto.commit","true");
            // 消费者自动提交的间隔
            props.put("auto.commit.interval.ms","1000");
            // 从最早的数据开始消费 earliest | latest | none
            props.put("auto.offset.reset","earliest");
            props.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
            props.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
            KafkaConsumer<String,String> consumer=new KafkaConsumer<String, String>(props);

            // 订阅队列
            consumer.subscribe(Arrays.asList("shuaiTopic"));

            //指定Offset消费
            consumer.poll(Duration.ofMillis(50000));
            final Set<TopicPartition> topicPartitions = consumer.assignment();
            topicPartitions.forEach(t -> {
                consumer.seek(new org.apache.kafka.common.TopicPartition(t.topic(),t.partition()),0);
                log.info("Reset offset {} to zero.",t);
            });

            try {
                while (true){
                    ConsumerRecords<String,String> records=consumer.poll(Duration.ofMillis(1000));
                    for (ConsumerRecord<String,String> record:records){
                        System.out.printf("offset = %d ,key =%s, value= %s, partition= %s%n" ,record.offset(),record.key(),record.value(),record.partition());
                    }
                }
            }finally {
                consumer.close();
            }
        }



}
