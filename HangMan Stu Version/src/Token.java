import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class Token {
	
	 int x = 0;
	    int y = 159;
	    int width = 40;
	    int height = 45;
	    JLabel label;
	    boolean done = false;
	    int vy= 0;
	    
	public void move(){
        if(done==false){
            y+=vy;
            label.setBounds(x, y, width, height);
            if(y>=650){ 
                done = true;
                vy = 0;
                this.label.setVisible(false);
            }
        }
    }
	 public void falling(){
         String src = new File("").getAbsolutePath()+"/src/";
         ImageIcon panda_b = new ImageIcon(src+"panda2.gif");
         label.setIcon(panda_b);
   }
   
	
	
    public void shift(){
        if(x==230){
            vy=10;
            falling();
        }else{
            x++;
            label.setBounds(x, y, width, height);
        }
    }
}
