package org.superbiz.moviefun;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.superbiz.moviefun.movies.Movie;
import org.superbiz.moviefun.movies.MoviesBean;
import org.superbiz.moviefun.podcasts.Podcast;
import org.superbiz.moviefun.podcasts.PodcastRepository;

import java.util.Map;

@Controller
public class RootController {
    private MoviesBean moviesBean;
    private PodcastRepository podcastRepository;

    public RootController(MoviesBean moviesBean, PodcastRepository podcastRepository) {
        this.moviesBean = moviesBean;
        this.podcastRepository = podcastRepository;
    }

    @GetMapping("/")
    public String rootPath() {
        return "index";
    }

    @GetMapping("/setup")
    public String setupDatabase(Map<String, Object> model) {

        moviesBean.addMovie(new Movie("Wedding Crashers", "David Dobkin", "Comedy", 7, 2005));
        moviesBean.addMovie(new Movie("Starsky & Hutch", "Todd Phillips", "Action", 6, 2004));
        moviesBean.addMovie(new Movie("Shanghai Knights", "David Dobkin", "Action", 6, 2003));
        moviesBean.addMovie(new Movie("I-Spy", "Betty Thomas", "Adventure", 5, 2002));
        moviesBean.addMovie(new Movie("The Royal Tenenbaums", "Wes Anderson", "Comedy", 8, 2001));
        moviesBean.addMovie(new Movie("Zoolander", "Ben Stiller", "Comedy", 6, 2001));
        moviesBean.addMovie(new Movie("Shanghai Noon", "Tom Dey", "Comedy", 7, 2000));

        model.put("movies", moviesBean.getMovies());

        podcastRepository.save(new Podcast("Wait Wait...Don't Tell Me!",
                "NPR's weekly current events quiz.",
                "https://www.npr.org/programs/wait-wait-dont-tell-me/"));
        podcastRepository.save(new Podcast("TED Radio Hour",
                "Guy Raz explores the emotions, insights, and discoveries that make us human.",
                "https://www.npr.org/programs/ted-radio-hour/"));
        podcastRepository.save(new Podcast("Fresh Air",
                "Hosted by Terry Gross, this show features intimate conversations with today's biggest luminaries.",
                "https://www.npr.org/programs/fresh-air/"));
        podcastRepository.save(new Podcast("NPR Politics Podcast",
                "The NPR Politics Podcast is where NPR's political reporters talk to you like they talk to each other.",
                "https://www.npr.org/sections/politics/"));

        model.put("podcasts", podcastRepository.findAll());

        return "setup";
    }

}
