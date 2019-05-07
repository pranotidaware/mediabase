package org.superbiz.moviefun.podcasts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
@EnableEurekaClient
public class PodcastMicroserviceApplication {

    public static void main(String... args) {
        SpringApplication.run(PodcastMicroserviceApplication.class, args);
    }
}
