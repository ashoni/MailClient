package demail.model.entity;

/**
 *
 * @author Анна
 */
public interface Persistent<T> {
    T getId();
    void setId(T id);
}
