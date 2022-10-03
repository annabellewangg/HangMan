/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author domingodavid
 */
public class Panda extends Token{
   
        
    public Panda(){
        String src = new File("").getAbsolutePath()+"/src/";
        ImageIcon panda_b = new ImageIcon(src+"idle5.gif");
        label = new JLabel(panda_b);
    }
    
    public void falling(){
          String src = new File("").getAbsolutePath()+"/src/";
          ImageIcon panda_b = new ImageIcon(src+"panda2.gif");
          label.setIcon(panda_b);
    }
    
    public void setLocation(int x, int y){
        this.x = x;
        label.setBounds(x, 130, width, height);
    }
    
    
}
