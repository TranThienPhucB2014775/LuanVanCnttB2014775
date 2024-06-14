//package com.profile.service.client;
//
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import reactor.core.publisher.Mono;
//
//@FeignClient(name = "profile-service")
//public class UserClientService {
//    @PostMapping("/api/v1/auth/introspect")
//    public Mono<ApiResponse<IntrospectResponse>> introspect(@RequestBody IntrospectRequest request);
//}
