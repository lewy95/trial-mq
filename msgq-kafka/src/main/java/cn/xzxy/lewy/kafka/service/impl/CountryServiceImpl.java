package cn.xzxy.lewy.kafka.service.impl;

import cn.xzxy.lewy.kafka.mapper.CountryMapper;
import cn.xzxy.lewy.kafka.pojo.Country;
import cn.xzxy.lewy.kafka.service.CountryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("countryService")
public class CountryServiceImpl implements CountryService {

    @Resource
    CountryMapper countryMapper;

    @Override
    public Country getCountry(int id) {

        return countryMapper.selectByPrimaryKey(id);
    }
}
