/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demail.model.dao;

import demail.model.entity.Persistent;

/**
 *
 * @author Анна
 */
public interface DAO<K,T extends Persistent<K>> {
    void create(T persistent);

    void update(T persistent);
    
    void delete(K id);

    T get(K id);
}
