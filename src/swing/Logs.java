package swing;

import util.LogComparator;
import util.Logger;
import util.State;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class Logs extends JPanel {
    private State state = State.MENU;
    private JComboBox games;
    private JPanel panel = new JPanel(), panel2 = new JPanel();
    private ArrayList<Logger> logs = new ArrayList<>();
    private String[] files;
    private JTable table = new JTable();
    private JScrollPane scroll = new JScrollPane(table);

    public Logs(){
        initNames();
        initComboBox();
        this.setLayout(new BorderLayout());
        this.setSize(800,800);

        panel2.add(scroll);

        this.add(panel, BorderLayout.PAGE_START);




        this.setVisible(true);
    }

    private void initComboBox(){
        JButton backToMenu = new JButton("Back to Menu");
        backToMenu.addActionListener(e -> state = State.MENU);


        JComboBox<String> games = new JComboBox<>();

        for (Logger log: logs) {
            games.addItem(log.getName());
        }

        JButton checkSave = new JButton("Checkout save");
        checkSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initTable(games.getItemAt(games.getSelectedIndex()));

            }
        });

        panel.add(backToMenu);
        panel.add(games);
        panel.add(checkSave);
    }

    private void initTable(String name){
        Logger log = new Logger();
        for (int i = 0; i <= logs.size() - 1; i++) {
            if(Objects.equals(logs.get(i).getName(), name)) {
                log = logs.get(i);
                break;
            }
        }

        String[] fens = new String[log.getMoves().size()];
        for(int i = 1; i <= log.getMoves().size(); i++) {
            fens[i - 1] = log.get(i).toString();
            System.out.println(fens[i - 1]);
        }
        String [] cols = new String[2];
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnCount(0);
        model.addColumn("FEN-S",fens);
        table = new JTable(model);
        model.fireTableDataChanged();

        scroll = new JScrollPane(table);
        panel2 = new JPanel();
        panel2.add(scroll);
        this.add(panel2, BorderLayout.CENTER);
    }

    public void initNames(){
        File f = new File("saves" + File.separator);
        FilenameFilter filter = (dir, name) -> name.endsWith(".txt");
        files = f.list(filter);
        assert files != null;
        for (String path: files) {
            logs.add(new Logger("saves" + File.separator + path));
        }

        Collections.sort(logs, new LogComparator());
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState(){
        return state;
    }
}
