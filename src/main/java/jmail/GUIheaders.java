package jmail;

import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.swing.*;
import java.util.Enumeration;

public class GUIheaders extends JFrame {
    private JPanel Panelheader;
    private DefaultListModel model;
    private JList list1;

    private Message message;

    public GUIheaders(Message m) throws MessagingException {
        message = m;
        model = new DefaultListModel();
        list1.setModel(model);
        ShowHeaders();
        setContentPane(Panelheader);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);
    }

    public void ShowHeaders() throws MessagingException {
        Enumeration e = message.getAllHeaders();
        Header h = (Header) e.nextElement();
        while (e.hasMoreElements()) {
            model.addElement(h.getName() + " ---> " + h.getValue());
            h = (Header) e.nextElement();
        }
    }
}
