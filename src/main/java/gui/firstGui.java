package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class firstGui {
    private JTextField NrQueues;
    private JTextField NrClients;
    private JTextField SimulationTime;
    private JTextField MinArrival;
    private JTextField MaxArrival;
    private JTextField MinService;
    private JTextField MaxService;
    private JButton startButton;
    private JPanel firstGuiPanel;

    public firstGui() {

    MainInterface.changePanel(firstGuiPanel, "FirstGuiPanel");

    startButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                GuiAfisare guiAfs = new GuiAfisare(Integer.parseInt(NrQueues.getText()), Integer.parseInt(NrClients.getText()),Integer.parseInt(MinArrival.getText()),Integer.parseInt(MaxArrival.getText()),Integer.parseInt(MinService.getText()),Integer.parseInt(MaxService.getText()), Integer.parseInt(SimulationTime.getText()));
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }

        }
    });
}
}
