package org.superbiz.moviefun.podcasts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;


@SpringBootApplication
@EnableAuthorizationServer
@EnableResourceServer
@EnableEurekaClient
public class PodcastMicroserviceApplication {

    public static void main(String... args) {
        SpringApplication.run(PodcastMicroserviceApplication.class, args);
    }
}
