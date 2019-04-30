package org.superbiz.moviefun.podcastsui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class PodcastUIController {
    private PodcastClient podcastClient;

    public PodcastUIController(PodcastClient podcastClient) {
        this.podcastClient = podcastClient;
    }

    @GetMapping("/podcasts")
    public String allPodcasts(Map<String, Object> model) {
        model.put("podcasts", podcastClient.getAll() );
        return "podcasts";
    }

}
