package org.superbiz.moviefun;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.client.RestOperations;

import org.superbiz.moviefun.moviesui.ActionServlet;
import org.superbiz.moviefun.moviesui.MovieClient;
import org.superbiz.moviefun.podcastsui.PodcastClient;

@SpringBootApplication
@EnableAuthorizationServer
/*@EnableResourceServer
@EnableEurekaClient*/
@EnableCircuitBreaker
public class Application {

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ServletRegistrationBean registerActionServlet(ActionServlet actionServlet) {
        return new ServletRegistrationBean(actionServlet, "/moviefun/*");
    }

    @Bean
    public MovieClient movieClient(RestOperations restOperations) {
        //return new MovieClient(moviesURL, restOperations);
        return new MovieClient("//movies-ms/movies", restOperations);
    }

    @Bean
    public PodcastClient podcastsClient(RestOperations restOperations) {
        return new PodcastClient("//podcasts-ms/podcasts", restOperations);
    }
}