package com.ironhack.business.configurations.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;

//@Component
public class FeignClientInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
//        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        requestTemplate.header(Constants.HEADER_AUTHORIZACION_KEY, String.format("%s %s", Constants.TOKEN_BEARER_PREFIX, TokenContextHolder.getToken()));
    }
}
