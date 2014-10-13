/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.customerapp.ejb;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.customerapp.entity.Customer;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.persistence.Query;
/**
 *
 * @author nuwan600
 */
@Stateless
@LocalBean

public class CustomerSession {
    
    
    @Resource(mappedName = "jms/NewMessage12")
    private Queue newMessage12;
    @Resource(mappedName = "jms/NewMessage12Factory")
    private ConnectionFactory newMessage12Factory;
    @PersistenceContext(unitName = "ejb_test_app-ejbPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public List<Customer> retrieve() {
        Query query = em.createNamedQuery("Customer.findAll");
        return query.getResultList();
    }

    public Customer Update(Customer thecustomer) {
       Customer updated = em.merge(thecustomer);
       try
        {
            sendJMSMessageToNewMessage12(updated);
            //sendJMSMessageToNotificationQueue(updated);
        } catch (JMSException ex) {
            Logger.getLogger(CustomerSession.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Customer updated in CustomerSessionBean!");
        return updated;
       
        
        //return em.merge(thecustomer);
    }

    private Message createJMSMessageForjmsNewMessage12(Session session, Object messageData) throws JMSException {
        // TODO create and populate message to send
        //TextMessage tm = session.createTextMessage();
        //tm.setText(messageData.toString());
        ObjectMessage tm = session.createObjectMessage();
        tm.setObject((Serializable) messageData);
        return tm;
    }

    private void sendJMSMessageToNewMessage12(Object messageData) throws JMSException {
        Connection connection = null;
        Session session = null;
        try {
            connection = newMessage12Factory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer messageProducer = session.createProducer(newMessage12);
            messageProducer.send(createJMSMessageForjmsNewMessage12(session, messageData));
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                    Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Cannot close session", e);
                }
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
    
    


    
    
    
}
