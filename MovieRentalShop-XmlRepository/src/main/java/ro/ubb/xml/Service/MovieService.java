package ro.ubb.xml.Service;

import ro.ubb.xml.Domain.Movie;
import ro.ubb.xml.Domain.Validators.ValidatorException;
import ro.ubb.xml.Repository.Repository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MovieService {
    private Repository<Long, Movie> repository;

    public MovieService(Repository<Long, Movie> repository) {
        this.repository = repository;
    }
    public void addMovie(Movie movie) throws ValidatorException {
        repository.save(movie);
    }

    public Set<Movie> getAllMovies() {
        Iterable<Movie> movies = repository.findAll();
        return StreamSupport.stream(movies.spliterator(), false).collect(Collectors.toSet());
    }
    public void updateMovie(Movie movie) throws ValidatorException {
        repository.update(movie);
    }
    public void deleteMovie(Long id) throws ValidatorException {
        repository.delete(id);
    }
}
