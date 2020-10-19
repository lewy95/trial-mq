package cn.xzxy.lewy.kafka.message.producer;

import cn.xzxy.lewy.kafka.message.model.MessageVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
@AllArgsConstructor
public class MsgProducer {

    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMsg(MessageVO messageVO) {
        try {
            kafkaTemplate.send(messageVO.getTopic(), messageVO.getKey(), messageVO);
            log.info("消息推送成功>>推送消息数目1条");
        } catch (Exception e) {
            log.error("消息推送失败>>失败原因：{}", e.getMessage());
            e.printStackTrace();
        }
    }
}
