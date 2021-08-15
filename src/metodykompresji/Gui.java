package metodykompresji;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class Gui extends JFrame {
    private  JFrame okno = new JFrame("Otworz zdjecie");
    private JButton otworz = new JButton("Otworz zdjecie");
    private JFileChooser plikWybierz = new JFileChooser();
    Gui(){
    okno.setSize(100,100);
    okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    okno.add(otworz);
    okno.setVisible(true);
    okno.setLocation(650, 350);
    okno.pack();
    otworz.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e) {
        
    if (plikWybierz.showOpenDialog(Gui.this) == JFileChooser.APPROVE_OPTION){
                   try {
                      File plik = plikWybierz.getSelectedFile();
                      WyswietlObraz wyswietl = new WyswietlObraz(plik);
                   } catch (Exception ex) {
                       System.out.println(ex);
                   }
               }
        }
    });
    }
}
