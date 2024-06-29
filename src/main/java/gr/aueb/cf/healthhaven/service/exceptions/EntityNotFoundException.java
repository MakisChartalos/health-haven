package gr.aueb.cf.healthhaven.service.exceptions;

/**
 * Exception thrown when an entity with a given ID does not exist in the database.
 */
public class EntityNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public EntityNotFoundException(Class<?> entityClass, Long id) {
        super("Entity " + entityClass.getSimpleName() + " with id " + id + " does not exist");
    }
}