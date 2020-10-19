package cn.xzxy.lewy.kafka.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
// 指定要扫描的Mapper类的包的路径
@MapperScan("cn.xzxy.lewy.kafka.mapper")
public class MybatisConfig {

}
