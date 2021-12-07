package swing;

import util.State;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame implements Runnable{

    private final JPanel cont;
    private JPanel buttonPanel;
    private JPanel bannerPanel;
    private CardLayout cl = new CardLayout();
    private GameGUI gui;
    private Logs logs;

    public MainMenu(){
        initBanner();
        initButtons();

        cont = new JPanel();
        JPanel main = new JPanel();
        gui = new GameGUI();
        logs = new Logs();

        cont.setLayout(cl);

        main.add(bannerPanel, BorderLayout.NORTH);
        main.add(buttonPanel, BorderLayout.SOUTH);
        //this.add(main);

        cont.add(main, "1");
        cont.add(gui, "2");
        cont.add(logs, "3");

        cl.show(cont, "1");


        this.add(cont);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(800,800);
        this.setVisible(true);
        this.setTitle("ToldiChess");
    }
    private void initBanner(){
        JLabel banner = new JLabel();
        banner.setIcon(new ImageIcon("pieces/menu.png"));
        bannerPanel = new JPanel();
        bannerPanel.add(banner);

    }

    private void initButtons(){
        JButton newGame = new JButton("New Game");
        newGame.addActionListener(e -> {
            gui.setState(State.GAME);
            cl.show(cont, "2");
        });

        JPanel newGamePanel = new JPanel(new GridLayout(1, 1));
        newGamePanel.setBorder(BorderFactory.createEmptyBorder(40,40,40,40));
        newGamePanel.setSize(new Dimension(400, 110));
        newGamePanel.add(newGame);

        JButton gameLogs = new JButton("Saved Games");
        gameLogs.addActionListener(e -> {
            logs.setState(State.LOG);
            logs.initNames();
            cl.show(cont, "3");

        });

        JPanel gameLogsPanel = new JPanel(new GridLayout(1, 1));
        gameLogsPanel.setBorder(BorderFactory.createEmptyBorder(40,40,40,40));
        gameLogsPanel.setPreferredSize(new Dimension(400, 110));
        gameLogsPanel.add(gameLogs);

        buttonPanel = new JPanel(new GridLayout(2, 1));
        buttonPanel.setPreferredSize(new Dimension(400, 220));
        buttonPanel.add(newGamePanel);
        buttonPanel.add(gameLogsPanel);

    }

    @Override
    public void run() {
        try {
            while (true) {
                while (gui.getState() == State.GAME) {
                    cl.show(cont, "2");
                }
                while (logs.getState() == State.LOG){
                    cl.show(cont, "3");
                }
                cl.show(cont, "1");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        Thread t = new Thread(new MainMenu());
        t.start();
    }
}
