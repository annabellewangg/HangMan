/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 *
 * @author ddavid
 */
public class HangMan extends JPanel implements ActionListener {

    private BufferedImage bg;
    private ImageIcon bg2;
    private ImageIcon plank_bg;
    private ImageIcon panda_b;

    JFrame frame;
    Timer timer;
    boolean visible = false;
    JLabel background, plank;
    JLabel panda;
    JLabel label_score = new JLabel();
    JLabel label_word = new JLabel();
    int count = 450;
    ArrayList<Panda> pandas;
    private ArrayList<String> dic;
    String word = "";
    String src = new File("").getAbsolutePath() + "/src/";
    JTextField b;
    JLabel label_strikes = new JLabel();
    Logic l;
    int score = 0;

    public void setup(JFrame frame) {
        l = new Logic();
        reset(frame);
        b = new JTextField();
        
        b.setHorizontalAlignment(SwingConstants.CENTER);
        label_strikes.setFont(new Font("TimesRoman", Font.BOLD, 36));
        label_strikes.setText(" ");
        label_score.setBounds(0,100,frame.getWidth(),50);
        label_score.setFont(new Font("TimesRoman", Font.BOLD, 36));
        label_score.setText(Integer.toString(score));
        label_score.setHorizontalAlignment(SwingConstants.CENTER);
        
        frame.add(label_score);
        b.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {

            }

            @Override
            public void keyPressed(KeyEvent ke) {
                if(total!=0) return;
                if (ke.getKeyCode() == KeyEvent.VK_ENTER) {

                    //  word = Logic.encrypt(word, word, word);
                    String old = label_word.getText();

                    if (!Logic.guessLetter(word, Logic.withoutSpaces(label_word.getText()), b.getText())) {
                        total = 40;
                        shift();
                        label_strikes.setText(Logic.addStrike(label_strikes.getText(), b.getText()));
                    } 
                    if (Logic.withoutSpaces(label_word.getText()).equals(word)) {
                        for (Panda p : pandas) {

                            if (p.label != null) {
                                p.label.setVisible(false);
                            }
                        }
                        score=Logic.updateScore(score, true);
                        label_score.setText(Integer.toString(score));
                        reset(frame);
                    }
                    label_word.setText(Logic.withSpaces(Logic.encrypt(word, Logic.withoutSpaces(label_word.getText()), b.getText())));

                    b.setText(Logic.clearText());
                    b.requestFocus();
                }
            }

            @Override
            public void keyReleased(KeyEvent ke) {
            }
        });
        JButton guess = new JButton("GUESS");
        label_word.setFont(new Font("TimesRoman", Font.PLAIN, 36));
        guess.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //  word = Logic.encrypt(word, word, word);
                String old = label_word.getText();
                label_word.setText(Logic.withSpaces(Logic.encrypt(word, Logic.withoutSpaces(label_word.getText()), b.getText())));
                if (!Logic.guessLetter(word, Logic.withoutSpaces(label_word.getText()), b.getText())) {
                    total = 40;
                    shift();
                    label_strikes.setText(Logic.addStrike(label_strikes.getText(), b.getText()));
                } else if (Logic.withoutSpaces(label_word.getText()).equals(word)) {
                    for (Panda p : pandas) {
                        if (p.label != null) {
                            p.label.setVisible(false);
                        }
                    }
                    visible=false;
                    reset(frame);
                }
                b.setText(Logic.clearText());
                b.requestFocus();
            }
        });
        b.setBounds(0, 0, (int) (frame.getWidth()), 50);
        
        b.setFocusable(true);
        label_strikes.setBounds(0,350, 200, 50);
        frame.add(label_strikes);
        frame.add(b);
        frame.add(guess);
    }

    public HangMan() {
        frame = new JFrame("Hang Man");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().setBackground(new Color(0, 184, 212));
        frame.add(this);
        frame.setSize(500, 750);
        timer = new Timer(33, this);
        timer.setInitialDelay(990);
        timer.start();
        frame.setLayout(null);

        String src = new File("").getAbsolutePath() + "/src/";
        bg2 = new ImageIcon(src + "water.gif");
        plank_bg = new ImageIcon(src + "wood.png");
        background = (new JLabel(bg2));
        plank = new JLabel(plank_bg);
        background.setBounds(0, 400, 500, 500);
        plank.setBounds(-5, 200, 235, 70);
        panda = new JLabel(panda_b);
        panda.setBounds(0, 129, 100, 100);
        background.setVisible(false);
        frame.add(background);
        frame.add(plank);
        frame.add(panda);

        try {
            bg = ImageIO.read(new File(src + "water2.gif"));
        } catch (Exception e) {
            System.out.println("background file not found");
        }

        dic = new ArrayList<String>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(src + "dict"));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                dic.add(line.trim());
                line = br.readLine();
            }
            String everything = sb.toString();
        } catch (Exception ex) {
            Logger.getLogger(HangMan.class.getName()).log(Level.SEVERE, null, ex);
        }

        setup(frame);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    int num_pandas = 5;

    public void reset(JFrame f) {

        total = 40;
        pandas = new ArrayList<Panda>();
        panda_b = new ImageIcon(src + "panda2.gif");

        for (int i = 0; pandas.size() < 5; i++) {
            pandas.add(new Panda());
            pandas.get(i).setLocation(40 * i, 129);
            f.add(pandas.get(i).label);
        }

        visible = false;
        num_pandas = 5;

        int x = (int) (Math.random() * dic.size());
        String w = dic.get(x);
        dic.remove(x);
        word = w;

        label_word.setText(Logic.withSpaces(Logic.reset(w)));
        label_word.setBounds(0, 270, frame.getWidth(), 50);
        label_word.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(label_word);
        label_strikes.setText(" ");
        frame.add(label_strikes);
        //System.out.println("reset: " + word);
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g.drawString(Integer.toString(score), 250, 100);

        if (visible) {
            g2.drawImage(bg, 0, 0, null);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (visible) {
            count -= 33;
        }
        if (count <= 0) {
            visible = false;
            count = 450;
            background.setVisible(false);
            num_pandas--;
        }
        if (total != 0) {
            shift();
        }
        drop();

    }

    public void drop() {
        for (int i = 0; i < pandas.size(); i++) {
            if (pandas.get(i).done == false) {
                pandas.get(i).move();
                if (pandas.get(i).label.isVisible() == false) {
                    visible = true;
                    count = 450;
                    background.setVisible(true);
                    pandas.remove(i);
                }
            }
        }
        if (num_pandas == 0) {
            reset(frame);
        }
    }

    int total = 40;

    public void shift() {
        for (int i = 0; i < pandas.size(); i++) {
            pandas.get(i).shift();
        }
        total--;
    }

}
