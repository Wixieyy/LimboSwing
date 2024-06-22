import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Start {
    private static int windowCount = 0;
    private static ArrayList<int[]> coordinates = new ArrayList<>();
    private static ArrayList<JFrame> frames = new ArrayList<>();
    private final int COORDINATEXLEFT = 650;
    private final int COORDINATEXRIGHT = 1050;

    public void start() throws InterruptedException {
        loadCoordinates();

        for (int x = 0; x < 8; x++) {
            JFrame frame = new JFrame(String.valueOf(windowCount + 1));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //DO_NOTHING_ON_CLOSE
            frame.setIconImage(new ImageIcon("src/main/resources/redkey.png").getImage());
            frame.setSize(200,200);
            frame.setLocation(coordinates.get(x)[0], coordinates.get(x)[1]);
            frame.setLayout(new BorderLayout());
            frame.setResizable(false);

            ImageIcon imageIcon = new ImageIcon("src/main/resources/redkey.png");
            Image image = imageIcon.getImage();
            Image scaledImage = image.getScaledInstance(image.getWidth(null)/2, image.getHeight(null)/2, Image.SCALE_SMOOTH);
            JLabel label = new JLabel(new ImageIcon(scaledImage));
            frame.add(label, BorderLayout.CENTER);

            frame.setVisible(true);
            frames.add(frame);
            windowCount++;
            Thread.sleep(125);
        }
        frames.get(2).setLocation(coordinates.get(5)[0],coordinates.get(5)[1]);
    }

    private void loadCoordinates() {
        coordinates.add(new int[]{COORDINATEXLEFT, 100});
        coordinates.add(new int[]{COORDINATEXRIGHT, 100});

        coordinates.add(new int[]{COORDINATEXLEFT, 325});
        coordinates.add(new int[]{COORDINATEXRIGHT, 325});

        coordinates.add(new int[]{COORDINATEXLEFT, 550});
        coordinates.add(new int[]{COORDINATEXRIGHT, 550});

        coordinates.add(new int[]{COORDINATEXLEFT, 775});
        coordinates.add(new int[]{COORDINATEXRIGHT, 775});
    }
}