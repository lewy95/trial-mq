package cn.xzxy.lewy.kafka.message.model;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

@Slf4j
public class JSONSerializer implements Serializer {

    @Override
    public byte[] serialize(String s, Object o) {
        return JSON.toJSONBytes(o);
    }

    @Override
    public void configure(Map configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, Headers headers, Object data) {
        return JSON.toJSONBytes(data);
    }

    @Override
    public void close() {

    }
}
