package org.example.panels;

import org.example.helper.CircleButton;
import org.example.helper.CircleButtonSerialized;
import org.example.helper.ConnectingLine;
import org.example.helper.GameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;
import java.util.Random;

public class DrawingPanel extends JPanel {
    public static CircleButton selectedDot;
    public StaticPanel staticPanel;

    private Point mousePosition = new Point(0, 0);
    private Random random = new Random();

    public DrawingPanel(StaticPanel staticPanel) {
        setBackground(Color.WHITE);
        setOpaque(false);
        setLayout(null);

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mousePosition = e.getPoint();
                repaint();
            }
        });
        this.staticPanel = staticPanel;
    }

    public void generateDots(int num) {
        GameManager.allCircleButtons.clear();
        for (int i = 0; i < num; i++) {
            int x = random.nextInt(getWidth() - 20) + 10;
            int y = random.nextInt(getHeight() - 20) + 10;

            addButton(x, y, 10);
        }
        revalidate();
        repaint();
    }

    public void generateDots(List<CircleButtonSerialized> savedButs) {
        GameManager.allCircleButtons.clear();
        for (var but : savedButs) {
            int x = but.getPosX();
            int y = but.getPosY();

            addButton(x, y, but.getDiameter());

        }
        revalidate();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (selectedDot != null && !GameManager.gameOver) {
            int centerX = selectedDot.getX() + selectedDot.getWidth() / 2;
            int centerY = selectedDot.getY() + selectedDot.getHeight() / 2;

            g.setColor(GameManager.turn % 2 == 0 ? Color.BLUE : Color.RED);
            g.drawLine(centerX, centerY, mousePosition.x, mousePosition.y);
            return;
        }

    }



    public void addButton(int x, int y, int diameter)
    {
        CircleButton dot = new CircleButton(x, y, diameter);
        dot.setBackground(Color.gray);
        dot.setBounds(x, y, diameter, diameter);
        dot.addActionListener(e ->
            {
                if(selectedDot == null)
                    selectedDot = dot;
                else
                {
                    if(!selectedDot.equals(dot) && !GameManager.hasLine(selectedDot, dot))
                    {
                        if(GameManager.gameOver) return;
                        ConnectingLine newLine = new ConnectingLine(selectedDot.getX(), selectedDot.getY(), dot.getX(), dot.getY(), diameter/2,GameManager.turn % 2 == 0);
                        GameManager.addLine(newLine, false);

                    }
                    selectedDot = null;
                }
                repaint();
            }
        );
        GameManager.allCircleButtons.add(dot);
        GameManager.staticPanel.imprintButton(dot);
        add(dot);
    }

}
