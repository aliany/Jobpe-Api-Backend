package com.ironhack.edge.feing;

import com.ironhack.edge.dtos.BusinessDto;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@FeignClient("business-ms")
@LoadBalancerClient("business-ms")
public interface BusinessClient {

    @GetMapping("/business")
    List<BusinessDto> findAll();

    @PostMapping("/business")
    BusinessDto create(@RequestBody @Valid BusinessDto businessDto);

}
