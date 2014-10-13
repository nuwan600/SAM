/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dell
 */
@Stateless
public class BookEntityFacade extends AbstractFacade<BookEntity> {
    @PersistenceContext(unitName = "BookCatalougeApp-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BookEntityFacade() {
        super(BookEntity.class);
    }
    
}
