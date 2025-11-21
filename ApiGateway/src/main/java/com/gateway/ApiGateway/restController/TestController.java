package com.gateway.ApiGateway.restController;

//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/public")
    public String publicEndpoint() {
        return "Public endpoint - no auth required";
    }

//    @GetMapping("/private")
////    @PreAuthorize("hasAuthority('SCOPE_api.read')")
//    public String privateEndpoint(Authentication authentication) {
//        return "Hello " + authentication.getName() + ", you are authenticated!";
//    }
}