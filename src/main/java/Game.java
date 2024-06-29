import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private static int windowCount = 0;
    private static ArrayList<int[]> coordinates = new ArrayList<>();
    private static ArrayList<JFrame> frames = new ArrayList<>();
    private final int COORDINATEXLEFT = 675;
    private final int COORDINATEXRIGHT = 1025;

    public void start() throws InterruptedException {
        loadCoordinates();

        Music.playMusic("src/main/resources/limbo-vol-trim.wav");

        for (int x = 0; x < 8; x++) {
            JFrame frame = new JFrame(String.valueOf(windowCount + 1));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // DO_NOTHING_ON_CLOSE
            frame.setIconImage(new ImageIcon("src/main/resources/redkey.png").getImage());
            frame.setSize(200, 200);
            frame.setLocation(coordinates.get(x)[0], coordinates.get(x)[1]);
            frame.setLayout(new BorderLayout());
            frame.setResizable(false);

            ImageIcon imageIcon = new ImageIcon("src/main/resources/redkey.png");
            Image image = imageIcon.getImage();
            Image scaledImage = image.getScaledInstance(image.getWidth(null) / 2, image.getHeight(null) / 2, Image.SCALE_SMOOTH);
            JLabel label = new JLabel(new ImageIcon(scaledImage));
            label.setOpaque(false);
            frame.add(label, BorderLayout.CENTER);

            frame.setVisible(true);
            frames.add(frame);
            windowCount++;
            Thread.sleep(200);
        }
        Thread.sleep(2000);
        moveWindowsToNewCoordinates();
    }

    private void moveWindowsToNewCoordinates() {
        List<JFrame> framesToMove = new ArrayList<>(frames);

        while (!framesToMove.isEmpty()) {
            JFrame frame = framesToMove.remove(0);
            int[] newCoordinates = pickRandomCoordinates();
            JFrame frameAtNewLocation = getFrameAtLocation(newCoordinates[0], newCoordinates[1]);

            if (frameAtNewLocation != null) {
                swapFrames(frame, frameAtNewLocation);
                framesToMove.remove(frameAtNewLocation);
            } else {
                moveWindowSmoothly(frame, newCoordinates[0], newCoordinates[1], 1000);
            }
        }
    }

    private JFrame getFrameAtLocation(int x, int y) {
        for (JFrame frame : frames) {
            Point location = frame.getLocation();
            if (location.x == x && location.y == y) {
                return frame;
            }
        }
        return null;
    }

    private void swapFrames(JFrame frame1, JFrame frame2) {
        Point location1 = frame1.getLocation();
        Point location2 = frame2.getLocation();

        moveWindowSmoothly(frame1, location2.x, location2.y, 1000);
        moveWindowSmoothly(frame2, location1.x, location1.y, 1000);
    }

    private void moveWindowSmoothly(JFrame frame, int targetX, int targetY, int duration) {
        Point startPoint = frame.getLocation();
        int startX = startPoint.x;
        int startY = startPoint.y;

        int deltaX = targetX - startX;
        int deltaY = targetY - startY;

        int framesPerSecond = 60;
        int totalFrames = (duration / 1000) * framesPerSecond;
        int delay = duration / totalFrames;

        Timer timer = new Timer(delay, null);
        final int[] currentFrame = {0};
        timer.addActionListener(e -> {
            currentFrame[0]++;
            float fraction = (float) currentFrame[0] / totalFrames;
            int newX = startX + Math.round(deltaX * fraction);
            int newY = startY + Math.round(deltaY * fraction);
            frame.setLocation(newX, newY);
            if (currentFrame[0] >= totalFrames) {
                timer.stop();
            }
        });
        timer.start();
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

    private int[] pickRandomCoordinates() {
        return coordinates.get((int) Math.floor(Math.random() * coordinates.size()));
    }

    public static void main(String[] args) throws InterruptedException {
        Game game = new Game();
        game.start();
    }
}
