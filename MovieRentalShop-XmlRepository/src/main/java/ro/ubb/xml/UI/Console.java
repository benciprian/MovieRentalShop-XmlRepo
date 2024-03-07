package ro.ubb.xml.UI;

import ro.ubb.xml.Domain.Movie;
import ro.ubb.xml.Domain.Validators.ValidatorException;
import ro.ubb.xml.Service.MovieService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Console {

    private MovieService movieService;

    private Scanner scanner;

    public Console(MovieService movieService) {
        this.movieService = movieService;
        this.scanner = new Scanner(System.in);
    }

    private void showMenu() {
        System.out.println("1. Movie rent CRUD");
        System.out.println("0. Exit");
    }

    public void run() {
        while (true) {
            showMenu();
            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    runMovieCrud();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid option!");
                    break;
            }
        }
    }

    private void runMovieCrud() {
        while (true) {
            System.out.println("1. Add movie rental");
            System.out.println("2. View all movies rental");
            System.out.println("3. Update movie rental");
            System.out.println("4. Remove movies rental");
            System.out.println("9. Back");

            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    addMovieRental();
                    break;
                case "2":
                    printMovies();
                    break;
                case "3":
                    updateMovieRental();
                    break;
                case "4":
                    deleteMovieRental();
                    break;
                case "9":
                    return;
                default:
                    System.out.println("Invalid option!");
                    break;
            }
        }
    }
    private void addMovieRental() {
        Movie movie = readMovie();
        if (movie != null) {
            try {
                movieService.addMovie(movie);
                System.out.println("Movie added successfully.");
            } catch (ValidatorException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void printMovies() {
        System.out.println("List of Movies:");
        for (Movie movie : movieService.getAllMovies()) {
            System.out.println(movie);
        }
    }

    private Movie readMovie() {
        System.out.println("Enter movie details:");
        Long id = readLong("Enter ID: ");
        String title = readString("Enter title: ");
        int year = readInt("Enter year: ");
        String genre = readString("Enter genre: ");
        double rentalPrice = readDouble("Enter rental price: ");

        Movie movie = new Movie(id, title, year, genre, rentalPrice);
        return movie;
    }

    private Long readLong(String message) {
        try {
            System.out.print(message);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            return Long.parseLong(bufferedReader.readLine());
        } catch (IOException | NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            return readLong(message);
        }
    }

    private int readInt(String message) {
        try {
            System.out.print(message);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            return Integer.parseInt(bufferedReader.readLine());
        } catch (IOException | NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            return readInt(message);
        }
    }

    private double readDouble(String message) {
        try {
            System.out.print(message);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            return Double.parseDouble(bufferedReader.readLine());
        } catch (IOException | NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            return readDouble(message);
        }
    }

    private String readString(String message) {
        try {
            System.out.print(message);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            return bufferedReader.readLine();
        } catch (IOException e) {
            System.out.println("Invalid input. Please enter a valid string.");
            return readString(message);
        }
    }

    private void updateMovieRental() {
        Long id = readLong("Enter the ID of the movie you want to update: ");
        Movie existingMovie = movieService.getAllMovies()
                .stream()
                .filter(movie -> movie.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (existingMovie == null) {
            System.out.println("Movie not found.");
            return;
        }

        Movie updatedMovie = readMovie();
        updatedMovie.setId(id);

        try {
            movieService.updateMovie(updatedMovie);
            System.out.println("Movie updated successfully.");
        } catch (ValidatorException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    private void deleteMovieRental() {
        Long id = readLong("Enter the ID of the movie you want to delete: ");
        Movie existingMovie = movieService.getAllMovies()
                .stream()
                .filter(movie -> movie.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (existingMovie == null) {
            System.out.println("Movie not found.");
            return;
        }

        try {
            movieService.deleteMovie(id);
            System.out.println("Movie deleted successfully.");
        } catch (ValidatorException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


}
