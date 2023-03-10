package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.PriorityQueue;

public class Birou<E> {
    PriorityQueue<Request> cereri_birou = new PriorityQueue<>(new RequestBirouComparator());
    ArrayList<FunctionarPublic> functionari = new ArrayList<>();
    String element;

    public Birou(String element) {
        this.element = element;
    }
    public void afiseazaCereriBirou(String file) throws IOException {
        try (FileWriter fw = new FileWriter("src/main/resources/output/" + file, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(this.element + " - cereri in birou:");
            PriorityQueue<Request> aux = new PriorityQueue<>(new RequestBirouComparator());
            while (!cereri_birou.isEmpty()) {
                aux.add(cereri_birou.peek());
                out.println(cereri_birou.poll().toStringBirou());
            }
            cereri_birou = aux;
        } catch(FileNotFoundException e) {
            File f = new File("src/main/resources/output/" + file);
            f.createNewFile();
            afiseazaCereriBirou(file);
        } catch (IOException e) {
            System.out.println("Error writing to file");
            e.printStackTrace();
        }
    }

    public void adaugaFunctionar(String nume) {
        functionari.add(new FunctionarPublic(nume));
    }

    public void rezolvaCerere(String nume_angajat, ArrayList<Utilizator> utilizatori, String file) throws IOException {
        FunctionarPublic angajat = null;
        for (FunctionarPublic f : functionari) {
            if (f.getNume().equals(nume_angajat)) {
                angajat = f;
            }
        }
        if (angajat != null) {
            Request cerere = cereri_birou.poll();
            angajat.cereri_rezolvate.add(cerere);
            String nume_utilizator = (cerere.body.split(" ")[1] + " " + cerere.body.split(" ")[2]).split(",")[0];
            System.out.println(cereri_birou);
            String tip_utilizator = "c";
            for (Utilizator u : utilizatori) {
                if (u.getNume().equals(nume_utilizator)) {
                    u.cereri_rezolvate.add(cerere);
                    u.retrageCerere(cerere.date, u);
                    tip_utilizator = u.getClass().getSimpleName();
                }
            }
            try (FileWriter fw = new FileWriter("src/main/resources/output/functionar_" +  nume_angajat + ".txt", true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                if (tip_utilizator.equals("c")) {
                 out.println(cerere.toStringFinalizateJuridic());
                } else {
                    out.println(cerere.toStringFinalizate());
                }
            } catch(FileNotFoundException e) {
                File f = new File("src/main/resources/output/functionar_" +  nume_angajat + ".txt");
                f.createNewFile();
                rezolvaCerere(nume_angajat, utilizatori, file);
            } catch (IOException e) {
                System.out.println("Error writing to file");
                e.printStackTrace();
            }
        }
    }

    public void retrageCerereBirou(Date data) {
        for (Request req : cereri_birou) {
            if (req.date.equals(data)) {
                cereri_birou.remove(req);
                break;
            }
        }
    }
}

// Metoda prin care se vor compara elementele din coada de prioritati
class RequestBirouComparator implements Comparator<Request> {
    @Override
    public int compare(Request o1, Request o2) {
        if (o1.priority < o2.priority) {
            return 1;
        } else if (o1.priority > o2.priority) {
            return -1;
        } else
            return o1.date.compareTo(o2.date);
    }
}