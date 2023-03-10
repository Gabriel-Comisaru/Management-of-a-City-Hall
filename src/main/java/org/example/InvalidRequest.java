package org.example;

import java.io.*;

public class InvalidRequest extends Exception {
    public InvalidRequest(String tip, Cerere cer, String file) throws IOException, InvalidRequest {
        try (FileWriter fw = new FileWriter("src/main/resources/output/" + file, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println("Utilizatorul de tip " + tip.toLowerCase() +  " nu poate inainta o cerere de tip " + cer.label);
        } catch(FileNotFoundException e) {
            File f = new File("src/main/resources/output/" + file);
            f.createNewFile();
            throw new InvalidRequest(tip,cer,file);
        } catch (IOException e) {
            System.out.println("Error writing to file");
        }
    }
}
