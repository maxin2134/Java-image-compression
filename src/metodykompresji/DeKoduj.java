package metodykompresji;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFrame;

public class DeKoduj extends JFrame{
    private  JFrame okno = new JFrame("Obraz");
    private String [] kwak;

DeKoduj(File plik) throws IOException{
    okno.setSize(100,100);
    okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    okno.setVisible(true);
    okno.setLocation(650, 350);
    okno.pack();
    
        /*BufferedReader reader = null;
        try {
        reader = new BufferedReader(new FileReader(plik));
        StringBuilder stringBuilder = new StringBuilder();
        char[] buffer = new char[50];
        try{
        while (reader.read(buffer) != -1) {
        stringBuilder.append(new String(buffer));
        buffer = new char[10];
        }
        reader.close();
        }catch (Exception ex){
        System.out.println(ex);
        }
        String content = stringBuilder.toString();
        System.out.println(content);
        }catch (FileNotFoundException ex) {
        Logger.getLogger(DeKoduj.class.getName()).log(Level.SEVERE, null, ex);
        }
        }*/ 
        
       String sciezka = plik.getAbsolutePath();
       BufferedReader reader = null;
		StringBuilder stringBuilder = new StringBuilder();

		try {
			reader = new BufferedReader(new FileReader(sciezka));
			String line = null;
			String ls = System.getProperty("line.separator");
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append(ls);
			}
			// delete the last ls
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

        String content = stringBuilder.toString();
        System.out.println(content);
        
}}
