package org.example;

import java.io.IOException;
public class Angajat extends Utilizator {
    private String companie;

    private String template = "Subsemnatul <nume>, angajat la compania <companie>, va rog sa-mi aprobati urmatoarea solicitare: <tip_cerere>";
    public Angajat(String nume, String companie) {
        super(nume);
        this.companie = companie;
    }
    public String getCompanie() {
        return companie;
    }

    public void setCompanie(String companie) {
        this.companie = companie;
    }

    @Override
    public String toString() {
        return "Angajat{" + "nume=" + getNume() + ", companie=" + companie + '}';
    }

    public String getTemplate() {
        return template;
    }

    public String adaugaCerere(String data, Cerere cer, String file) throws InvalidRequest, IOException {
        String request = template;
        switch(cer) {
            case INLOCUIRE_BULETIN:
            case INREGISTRARE_VENIT_SALARIAL:
            case INLOCUIRE_CARNET_DE_SOFER:
                return request.replace("<nume>", this.getNume()).replace("<companie>", this.getCompanie()).replace("<tip_cerere>", cer.label);
                default:
                    throw new InvalidRequest(this.getClass().getSimpleName(), cer, file);

        }
    }
}
