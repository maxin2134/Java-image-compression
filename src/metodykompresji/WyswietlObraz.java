package metodykompresji;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class WyswietlObraz extends JFrame {
    private JFrame okno = new JFrame("Zdjecie");
    private JButton zapisz = new JButton("Zapisz");
    private JButton kolory = new JButton("R,G,B");
    private JButton kodowanie = new JButton("kodowanie");
    private JButton dekodowanie = new JButton("Dekodowanie");
    private JPanel buttonPane = new JPanel();
    private int [][] tab;
    private int [] tab2;
    private JFileChooser plikWybierz = new JFileChooser();

WyswietlObraz(File plik){
    okno.setSize(600,600);
    okno.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
    BufferedImage img = null;
    JLabel imageLabel = new JLabel(); 
    imageLabel.setSize(600, 600);
try {
    img = ImageIO.read(plik);

} catch (IOException e) {
    e.printStackTrace();
}
    Image dimg = img.getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(),Image.SCALE_SMOOTH);
    ImageIcon image = new ImageIcon(dimg);
    imageLabel.setIcon(image);
    
    ImageIcon image2 = new ImageIcon(dimg);
    JLabel imageLabel2 = new JLabel(); 
    imageLabel2.setIcon(image2);
    
    JScrollPane scrollPane = new JScrollPane(imageLabel);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    okno.add(scrollPane, BorderLayout.WEST);
    
    JScrollPane scrollPane2 = new JScrollPane(imageLabel2);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    okno.add(scrollPane2, BorderLayout.CENTER);
    
    
    
    buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.Y_AXIS));
    buttonPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    buttonPane.add(Box.createVerticalBox());
    buttonPane.add(zapisz);
    buttonPane.add(Box.createRigidArea(new Dimension(10, 10)));
    buttonPane.add(kolory);
    buttonPane.add(Box.createRigidArea(new Dimension(10, 10)));
    buttonPane.add(kodowanie);
    buttonPane.add(Box.createRigidArea(new Dimension(10, 10)));
    buttonPane.add(dekodowanie);
    Container contentPane = getContentPane();
    contentPane.add(buttonPane);
    
    okno.setVisible(true);
    okno.pack();
    okno.add(contentPane, BorderLayout.EAST);

    
    
    zapisz.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e) {
            BufferedImage image = null;
           if (plikWybierz.showSaveDialog(WyswietlObraz.this) == JFileChooser.APPROVE_OPTION){
                   try {
                       
                      image = ImageIO.read(plik);}
                   catch (Exception ex) {
                       System.out.println(ex);
                   }
                      Raster raster = image.getRaster();
                      int width = image.getWidth(); //szerokosc
                      int height = image.getHeight(); // wysokosc
                      tab = new int [height][width];
                      int licznik = 0;
                      
                      for(int i = 0; i < width ; i++){
                          for (int j = 0; j < height; j++){
                              tab[j][i] = raster.getSample(i, j, 0);
                              licznik++;
                          }
                      }
                      
                      tab2 = new int[licznik];
                      licznik = 0;
                      
                      for(int i = 0; i < height ; i++){
                          for (int j = 0; j < width; j++){
                              tab2[licznik] = tab[i][j];
                              licznik++;
                          }
                      }
                      
            BufferedImage autImg = new BufferedImage(width,height,BufferedImage.TYPE_BYTE_GRAY);
            WritableRaster autRast = autImg.getRaster();
            autRast.setSamples(0, 0, width, height, 0, tab2);
            String path=plikWybierz.getSelectedFile().getAbsolutePath();
                try {
                    ImageIO.write(autImg, "bmp", new File(path + ".bmp"));
                } catch (IOException ex) {
                    Logger.getLogger(WyswietlObraz.class.getName()).log(Level.SEVERE, null, ex);
                }
            
    
               }}
    });
    
    
    kolory.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e) {
            Rgb rgb = new Rgb(plik);
        }
    });
    
    kodowanie.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e) {
            Koduj koduj = new Koduj(plik);
        }
    });
    
    dekodowanie.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e) {
            File dekompresor = null;
            if (plikWybierz.showOpenDialog(WyswietlObraz.this) == JFileChooser.APPROVE_OPTION){
                   try {
                        dekompresor = plikWybierz.getSelectedFile();
                        DeKoduj dekoduj = new DeKoduj(dekompresor);
                   } catch (Exception ex) {
                       System.out.println(ex);
                   }
               }
            
        }
    });
}      
}