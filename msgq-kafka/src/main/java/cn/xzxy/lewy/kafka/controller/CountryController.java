package cn.xzxy.lewy.kafka.controller;

import cn.xzxy.lewy.kafka.common.model.JsonResponseEntity;
import cn.xzxy.lewy.kafka.dto.CountryDetailReq;
import cn.xzxy.lewy.kafka.pojo.Country;
import cn.xzxy.lewy.kafka.service.CountryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/rest/country")
public class CountryController {

    @Resource
    private CountryService countryService;

    @PostMapping("detail")
    public JsonResponseEntity getCountry(@RequestBody @Valid CountryDetailReq countryDetailReq) {

        Country country = countryService.getCountry(countryDetailReq.getCountryId());

        if (country == null) {
            return JsonResponseEntity.buildBusinessError("无法查询到信息", 10086);
        } else {
            return JsonResponseEntity.buildOK("操作成功", country);
        }
    }

    @GetMapping("detailGet/{id}")
    public JsonResponseEntity getCountry2(@PathVariable int id) {

        Country country = countryService.getCountry(id);

        if (country == null) {
            return JsonResponseEntity.buildBusinessError("无法查询到信息", 10086);
        } else {
            return JsonResponseEntity.buildOK("操作成功", country);
        }
    }
}