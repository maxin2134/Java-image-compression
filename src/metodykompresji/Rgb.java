package metodykompresji;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Rgb {
    
    private JFrame okno = new JFrame("RGB");
    private int [][] czerwony;
    private int [][] zielone;
    private int [][] niebieski;
    int licznik = 0;
    BufferedImage image = null;
    BufferedImage obrazCz = null;
    BufferedImage obrazZi = null;
    BufferedImage obrazNi = null;

    
    Rgb (File plik){
    okno.setSize(100,100);
    okno.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
    try {              
        image = ImageIO.read(plik);
    }
    catch (Exception ex) {
        System.out.println(ex);
        }
    int width = image.getWidth(); //szerokosc
    int height = image.getHeight(); // wysokosc
    czerwony = new int [height][width];
    zielone = new int [height][width];
    niebieski = new int [height][width];
    
    obrazCz = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
    obrazZi = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
    obrazNi = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
    
    for(int i = 0; i < width ; i++){
        for (int j = 0; j < height; j++){
                int rgb = image.getRGB(i, j);
                int red = (rgb) & 0x00FF0000;
                int green = (rgb) & 0x0000FF00;
                int blue = (rgb) & 0x000000FF;
                
                czerwony[j][i] = red;
                zielone[j][i] = green;
                niebieski[j][i] = blue;
                                
            }
         }
     
    for(int i = 0; i < width ; i++){
        for (int j = 0; j < height; j++){
                obrazCz.setRGB(i, j, czerwony[j][i]);
                obrazZi.setRGB(i, j, zielone[j][i]);
                obrazNi.setRGB(i, j, niebieski[j][i]);                      
            }
         }
    
    JLabel imageLabel = new JLabel(); 
    imageLabel.setSize(450, 450);
    Image dimg = obrazCz.getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(),Image.SCALE_SMOOTH);
    ImageIcon image = new ImageIcon(dimg);
    imageLabel.setIcon(image);
    
    JLabel imageLabel2 = new JLabel(); 
    imageLabel2.setSize(450, 450);
    Image dimg2 = obrazZi.getScaledInstance(imageLabel2.getWidth(), imageLabel2.getHeight(),Image.SCALE_SMOOTH);
    ImageIcon image2 = new ImageIcon(dimg2);
    imageLabel2.setIcon(image2);
    
    JLabel imageLabel3 = new JLabel(); 
    imageLabel3.setSize(450, 450);
    Image dimg3 = obrazNi.getScaledInstance(imageLabel3.getWidth(), imageLabel3.getHeight(),Image.SCALE_SMOOTH);
    ImageIcon image3 = new ImageIcon(dimg3);
    imageLabel3.setIcon(image3);
    
    okno.add(imageLabel,BorderLayout.WEST);
    okno.add(imageLabel2,BorderLayout.CENTER);
    okno.add(imageLabel3,BorderLayout.EAST);
    okno.setVisible(true);
    okno.pack();
    }
}
