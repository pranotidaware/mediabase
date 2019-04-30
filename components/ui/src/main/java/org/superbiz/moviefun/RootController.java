package org.superbiz.moviefun;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.superbiz.moviefun.moviesui.MovieClient;
import org.superbiz.moviefun.moviesui.MovieUI;
import org.superbiz.moviefun.podcastsui.PodcastClient;
import org.superbiz.moviefun.podcastsui.PodcastUI;



import java.util.Map;

@Controller
public class RootController {
    private MovieClient movieClent;
    private PodcastClient podcastClient;

    public RootController(MovieClient movieClent, PodcastClient podcastClient) {
        this.movieClent = movieClent;
        this.podcastClient = podcastClient;
    }

    @GetMapping("/")
    public String rootPath() {
        return "index";
    }

    @GetMapping("/setup")
    public String setupDatabase(Map<String, Object> model) {

        movieClent.create(new MovieUI("Wedding Crashers", "David Dobkin", "Comedy", 7, 2005));
        movieClent.create(new MovieUI("Starsky & Hutch", "Todd Phillips", "Action", 6, 2004));
        movieClent.create(new MovieUI("Shanghai Knights", "David Dobkin", "Action", 6, 2003));
        movieClent.create(new MovieUI("I-Spy", "Betty Thomas", "Adventure", 5, 2002));
        movieClent.create(new MovieUI("The Royal Tenenbaums", "Wes Anderson", "Comedy", 8, 2001));
        movieClent.create(new MovieUI("Zoolander", "Ben Stiller", "Comedy", 6, 2001));
        movieClent.create(new MovieUI("Shanghai Noon", "Tom Dey", "Comedy", 7, 2000));

        model.put("podcasts", movieClent.getAll());

        podcastClient.create(new PodcastUI("Wait Wait...Don't Tell Me!",
                "NPR's weekly current events quiz.",
                "https://www.npr.org/programs/wait-wait-dont-tell-me/"));
        podcastClient.create(new PodcastUI("TED Radio Hour",
                "Guy Raz explores the emotions, insights, and discoveries that make us human.",
                "https://www.npr.org/programs/ted-radio-hour/"));
        podcastClient.create(new PodcastUI("Fresh Air",
                "Hosted by Terry Gross, this show features intimate conversations with today's biggest luminaries.",
                "https://www.npr.org/programs/fresh-air/"));
        podcastClient.create(new PodcastUI("NPR Politics Podcast",
                "The NPR Politics Podcast is where NPR's political reporters talk to you like they talk to each other.",
                "https://www.npr.org/sections/politics/"));

        model.put("podcasts", podcastClient.getAll());

        return "setup";
    }

}
