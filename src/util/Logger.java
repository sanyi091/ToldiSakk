package util;

import game.Fen;

import java.io.*;
import java.util.HashMap;

/**
 * The main class of saving the game, so it has to be serializable.
 */
public class Logger implements Serializable{
     static final long serialVersionUID = 888888888L;
     String path;
     private HashMap<Integer, Fen> moves = new HashMap<>();
     private int size = 0;
     boolean[] end = new boolean[2];

    /**
     * Creates a new logger which has the name of the current time.
     */
    public Logger(){
        path =  "saves" +
                File.separator +
                System.currentTimeMillis() / 1000L +
                ".txt";
        end = new boolean[]{false, false, false};
    }

    /**
     * Open an already existing save based on its path.
     * @param path The path of the directory.
     */
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

    /**
     * Puts a new move into the logger.
     * @param fen The new value.
     */
    public void put(Fen fen){
        size++;
        moves.put(size, fen);

    }

    /**
     * Gives back the HashMap which stores the moves.
     * @return The HashMap.
     */
    public HashMap<Integer, Fen> getMoves(){
        return moves;
    }

    /**
     * Return a value based on its key(index).
     * @param key The keyof the value.
     * @return The needed state.
     */
    public Fen get(int key){
        return moves.get(key);
    }

    /**
     * Deletes the last state from the HashMap, used in reversing a move.
     */
    public void drop(){
        if(size > 1) {
            moves.remove(size);
            size--;
        }
    }

    /**
     * Returns the last value put into the HashMap
     * @return The last value.
     */
    public Fen getLast(){
        return moves.get(size);
    }

    /**
     * Saves the logger into a text file
     * @param name The name of the file if blank, random.
     */
    public void save(String name) {
        try {
            if(name != null || !name.equals("")) {
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

    /**
     * Configures the endstate of a game.
     * @param inx 0 - Mate, 1 - Stalemate.
     */
    public void configEnd(int inx){
        end[inx] = true;
    }

    /**
     * Returns the path of the Logger.
     * @return The path.
     */
    public String getName() {
        return path;
    }
}
