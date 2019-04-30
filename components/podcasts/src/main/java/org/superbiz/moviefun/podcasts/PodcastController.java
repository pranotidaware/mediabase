package org.superbiz.moviefun.podcasts;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

    @RestController
    @RequestMapping("/podcasts")
    public class PodcastController {

        private PodcastRepository podcastRepository;

        public PodcastController(PodcastRepository podcastRepository) {
            this.podcastRepository = podcastRepository;
        }

        @PostMapping
        public ResponseEntity<Podcast> create(@RequestBody Podcast podcast) {

            podcastRepository.save(podcast);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Podcast> delete(@PathVariable Long id) {
            Podcast doomed = podcastRepository.findOne(id);
            if (doomed != null) podcastRepository.delete(doomed.getId());
            HttpStatus status = (doomed != null) ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(status);
        }

        @GetMapping()
        public Iterable<Podcast> read(){
                return podcastRepository.findAll();

        }

    }