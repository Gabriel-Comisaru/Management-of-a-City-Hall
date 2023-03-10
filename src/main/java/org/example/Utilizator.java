package org.example;

import java.io.*;
import java.util.*;

public abstract class Utilizator {
    private String nume;

    public Utilizator() {
    }
    PriorityQueue<Request> cereri_in_asteptare = new PriorityQueue<>(new RequestComparator());
    ArrayList<Request> cereri_rezolvate = new ArrayList<>();
    public Utilizator(String nume) {
        this.nume = nume;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    //metoda adauga cerere va fi implementata de fiecare tip de utilizator, ea avand un template diferit
    public abstract String adaugaCerere(String data, Cerere cer, String file) throws InvalidRequest, IOException;
    @Override
    public abstract String toString();

    public abstract String getTemplate();

    public void afiseazaCereriInAsteptare(Utilizator u, String file) throws IOException {
            try (FileWriter fw = new FileWriter("src/main/resources/output/" + file, true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                out.println(u.getNume() + " - cereri in asteptare:");
                PriorityQueue<Request> aux = new PriorityQueue<>(new RequestComparator());
                while (!u.cereri_in_asteptare.isEmpty()) {
                    aux.add(u.cereri_in_asteptare.peek());
                    out.println(u.cereri_in_asteptare.poll());
                }
                u.cereri_in_asteptare = aux;
            } catch(FileNotFoundException e) {
                File f = new File("src/main/resources/output/" + file);
                f.createNewFile();
                afiseazaCereriInAsteptare(u, file);
            } catch (IOException e) {
                System.out.println("Error writing to file");
                e.printStackTrace();
            }
    }

    public void afiseazaCereriFinalizate(Utilizator u, String file) throws IOException {
        try (FileWriter fw = new FileWriter("src/main/resources/output/" +  file, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(u.getNume() + " - cereri in finalizate:");
            for (Request rq : u.cereri_rezolvate) {
                out.println(rq.toString());
            }
        } catch(FileNotFoundException e) {
            File f = new File("src/main/resources/output/" + file);
            f.createNewFile();
            afiseazaCereriFinalizate(u, file);
        } catch (IOException e) {
            System.out.println("Error writing to file");
            e.printStackTrace();
        }
    }

    public void retrageCerere(Date data, Utilizator u) {
        Utilizator u_aux;
        for (Request req : u.cereri_in_asteptare) {
            if (req.date.equals(data)) {
                u.cereri_in_asteptare.remove(req);
                break;
            }
        }
    }

}
