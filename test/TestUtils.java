/*
 * TestUtils
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

public class TestUtils {

    static int counter;

    public static Component getChildNamed(Component parent, String name) {

        // Debug line
        //System.out.println("Class: " + parent.getClass() +
        //		" Name: " + parent.getName());
        if (name.equalsIgnoreCase(parent.getName())) {
            return parent;
        }

        if (parent instanceof Container) {
            Component[] children = (parent instanceof JMenu)
                    ? ((JMenu) parent).getMenuComponents()
                    : ((Container) parent).getComponents();

            for (int i = 0; i < children.length; ++i) {
                Component child = getChildNamed(children[i], name);
                if (child != null) {
                    return child;
                }
            }
        }

        return null;
    }

    public static Component getChildIndexed(
            Component parent, String klass, int index) {
        counter = 0;

        // Step in only owned windows and ignore its components in JFrame
        if (parent instanceof Window) {
            Component[] children = ((Window) parent).getOwnedWindows();

            for (int i = 0; i < children.length; ++i) {
                // take only active windows
                if (children[i] instanceof Window
                        && !((Window) children[i]).isActive()) {
                    continue;
                }

                Component child = getChildIndexedInternal(
                        children[i], klass, index);
                if (child != null) {
                    return child;
                }
            }
        }

        return null;
    }

    private static Component getChildIndexedInternal(
            Component parent, String klass, int index) {

        // Debug line
        //System.out.println("Class: " + parent.getClass() +
        //		" Name: " + parent.getName());
        if (parent.getClass().toString().endsWith(klass)) {
            if (counter == index) {
                return parent;
            }
            ++counter;
        }

        if (parent instanceof Container) {
            Component[] children = (parent instanceof JMenu)
                    ? ((JMenu) parent).getMenuComponents()
                    : ((Container) parent).getComponents();

            for (int i = 0; i < children.length; ++i) {
                Component child = getChildIndexedInternal(
                        children[i], klass, index);
                if (child != null) {
                    return child;
                }
            }
        }

        return null;
    }

    // use JPanel and contenpane
    public static BufferedImage getScreenShot(
            Component component) {

        BufferedImage image = new BufferedImage(
                component.getWidth(),
                component.getHeight(),
                BufferedImage.TYPE_INT_RGB
        );
        // call the Component's paint method, using
        // the Graphics object of the image.
        component.paint(image.getGraphics()); // alternately use .printAll(..)
        return image;
    }

    // use JFrame
    public static BufferedImage getScreenShot(
            JFrame frame) {

        BufferedImage image = new BufferedImage(
                frame.getWidth(),
                frame.getHeight(),
                BufferedImage.TYPE_INT_RGB
        );
        // call the Component's paint method, using
        // the Graphics object of the image.

        // frame.paint(image.getGraphics()); // alternately use .printAll(..)
        frame.printAll(image.getGraphics());
        return image;
    }

    public static void saveImage(Component component, String filename) {

        BufferedImage img = getScreenShot(component);
        component.setVisible(true);
        System.out.println("width: " + img.getWidth());
        System.out.println("height: " + img.getHeight());
//        JOptionPane.showMessageDialog(
//                null,
//                new JLabel(
//                        new ImageIcon(
//                                img.getScaledInstance(
//                                        img.getWidth(null) / 2,
//                                        img.getHeight(null) / 2,
//                                        Image.SCALE_SMOOTH)
//                        )));
        try {
            // write the image as a PNG
            ImageIO.write(
                    img,
                    "png",
                    new File(filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void saveImage(JFrame frame, String filename) {

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel component = (JPanel) frame.getContentPane();
        BufferedImage img = getScreenShot(component);
        component.setVisible(true);
        System.out.println("width: " + img.getWidth());
        System.out.println("height: " + img.getHeight());
//        JOptionPane.showMessageDialog(
//                null,
//                new JLabel(
//                        new ImageIcon(
//                                img.getScaledInstance(
//                                        img.getWidth(null) / 2,
//                                        img.getHeight(null) / 2,
//                                        Image.SCALE_SMOOTH)
//                        )));
        try {
            // write the image as a PNG
            ImageIO.write(
                    img,
                    "png",
                    new File(filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
// vim: set ai sw=4 ts=4:
