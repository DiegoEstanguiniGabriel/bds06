package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.MovieDTO;
import com.devsuperior.movieflix.dto.MovieGenreDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.repositories.GenreRepository;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Transactional(readOnly = true)
    public MovieGenreDTO findById(Long id){
        Optional<Movie> obj = repository.findById(id);
        Movie entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new MovieGenreDTO(entity, entity.getGenre());
    }

    @Transactional(readOnly = true)
    public List<ReviewDTO> findReviews(Long movieId){
        List<Review> list = reviewRepository.findAllByMovieId(movieId);
        return list.stream().map(x -> new ReviewDTO(x, x.getMovie(), x.getUser())).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<MovieDTO> findByGenre(Long genreId, Pageable pageable){

        Page<Movie> page = null;

        Pageable sortedByTitle = PageRequest.of(0, 20, Sort.by("title"));

        if (genreId == 0){
            page = repository.findAll(sortedByTitle);
        }
        else {
            page = repository.findByGenreId(genreId, pageable);
        }

        return page.map(x -> new MovieDTO(x));
    }
}
