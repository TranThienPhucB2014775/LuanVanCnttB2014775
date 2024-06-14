package com.gateway.client;

import com.gateway.dto.ApiResponse;
import com.gateway.dto.request.IntrospectRequest;
import com.gateway.dto.response.IntrospectResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

@FeignClient(name = "profile-service")
public interface ProfileFeignClient  {
    @PostMapping("/api/v1/auth/introspect")
    public Mono<ApiResponse<IntrospectResponse>> introspect(@RequestBody IntrospectRequest request);
}
