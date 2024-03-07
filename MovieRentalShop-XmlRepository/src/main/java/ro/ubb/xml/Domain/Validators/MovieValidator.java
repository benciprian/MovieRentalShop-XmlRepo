package ro.ubb.xml.Domain.Validators;

import ro.ubb.xml.Domain.Movie;

public class MovieValidator implements Validator<Movie>{
    @Override
    public void validate(Movie entity) throws ValidatorException {
        //TODO validate movie

        // Validate the title (contains letters)
        if (entity.getTitle() == null || !entity.getTitle().matches(".*[a-zA-Z].*")) {
            throw new ValidatorException("Title must contain letters.");
        }

        // Validate the year (contains 4 digits)
        if (entity.getYear() < 1000 || entity.getYear() > 9999) {
            throw new ValidatorException("Year must contain 4 digits.");
        }
    }
}

