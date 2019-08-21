package com.huangxi.springboot.mainFunc;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RectPanel extends JPanel implements MouseListener,
        MouseMotionListener {
    Point pStart = null;
    Point pEnd = null;
    JToggleButton btn=new JToggleButton("button");
    public RectPanel() {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        btn.setLocation(20, 20);
        this.add(btn);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if (pStart != null && pEnd != null) {
            g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_BEVEL, 0, new float[] { 5, 5 }, 0));

            int x = Math.min(pStart.x, pEnd.x);
            int y = Math.min(pStart.y, pEnd.y);
            int width= Math.abs(pStart.x - pEnd.x);
            int height=Math.abs(pStart.y - pEnd.y);
            g2d.drawRect(x, y,width,height);
            g2d.setColor(new Color(125, 125, 125, 40));
            g2d.fillRect(pEnd.x, pEnd.y, pStart.x - pEnd.x, pStart.y -
                    pEnd.y);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400, 400);
                frame.setContentPane(new RectPanel());
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        pStart = e.getPoint();

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Rectangle r=new Rectangle(Math.min(pStart.x, pEnd.x),Math.min(pStart.y, pEnd.y), Math.abs(pStart.x - pEnd.x),
                Math.abs(pStart.y - pEnd.y));
        if(r.contains(btn.getBounds())){
            btn.setSelected(!btn.isSelected());
        }
        pStart = null;
        pEnd = null;
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        pEnd = e.getPoint();
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}