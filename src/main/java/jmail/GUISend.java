package jmail;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class GUISend extends JFrame {
    private JTextArea TFCorp;
    private JTextField TFObject;
    private JTextField TFDesti;
    private JButton ButtEnvoyer;
    private JLabel LabTo;
    private JLabel LabObjet;
    private JButton ButtAttach;
    private JLabel LabFile;
    private JPanel Sendmail;

    private String username;
    private String pwd;
    private String path;

    public GUISend (String username,String pwd){
        this.username=username;
        this.pwd = pwd;
        setContentPane(Sendmail);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setVisible(true);

        ButtAttach.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Let the user pick a file and store the path in a string
                JFileChooser chooser = new JFileChooser();
                chooser.showOpenDialog(null);
                File f = chooser.getSelectedFile();
                String filename = f.getAbsolutePath();
                path=filename.replace("\\","\\\\");
                LabFile.setText("Chemin Fichier: "+path);


            }
        });
        ButtEnvoyer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SendMail sm =new SendMail(username,pwd);
                if(path==null)
                {
                    sm.EnvoiEmail(username,TFDesti.getText(),TFObject.getText(),TFCorp.getText());
                }else{
                    sm.EnvoiEmailComplex(username,TFDesti.getText(),TFObject.getText(),TFCorp.getText(),path);
                }

                setVisible(false);

            }
        });
    }




}
