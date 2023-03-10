package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


import static org.example.Cerere.*;

public class ManagementPrimarie {
    public static void main(String[] args) throws IOException, ParseException {

        ArrayList<FunctionarPublic> fp = new ArrayList<>();
        ArrayList<Utilizator> utilizatori = new ArrayList<>();
        Utilizator utilizator = null;
        Birou<Angajat> birou_angajat = new Birou<>("angajat");
        Birou<Elev> birou_elev = new Birou<>("elev");
        Birou<EntitateJuridica> birou_entitatejuridica = new Birou<>("entitate juridica");
        Birou<Pensionar> birou_pensionar = new Birou<>("pensionar");
        Birou<Persoana> birou_persoana = new Birou<>("persoana");
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/input/" + args[0]))) {
            String line;
            line = br.readLine();
            String comanda;
            while (line != null) {
                // preluare comanda
                comanda = line.split(";")[0];
                if (comanda.equals("adauga_utilizator")) {
                    String tip = line.split(";")[1].substring(1);
                    // cazuri pentru a stii ce fel de utilizator se adauga in ArrayList
                    switch (tip) {
                        case "angajat":
                            utilizatori.add(new Angajat(line.split(";")[2].split(" ", 2)[1], line.split(";")[3].split(" ", 2)[1]));
                            break;
                        case "elev":
                            utilizatori.add(new Elev(line.split(";")[2].substring(1), line.split(";")[3].substring(1)));
                            break;
                        case "entitate juridica":
                            utilizatori.add(new EntitateJuridica(line.split(";")[2].substring(1), line.split(";")[3].substring(1)));
                            break;
                        case "persoana":
                            utilizatori.add(new Persoana(line.split(";")[2].substring(1)));
                            break;
                        case "pensionar":
                            utilizatori.add(new Pensionar(line.split(";")[2].substring(1)));
                            break;
                    }
                }
                if (comanda.equals("cerere_noua")) {
                    String nume = line.split(";")[1].split(" ", 2)[1];
                    // cautam utilizatorul care doreste sa creeze o cerere si-l salvam
                    for (Utilizator u : utilizatori) {
                        if (u.getNume().equals(nume)) {
                            utilizator = u;
                            break;
                        }
                    }
                    // tipul cererii + data + prioritatea
                    String tip = line.split(";")[2].substring(1).replace(" ", "_").toUpperCase();
                    String data = line.split(";")[3].split(" ", 2)[1];
                    Integer prioritate = Integer.parseInt(line.split(";")[4].split(" ", 2)[1]);
                    Cerere cerere = Cerere.valueOf(tip);
                    assert utilizator != null;
                    Request request;
                    String body;
                    Date date = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse(data);
                    try {
                        // body-ul cererii
                        body = utilizator.adaugaCerere(data,cerere, args[0]);
                    } catch (InvalidRequest e) {
                        line = br.readLine();
                        continue;
                    }
                    request = new Request(body, date, prioritate);
                    // verificam in ce birou va fi adaugata cererea
                    switch (utilizator.getClass().getSimpleName()) {
                        case "Angajat":
                            birou_angajat.cereri_birou.add(request);
                            break;
                        case "Elev":
                            birou_elev.cereri_birou.add(request);
                            //System.out.println(birou_elev.cereri_birou);
                            break;
                        case "EntitateJuridica":
                            birou_entitatejuridica.cereri_birou.add(request);
                            //System.out.println(birou_entitatejuridica.cereri_birou);
                            break;
                        case "Persoana":
                            birou_persoana.cereri_birou.add(request);
                            //System.out.println(birou_persoana.cereri_birou);
                            break;
                        case "Pensionar":
                            birou_pensionar.cereri_birou.add(request);
                            //System.out.println(birou_pensionar.cereri_birou);
                            break;
                    }
                    // adaugam cererea in cererile utilizatorului
                    if (utilizator.cereri_in_asteptare == null) {
                        PriorityQueue<Request> cereri_in_asteptare = new PriorityQueue<>(new RequestComparator());
                        cereri_in_asteptare.add(request);
                        utilizator.cereri_in_asteptare = cereri_in_asteptare;
                    } else {
                        utilizator.cereri_in_asteptare.add(request);
                    }


                }
                if (comanda.equals("afiseaza_cereri_in_asteptare")) {
                    String nume = line.split(";")[1].split(" ", 2)[1];
                    for (Utilizator u : utilizatori) {
                        if (u.getNume().equals(nume)) {
                            utilizator = u;
                            break;
                        }
                    }
                    assert utilizator != null;
                    utilizator.afiseazaCereriInAsteptare(utilizator, args[0]);
                }
                if (comanda.equals("retrage_cerere")) {
                    String nume = line.split(";")[1].substring(1);
                    String data = line.split(";")[2].substring(1);
                    Date date = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse(data);
                    for (Utilizator u : utilizatori) {
                        if (u.getNume().equals(nume)) {
                            utilizator = u;
                            break;
                        }
                    }
                    assert utilizator != null;
                    utilizator.retrageCerere(date, utilizator);
                    // verificam din ce birou retragem cererea
                    switch (utilizator.getClass().getSimpleName()) {
                        case "Angajat":
                            birou_angajat.retrageCerereBirou(date);
                            break;
                        case "Elev":
                            birou_elev.retrageCerereBirou(date);
                            break;
                        case "EntitateJuridica":
                            birou_entitatejuridica.retrageCerereBirou(date);
                            break;
                        case "Persoana":
                            birou_persoana.retrageCerereBirou(date);
                            break;
                        case "Pensionar":
                            birou_pensionar.retrageCerereBirou(date);
                            break;
                    }
                }
                if (comanda.equals("afiseaza_cereri")) {
                    String tip_birou = line.split(";")[1].substring(1);
                    // tipul biroului pentru care afisam cererile
                    // case-urile chiar nu stiu de ce sunt pozitionate in felul asta
                    switch (tip_birou) {
                        case "angajat":
                            birou_angajat.afiseazaCereriBirou(args[0]);
                            break;
                            case "elev":
                            birou_elev.afiseazaCereriBirou(args[0]);
                            break;
                            case "entitate juridica":
                            birou_entitatejuridica.afiseazaCereriBirou(args[0]);
                            break;
                        case "pensionar":
                            birou_pensionar.afiseazaCereriBirou(args[0]);
                            break;
                        case "persoana":
                            birou_persoana.afiseazaCereriBirou(args[0]);
                            break;
                    }
                }
                if (comanda.equals("adauga_functionar")) {
                    String tip_functionar = line.split(";")[1].substring(1);
                    String nume_functionar = line.split(";")[2].substring(1);
                    fp.add(new FunctionarPublic(nume_functionar, tip_functionar));
                    // aflam in ce fel de birou adaugam functionarul
                    switch (tip_functionar) {
                        case "angajat":
                            birou_angajat.adaugaFunctionar(nume_functionar);
                            break;
                            case "elev":
                            birou_elev.adaugaFunctionar(nume_functionar);
                            break;
                            case "entitate juridica":
                            birou_entitatejuridica.adaugaFunctionar(nume_functionar);
                            break;
                            case "persoana":
                            birou_persoana.adaugaFunctionar(nume_functionar);
                            break;
                            case "pensionar":
                            birou_pensionar.adaugaFunctionar(nume_functionar);
                            break;
                    }
                }
                if (comanda.equals("rezolva_cerere")) {
                    String tip_birou = line.split(";")[1].substring(1);
                    String nume_angajat = line.split(";")[2].substring(1);
                    // din ce birou rezolvam cererea
                    switch (tip_birou) {
                        case "angajat":
                            birou_angajat.rezolvaCerere(nume_angajat, utilizatori, args[0]);
                            break;
                            case "elev":
                            birou_elev.rezolvaCerere(nume_angajat, utilizatori, args[0]);
                            break;
                            case "entitate juridica":
                            birou_entitatejuridica.rezolvaCerere(nume_angajat, utilizatori, args[0]);
                            break;
                            case "persoana":
                            birou_persoana.rezolvaCerere(nume_angajat, utilizatori, args[0]);
                            break;
                            case "pensionar":
                                birou_pensionar.rezolvaCerere(nume_angajat, utilizatori, args[0]);
                                break;
                    }
                }
                if (comanda.equals("afiseaza_cereri_finalizate")) {
                    String nume_functionar = line.split(";")[1].substring(1);
                    Utilizator u = null;
                    for (Utilizator utilizator_aux : utilizatori) {
                        if (utilizator_aux.getNume().equals(nume_functionar)) {
                            u = utilizator_aux;
                            break;
                        }
                    }
                    assert u != null;
                    u.afiseazaCereriFinalizate(u, args[0]);
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}