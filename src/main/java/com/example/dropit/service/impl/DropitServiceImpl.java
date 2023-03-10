package com.example.dropit.service.impl;

import com.example.dropit.config.GeoapifyConfigurationProperties;
import com.example.dropit.dto.SingleLineAddressDto;
import com.example.dropit.exception.ResolveAddressException;
import com.example.dropit.feign.GeoapifyFeignClient;
import com.example.dropit.model.geoapify.GeoapifyResponse;
import com.example.dropit.model.geoapify.Properties;
import com.example.dropit.service.DropitService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@Data
public class DropitServiceImpl implements DropitService {

    private final GeoapifyConfigurationProperties geoapifyConfigurationProperties;
    private final GeoapifyFeignClient geoapifyFeignClient;

    @Override
    public Properties resolveAddress(SingleLineAddressDto singleLineAddressDto) {
        GeoapifyResponse geoapifyResponse = geoapifyFeignClient.getGeoapifyResponse(singleLineAddressDto.getSearchTerm(), geoapifyConfigurationProperties.getToken());

        if (CollectionUtils.isEmpty(geoapifyResponse.getFeatures())
                || !StringUtils.hasLength(geoapifyResponse.getFeatures().get(0).getProperties().getPostcode())) {
            throw new ResolveAddressException("Postcode not found. Check address string: <Building number> <Street> <City> <Country>");
        }

        return geoapifyResponse.getFeatures().get(0).getProperties();
    }
}
