package util;

import game.Fen;

import java.io.*;
import java.util.HashMap;

public class Logger implements Serializable{
     static final long serialVersionUID = 888888888L;
     String path;
     private HashMap<Integer, Fen> moves = new HashMap<>();
     private int size = 0;
     boolean[] end = new boolean[2];

    public Logger(){
        path =  "saves" +
                File.separator +
                System.currentTimeMillis() / 1000L +
                ".txt";
        end = new boolean[]{false, false, false};
    }

    public Logger(String path) {
        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Logger cpy = (Logger) ois.readObject();
            this.moves = cpy.moves;
            this.end = cpy.end;
            this.path = path;
            this.size = moves.size();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void put(Fen fen){
        size++;
        moves.put(size, fen);

    }

    public HashMap<Integer, Fen> getMoves(){
        return moves;
    }

    public Fen get(int key){
        return moves.get(key);
    }

    public void drop(){
        if(size > 1) {
            moves.remove(size);
            size--;
        }
    }

    public Fen getLast(){
        return moves.get(size);
    }

    public void save(String name) {
        try {
            if(name != null) {
                path = "saves" +
                        File.separator +
                        name +
                        ".txt";
            }
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.close();
            fos.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void configEnd(int inx){
        end[inx] = true;
    }

    public String getName() {
        return path;
    }
}
