package gui;

import BusinessLogic.SimulationManager;

import javax.swing.*;

public class GuiAfisare {
    private JPanel panel1;
    private JTextArea Afis;


    public GuiAfisare(int Q, int N, int lowA, int highA, int lowS,int highS, int timeLimit ) throws InterruptedException {



        SimulationManager sim1 = new SimulationManager(Q,N,lowA,highA,lowS,highS,timeLimit);
        Thread t = new Thread(sim1);
        t.start();
        t.join();
        Afis.setText(sim1.getRezultat().toString());
        MainInterface.changePanel(panel1, "panel1");



    }
}
