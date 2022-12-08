package jmail;

import javax.swing.*;

public class GUIVisio  extends JFrame {
    private JTextArea textArea1;
    private JPanel panel1;
    private JLabel LabFrom;
    private JLabel LabSubject;

    public GUIVisio(String from, String subject, String text) {
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setVisible(true);
        LabFrom.setText("Sent from: "+from);
        LabSubject.setText("Subject: "+subject);
        textArea1.setText(text);
    }


}
