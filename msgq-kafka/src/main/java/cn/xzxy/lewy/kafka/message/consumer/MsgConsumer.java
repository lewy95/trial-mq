package cn.xzxy.lewy.kafka.message.consumer;

import cn.xzxy.lewy.kafka.common.util.FastJsonUtil;
import cn.xzxy.lewy.kafka.message.handler.MessageHandler;
import cn.xzxy.lewy.kafka.message.model.ConstantsMessage;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Map;

/**
 * consumer regulation ：
 * >>> 1. check title，if null or others，ignore it；
 * >>> 2. check message body，if empty，ignore it；
 * >>> 3. check idempotence, if failed，ignore it；
 * >>> 4. seek for handler，if not suitable, ignore it；
 * >>> 5. check businessType which decides the action for handler，if null，ignore it；
 */
@Component
@Slf4j
public class MsgConsumer {

    //@Autowired
    //private RedissonClient redissonClient;

    private static final String MESSAGE_PREFIX = "COUNTRY_MESSAGE_TITLE_";

    @Autowired
    private Map<String, MessageHandler> messageHandlerMap;

    @KafkaListener(topics = ConstantsMessage.COUNTRY_TOPIC, groupId = "console-consumer-75829")
    public void acceptMessage(ConsumerRecord message) {
        log.info("收到消息{}", Thread.currentThread().getName());

        String title = message.key().toString();
        if (!ConstantsMessage.TITLE_FOR_EU.equals(title)
                && !ConstantsMessage.TITLE_FOR_AS.equals(title)) {
            log.warn("========非本业务消息========");
            return;
        }

        // 获取消息推送过来的消息体
        Object messageContent = FastJsonUtil.from(message.value(), JSONObject.class).get("value");
        if (ObjectUtils.isEmpty(messageContent)) {
            log.warn("消息内容为空, 不予处理req{}", message);
            return;
        }

        //String messageId = FastJsonUtil.from(message.value(), JSONObject.class).get("messageId").toString();
        //log.info("【接收到的消息】messageId: {} ", messageId);
        // 幂等性：判断该条消息是否已被消费过
        //if (this.checkConsumerMessage(messageId)) {
        //    log.info("该条消息已被消费过>>>messageId: {} ", messageId);
        //    return;
        //}

        // 获取消息处理类
        MessageHandler messageHandler = messageHandlerMap.get(MESSAGE_PREFIX + title);
        // 处理消息
        if (messageHandler == null) {
            log.warn("未获取到{}类型的消息handler", title);
            return;
        }
        // 格式转化
        JSONObject contentJson = JSONObject.parseObject(JSONObject.toJSONString(messageContent));
        messageHandler.handler(title, contentJson);
    }

    /**
     * 消息处理幂等性实现
     * @param messageId
     * @return true 消费过 false 未消费
     */
//    private boolean checkConsumerMessage(String messageId) {
//        try {
//            RBucket<String> messageIdCache = redissonClient.getBucket("COUNTRY_" + messageId);
//            // 判断是否已经消费过，消费过true
//            if (messageIdCache.isExists()) {
//                return true;
//            }
//            // 未消费返回false，并且设置messageId到redis
//            messageIdCache.set("COUNTRY_" + messageId, 12, TimeUnit.HOURS);
//        } catch (Exception e) {
//            log.error("redis处理country Message失败:{}", e.getMessage());
//            return false;
//        }
//        return false;
//    }
}
