/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author nuwan600
 */
@MessageDriven(mappedName = "jms/Customer", activationConfig = {
     @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class NewMessageBean implements MessageListener {
    
    @Resource
    private MessageDrivenContext mdc;
    @PersistenceContext(unitName = "BookCaterlog-ejbPU")
    private EntityManager em;
    
    
    public NewMessageBean() {
    }
    
    @Override
    public void onMessage(Message message) {
        
        ObjectMessage msg = null;
        try {
            if (message instanceof ObjectMessage) {
                msg = (ObjectMessage) message;
                Customer e = (Customer) msg.getObject();
                save(e);
            }
        } catch (JMSException e) {
            e.printStackTrace();
            mdc.setRollbackOnly();
        } catch (Throwable te) {
            te.printStackTrace();
        }
        
    }

    public void save(Object object) {
        em.persist(object);
    }
}
