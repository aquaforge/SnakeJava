package pkg.firstJavaSnake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;


class HelloComponent2 extends JComponent implements MouseMotionListener
{

    String theMessage;
    int messageX = 125, messageY = 95; // Координаты сообщения
    public HelloComponent2( String message ) {
        theMessage = message;
        addMouseMotionListener(this);
    }

    public void paintComponent( Graphics g ) {
        //g.drawString( theMessage, messageX, messageY );
        g.drawRect(messageX,messageY,15,30);
    }
    public void mouseDragged(MouseEvent e) {
        messageX = e.getX();
        messageY = e.getY();
        repaint();
    }
    public void mouseMoved(MouseEvent e) { }

}
