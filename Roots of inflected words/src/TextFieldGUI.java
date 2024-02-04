
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class TextFieldGUI implements ActionListener{
    JTextArea tf1,tf2;
    JButton b1;

    TextFieldGUI(){
        JFrame f= new JFrame();
//        f = new JFrame();
        tf1 = new JTextArea();
        tf2 = new JTextArea();

        JScrollPane scrollPane1 = new JScrollPane(tf1);
        JScrollPane scrollPane2 = new JScrollPane(tf2);

        scrollPane1.setBounds(50, 50, 600, 250);
        tf1.setFont(new Font("Arial", Font.PLAIN, 40));
        scrollPane2.setBounds(50, 400, 600, 250);
        tf2.setFont(new Font("Arial", Font.PLAIN, 40));

        b1 = new JButton("Stem inflected words");
        b1.setBounds(100, 320, 400, 50);

        b1.addActionListener(this);

        // Add components to the JFrame
        f.add(scrollPane1, BorderLayout.CENTER);
        f.add(scrollPane2, BorderLayout.CENTER);
        f.add(b1, BorderLayout.SOUTH);

        f.setSize(700, 750);
        f.setLayout(null);
        f.setVisible(true);
    }
    public void actionPerformed(ActionEvent e) {
        String s1=tf1.getText();
        String[] input1= s1.split(" ");
        String out = "";
        for (String i : input1){
            Stemming obj = new Stemming();
            String[] t = obj.stemmed_text(i);
            String temp = t[0] + " --> " + t[1] + "\n";

            out += temp;
        }
        tf2.setText(out);

    }
    public static void main(String[] args) {
        new TextFieldGUI();

    } }
