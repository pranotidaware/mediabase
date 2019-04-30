package org.superbiz.moviefun.podcastsui;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

public class PodcastClient {
    private static ParameterizedTypeReference<List<PodcastUI>> movieListType = new ParameterizedTypeReference<List<PodcastUI>>() {
    };
    private RestOperations restOperations;
    private String moviesURL;


    public PodcastClient(String moviesURL, RestOperations restOperations) {
        this.restOperations = restOperations;
        this.moviesURL = moviesURL;
    }

    public void create(PodcastUI movie) {
        restOperations.postForEntity(moviesURL, movie, PodcastUI.class);
    }

    public List<PodcastUI> getAll() {
        return restOperations.exchange(moviesURL, HttpMethod.GET, null, movieListType).getBody();
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
