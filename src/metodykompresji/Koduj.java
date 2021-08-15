package metodykompresji;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Koduj {
    
    private int elem = 0;
    private JFrame okno = new JFrame("RLE");
    private JButton zapisz = new JButton("Zapisz");
    private BufferedImage image = null;
    private String tekstMono = "";
    private String znacznikCz = "---------------------------------RED--------------------------------";
    private String tablicaCzPiks = "";
    private String znacznikZi = "--------------------------------GREEN--------------------------------";
    private String tablicaZiPiks = "";
    private String znacznikNi = "--------------------------------BLUE--------------------------------";
    private String tablicaNiPiks = "";
    private int czarnyBit,czerwonyBit,zielonyBit,niebieskiBit,tmpW,tmpR,tmpG,tmpB;
    private byte licznikW = 1, licznikR = 1, licznikG = 1, licznikB = 1;

    Koduj(File plik){
    okno.setSize(100,100);
    okno.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    okno.add(zapisz,BorderLayout.EAST);
    okno.setVisible(true);
    // sprawdz jaki to obraz
        try {
            image = ImageIO.read(plik);
            
            Raster ras = image.getRaster();
            
            elem = ras.getNumDataElements();
            
        } catch (IOException ex) {
            Logger.getLogger(Koduj.class.getName()).log(Level.SEVERE, null, ex);
        }
        // niezależnie jaki pobierzemy kolor z konkretnego piksela bedzie miał on indentyczna liczbe jak pozostale wartosci piksela grey = 3/(r+g+b) //
    if (elem == 1){
        int width = image.getWidth(); //szerokosc
        int height = image.getHeight(); // wysokosc
        for(int i = 0; i < width ; i++){
            for (int j = 0; j < height; j++){
                int rgb = image.getRGB(i, j);
                int czarPiksObrazu = (rgb) & 0x000000FF;
                                
                if (i == 0 && j == 0)
                    czarnyBit = czarPiksObrazu;
                else
                {
                    tmpW = czarnyBit;
                    czarnyBit = czarPiksObrazu;
                    
                       if ( tmpW == czarnyBit ) { licznikW++; }else 
                    {
                       tekstMono += tmpW + ":" + licznikW + "|";
                       licznikW = 1;
                    }
                }
                }
            }
}
    
    if (elem == 3 || elem == 4){
    // kompresja rle dla obrazu RGB
    int width = image.getWidth(); //szerokosc
        int height = image.getHeight(); // wysokosc
        for(int i = 0; i < width ; i++){
            for (int j = 0; j < height; j++){
                int rgb = image.getRGB(i, j);
                int czerwPiksObraz = (rgb) & 0x00FF0000 >> 16;
                int zielPiksObraz = (rgb) & 0x0000FF00 >> 8;
                int niebPiksObraz = (rgb) & 0x000000FF;
                
                if (i == 0 && j == 0)
                {
                    czerwonyBit = czerwPiksObraz;
                    zielonyBit = zielPiksObraz;
                    niebieskiBit = niebPiksObraz;
                }
                else
                {
                   tmpR = czerwonyBit;
                   tmpG = zielonyBit;
                   tmpB = niebieskiBit;
                    
                   czerwonyBit = czerwPiksObraz;
                   zielonyBit = zielPiksObraz;
                   niebieskiBit = niebPiksObraz;                   
                
                   if (tmpR == czerwonyBit ) {
                       licznikR++;
                       
                       if (licznikR == 127){
                            tablicaCzPiks += tmpR + ":" + ( licznikR ) + "|";
                            licznikW = 1;
                        }
                       
                   } else{
                       tablicaCzPiks += tmpR + ":" + ( licznikR ) + "|";
                       licznikR = 1;
                   }
                   
                   if (tmpG == zielonyBit ) {
                       licznikG++;
                       
                        if (licznikG == 127){
                            tablicaZiPiks += tmpG + ":" + ( licznikG ) + "|";
                            licznikG = 1;
                        }}
                   else 
                   {
                       tablicaZiPiks += tmpG + ":" + ( licznikG ) + "|";
                       licznikG = 1;
                   }
                   if (tmpB == niebieskiBit ) {
                       licznikB++;
                       
                       if (licznikB == 127){
                            tablicaNiPiks += tmpG + ":" + ( licznikB ) + "|";
                            licznikB = 1;
                        }
                    } else 
                       
                   {
                       tablicaNiPiks += tmpB + ":" + ( licznikB ) + "|";
                       licznikB = 1;
                   }
                }
            }
        }
    }
    
    
        
    zapisz.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e) {
        BufferedWriter writer = null;
            try {
                if (elem == 1)
                {
                writer = new BufferedWriter (new FileWriter("c:\\liczby2.txt"));
                writer.write(tekstMono);
                }else 
                {
                    writer = new BufferedWriter (new FileWriter("c:\\liczbyRGB.txt"));
                    writer.write(znacznikCz);
                    writer.newLine();
                    writer.write (tablicaCzPiks);
                    writer.newLine();
                    writer.write(znacznikZi);
                    writer.newLine();
                    writer.write(tablicaZiPiks);
                    writer.newLine();
                    writer.write(znacznikNi);
                    writer.newLine();
                    writer.write(tablicaNiPiks);
                }
            } catch (IOException ex) {
                
            }
        }
    });
}
}