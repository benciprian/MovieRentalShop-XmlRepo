package ro.ubb.xml.Domain;

import java.util.Objects;

public class Movie extends BaseEntity<Long> {
    private String title;
    private int year;
    private String genre;
    private double rentalPrice;

    public Movie(Long id, String title, int year, String genre, double rentalPrice) {
        super(id);
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.rentalPrice = rentalPrice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(double rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return year == movie.year && Double.compare(rentalPrice, movie.rentalPrice) == 0 && Objects.equals(title, movie.title) && Objects.equals(genre, movie.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, year, genre, rentalPrice);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + getId() +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", genre='" + genre + '\'' +
                ", rentalPrice=" + rentalPrice +
                '}';
    }

}
