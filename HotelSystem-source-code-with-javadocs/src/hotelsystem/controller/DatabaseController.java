package hotelsystem.controller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class DatabaseController {
    protected static final String SEPARATOR = "|";
    protected static final String DATABASE_DIR = "DB/";
    private final List<String> data;
    private File f;

    
    //Database Constructor
    public DatabaseController() {
        data = new ArrayList<>();

        f = new File(DATABASE_DIR);
        // if the directory does not exist, create it
        if (!f.exists()) {
            try {
                //noinspection ResultOfMethodCallIgnored
                f.mkdir();
            } catch (Exception ex) {
                //handle it
                System.out.println("[Error] You are not authorized to create a database. Please run as Administrator!");
                Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex); //Create Fatal Error to stop the program
            }
        }
    }

    //Abstract Methods for Child to Implement
    protected abstract boolean LoadDB();

    protected abstract void SaveDB();

    
    public boolean checkFileExist(String filepath) {
        f = new File(filepath);
        if (!f.exists()) {
            try {
                //noinspection ResultOfMethodCallIgnored
                f.createNewFile();
            } catch (Exception ex) {
                System.out.println("[Error] You are not authorized to create a database. Please run as Administrator!");
                //Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        return f.exists();
    }

    
    public void write(String fileName, List<String> data) {
        try (PrintWriter outWriter = new PrintWriter(new FileWriter(fileName))) {
            for (String aData : data) {
                outWriter.println(aData);
            }
        } catch (Exception io) {
            System.out.println("[Error] Failed to write to file!");
        }
    }

    
    public List<String> read(String fileName) throws IOException {
        data.clear();

        try (Scanner scanner = new Scanner(new FileInputStream(fileName))) {
            while (scanner.hasNextLine()) {
                data.add(scanner.nextLine());
            }
        }
        return data;
    }

}
