import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

public class FractalExplorer {

    private int displaySize;
    private JImageDisplay imageDisplay;
    private FractalGenerator fractalGenerator;
    private Rectangle2D.Double range;

    public FractalExplorer(int displaySize) {
        this.displaySize = displaySize;
        this.imageDisplay = new JImageDisplay(displaySize, displaySize);
        this.range = new Rectangle2D.Double(-2, -1.5, 3, 3);
        this.fractalGenerator = new MandelbrotFractal();
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Fractal Explorer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = frame.getContentPane();

        JButton clearButton = new JButton("Сброс изображения");
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fractalGenerator.getInitialRange(range);
                drawFractal();
            }
        });

        contentPane.setLayout(new BorderLayout());
        contentPane.add(imageDisplay, BorderLayout.CENTER);
        contentPane.add(clearButton, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void drawFractal() {
        System.out.println("drawFractal() start");
        for (int x = 0; x < imageDisplay.getWidth(); x++) {
            for (int y = 0; y < imageDisplay.getHeight(); y++) {
                double xCoord = FractalGenerator.getCoord(range.x,
                                                          range.x + range.width,
                                                          displaySize,
                                                          x);
                double yCoord = FractalGenerator.getCoord(range.y,
                                                          range.y + range.height,
                                                          displaySize,
                                                          y);
                int numIterations = fractalGenerator.numIterations(xCoord, yCoord);
                if (numIterations == -1) {
                    imageDisplay.drawPixel(x, y, 0);
                } else {
                    float hue = 0.7f + (float)numIterations/200f;
                    imageDisplay.drawPixel(x, y, Color.HSBtoRGB(hue, 1f, 1f));
                }
            }
        }
        imageDisplay.repaint();
        System.out.println("drawFractal() done");
    }

    public static void main(String[] args) {
        FractalExplorer fractalExplorer = new FractalExplorer(800);
        fractalExplorer.createAndShowGUI();
        fractalExplorer.drawFractal();
    }
}
