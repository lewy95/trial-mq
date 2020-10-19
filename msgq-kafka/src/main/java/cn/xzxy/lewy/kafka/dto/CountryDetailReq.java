package cn.xzxy.lewy.kafka.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CountryDetailReq {

    @NotNull(message = "countryId不能为空")
    Integer countryId;
}
