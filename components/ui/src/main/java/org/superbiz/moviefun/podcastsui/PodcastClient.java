package org.superbiz.moviefun.podcastsui;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

public class PodcastClient {
    private static ParameterizedTypeReference<List<PodcastUI>> movieListType = new ParameterizedTypeReference<List<PodcastUI>>() {
    };

    private RestOperations restOperations;
    private static final int CACHE_SIZE = 5;
    private final List<PodcastUI> lastRead = new ArrayList<>(CACHE_SIZE);
    private static final Logger log = LoggerFactory.getLogger(PodcastClient.class);
    private String moviesURL;


    public PodcastClient(String moviesURL, RestOperations restOperations) {
        this.restOperations = restOperations;
        this.moviesURL = moviesURL;
    }

    public void create(PodcastUI movie) {
        restOperations.postForEntity(moviesURL, movie, PodcastUI.class);
    }

    @HystrixCommand(fallbackMethod="getAllFallback")
    public List<PodcastUI> getAll() {
        List<PodcastUI> read = restOperations.exchange(moviesURL, HttpMethod.GET, null, movieListType).getBody();
        log.debug("Read {} podcasts from {}", read.size(), moviesURL);

        lastRead.clear();
        int copyCount = (read.size() < CACHE_SIZE) ? read.size() : CACHE_SIZE;
        for (int i =0; i < copyCount; i++)
            lastRead.add(read.get(i));
        log.debug("Copied {} podcasts into the cache", copyCount);

        return read;
    }

    public List<PodcastUI> getAllFallback() {
        log.debug("Returning {} podcasts from the fallback method", lastRead.size());

        return lastRead;
    }
    public void delete(Long id) {
        String deleteURL = new StringBuilder(moviesURL).append("/").append(id).toString();
        restOperations.delete(deleteURL);
    }

    public int count(String field, String key) {
        String URI = UriComponentsBuilder.fromHttpUrl(moviesURL + "/count")
                .queryParam("field", field)
                .queryParam("key", key)
                .build().toUriString();
        return restOperations.getForEntity(URI, Integer.class).getBody();
    }

    public int countAll() {
        return restOperations.getForEntity(moviesURL + "/count", Integer.class).getBody();
    }

    public List<PodcastUI> findAll(int offset, int size) {
        String URI = UriComponentsBuilder.fromUriString(moviesURL)
                .queryParam("start", offset)
                .queryParam("pageSize", size)
                .toUriString();
        return restOperations.exchange(URI, HttpMethod.GET, null, movieListType).getBody();
    }

    public List<PodcastUI> findRange(String field, String key, int offset, int size) {
        String URI = UriComponentsBuilder.fromUriString(moviesURL)
                .queryParam("field", field)
                .queryParam("key", key)
                .queryParam("start", offset)
                .queryParam("pageSize", size)
                .toUriString();
        return restOperations.exchange(URI, HttpMethod.GET, null, movieListType).getBody();

    }

    public PodcastUI find(Long id) {
        String findURL = new StringBuilder(moviesURL).append("/").append(id).toString();
        return restOperations.getForEntity(findURL, PodcastUI.class).getBody();
    }

}
