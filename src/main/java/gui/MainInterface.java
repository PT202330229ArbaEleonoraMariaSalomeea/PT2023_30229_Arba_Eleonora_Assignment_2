package gui;

import javax.swing.*;

public class MainInterface {
    private static JFrame mainInterface = initInterface();

    private static JFrame initInterface() {

        JFrame interf = new JFrame();
        interf.setSize(600, 400);
        interf.setLocationRelativeTo(null);
        interf.setVisible(true);
        interf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        return interf;

    }

    public static void changePanel(JPanel panel, String frameTitle)
    {
        mainInterface.setContentPane(panel);
        mainInterface.setTitle(frameTitle);
        mainInterface.getContentPane().revalidate();
        mainInterface.getContentPane().repaint();
    }
}
