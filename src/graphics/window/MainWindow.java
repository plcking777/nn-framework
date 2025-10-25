package graphics.window;

import com.sun.tools.javac.Main;
import neuralnetwork.NeuralNetwork;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class MainWindow extends JFrame {

    private BufferedImage image;
    private JProgressBar progressBar;
    private JSlider slider;

    private int imageWidth;
    private int imageHeight;
    private int imageUpscale;

    private NeuralNetwork nn;

    public MainWindow(NeuralNetwork nn, int width, int height, int imageWidth, int imageHeight, int imageUpscale) {
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.imageUpscale = imageUpscale;
        this.nn = nn;

        setTitle("Test window");
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        slider = new JSlider(0, 100, 0);
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        float max = 2f;
        float min = 0f;

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                double value = (slider.getValue() / 100.0f) * (max + min) + min;
                System.out.println("value: " + value);
                nn.setInput(List.of(value));
                nn.forward();
                setImage(nn.getOutput().getRow(0));
            }
        });
        add(slider, BorderLayout.EAST);



        getContentPane().setBackground(Color.DARK_GRAY);
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        add(progressBar, BorderLayout.SOUTH);

        image = new BufferedImage(imageWidth * imageUpscale, imageHeight * imageUpscale, BufferedImage.TYPE_INT_ARGB);

        MainDrawPanel panel = new MainDrawPanel();
        panel.setBackground(Color.DARK_GRAY);
        panel.setOpaque(true);
        add(panel);
    }

    private class MainDrawPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, null);
        }
    }

    public void setImage(double[] data) {

        for (int i = 0; i < data.length; i++) {

            int grayscaleValue = (int) (data[i] * 255);
            int color = (0xFF << 24) | (grayscaleValue << 16) | (grayscaleValue << 8) | grayscaleValue;

            int x = i % this.imageWidth;
            int y = (i / this.imageWidth);

            for (int a = 0; a < this.imageUpscale; a++) {
                for (int b = 0; b < this.imageUpscale; b++) {
                    image.setRGB((x*this.imageUpscale)+a, (y*this.imageUpscale)+b, color);
                }
            }
        }
        repaint();
    }

    /**
     * @param x value between 0 - 1
     */
    public void setProgressBar(float x) {
        this.progressBar.setValue((int)(x * 100));
    }
}
