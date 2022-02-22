package com.doguedogue.squares;

import javax.swing.JFrame;

/**
 * @author doguedogue
 * created on 25/AGO/2005
 */

public class AppSquares {   
    public static void main(String[] args) {    
        JFrame frame = new JFrame("Squares @doguedogue");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
//        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Animacion(700, 180, 50,0.03f));
        frame.setVisible(true);
    }
}