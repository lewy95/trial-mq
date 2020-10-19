package cn.xzxy.lewy.kafka.controller;


import cn.xzxy.lewy.kafka.common.model.JsonResponseEntity;
import cn.xzxy.lewy.kafka.dto.CountryKafkaDto;
import cn.xzxy.lewy.kafka.message.model.ConstantsMessage;
import cn.xzxy.lewy.kafka.message.model.MessageVO;
import cn.xzxy.lewy.kafka.message.producer.MsgProducer;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * test kafka message pushing
 */
@RestController
@RequestMapping("/message")
public class KafkaController {

    @Resource
    private MsgProducer msgProducer;

    @PostMapping("/push")
    public JsonResponseEntity sendMsg(@RequestBody @Valid CountryKafkaDto dto) {

        try {
            JSONObject jMessage = new JSONObject();
            // test for EU
            jMessage.put("businessType", ConstantsMessage.BUSINESS_TYPE_QUERY);
            jMessage.put("data", JSON.toJSON(dto));
            MessageVO message = new MessageVO(
                    ConstantsMessage.COUNTRY_TOPIC,
                    ConstantsMessage.TITLE_FOR_EU,
                    jMessage);
            msgProducer.sendMsg(message);
            return JsonResponseEntity.buildOK("Push Successfully", jMessage);
        } catch (Exception e) {
            return JsonResponseEntity.buildBusinessError("Push Failed");
        }
    }
}
