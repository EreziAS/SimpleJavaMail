package jmail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class GUIMail extends JFrame {
    private JPanel MainPanel;
    private JTextField TFUsername;
    private JPasswordField TFPassword;
    private JButton ButtonConnect;
    private JButton ButtonVisionner;
    private JButton ButtonNew;
    private JButton ButtonDelete;
    private JButton ButtonQuit;
    private JTable table1;
    private JButton LISTMTAButton;
    private DefaultTableModel model;
    private RetrieveMail rm;

    public GUIMail() {
        model = new DefaultTableModel(new Object[]{"Id","From", "Subject"}, 0);
        table1.setModel(model);
        setContentPane(MainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);




        ButtonConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setRowCount(0);
                String Username = TFUsername.getText();
                String Password = String.valueOf(TFPassword.getPassword());

                rm = new RetrieveMail(model, Username, Password);
                rm.Retrieve();

                Polling p = new Polling(rm);
                p.start();
            }
        });
        ButtonVisionner.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table1.getSelectedRow();
                System.out.println(row);
                Message message=rm.RetrieveMessage(row);



                try {
                    System.out.println("Type " + message.getContentType());
                    //Extract the string from the multipart
                    String text = "";
                    if (message.getContentType().contains("multipart")) {
                        Multipart mp = (Multipart) message.getContent();
                        System.out.println("Nombre de Multipart :" + mp.getCount());
                        for (int i = 0; i < mp.getCount(); i++) {
                            Part p = mp.getBodyPart(i);
                            String d=p.getDisposition();
                            System.out.println("Disposition num: " + i + " || Type: " + d);
                            if (p.isMimeType("text/plain")) {
                                text = (String)p.getContent();
                            }
                            if(d!=null && d.equalsIgnoreCase("ATTACHMENT")){
                                InputStream is = p.getInputStream();
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                int c;
                                while((c = is.read()) != -1) {
                                    baos.write(c);
                                }
                                baos.flush();
                                String filename = p.getFileName();
                                FileOutputStream fos = new FileOutputStream("F:\\\\Attach\\"+filename);
                                baos.writeTo(fos);
                                fos.close();
                                System.out.println("Attachment: " + filename);
                            }
                        }
                    } else if (message.getContentType().contains("text/plain")) {
                        text = message.getContent().toString();
                    }

                    GUIVisio guiVisio = new GUIVisio(((InternetAddress)((Address)(message.getFrom()[0]))).getAddress(),message.getSubject(),text);
                } catch (MessagingException e1) {
                    e1.printStackTrace();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        ButtonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table1.getSelectedRow();
                System.out.println(row);
                rm.DeleteMessage(row);
                model.setRowCount(0);
                rm.Retrieve();
            }
        });
        ButtonNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = TFUsername.getText();
                String pwd = String.valueOf(TFPassword.getPassword());

                GUISend guiSend = new GUISend(username,pwd);
            }
        });
        ButtonQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        LISTMTAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table1.getSelectedRow();
                System.out.println(row);
                Message message=rm.RetrieveMessage(row);

                try {
                    GUIheaders guiheaders = new GUIheaders(message);
                } catch (MessagingException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });
    }

    public static void main(String[] args) {

        GUIMail guiMail = new GUIMail();
    }
}
