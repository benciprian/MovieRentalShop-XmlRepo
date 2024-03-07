package ro.ubb.xml;

import ro.ubb.xml.Domain.Movie;
import ro.ubb.xml.Domain.Validators.MovieValidator;
import ro.ubb.xml.Domain.Validators.Validator;
import ro.ubb.xml.Repository.MovieFileXmlRepository;
import ro.ubb.xml.Repository.Repository;
import ro.ubb.xml.Service.MovieService;
import ro.ubb.xml.UI.Console;

public class Main {
    public static void main(String[] args) {
        String filePath = "./data/MovieRentalFile.xml"; // Check the file path

        Validator<Movie> movieValidator = new MovieValidator();
        Repository<Long, Movie> movieRepository = new MovieFileXmlRepository(movieValidator, filePath);

        MovieService movieService = new MovieService(movieRepository);
        Console console = new Console(movieService);
        console.run();

        System.out.println("bye");
    }
}

