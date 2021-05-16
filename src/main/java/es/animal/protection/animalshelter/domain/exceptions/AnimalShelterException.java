package es.animal.protection.animalshelter.domain.exceptions;

public class AnimalShelterException extends RuntimeException {

    private static final String DESCRIPTION = "AnimalShelterException";

    public AnimalShelterException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }
}
