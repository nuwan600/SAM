/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ejb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 *
 * @author dell
 */
@MessageDriven(mappedName = "jms/NewMessageAuthor", activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class NewMessageAuthor implements MessageListener {
    
    public NewMessageAuthor() {
    }
    
    @Override
    public void onMessage(Message message) {
    }
    
}
