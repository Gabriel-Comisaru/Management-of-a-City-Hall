package org.example;

import java.io.IOException;

public class Elev extends Utilizator {
    private String scoala;

    private String template = "Subsemnatul <nume>, elev la scoala <scoala>, va rog sa-mi aprobati urmatoarea solicitare: <tip_cerere>";
    public Elev(String nume, String scoala) {
        super(nume);
        this.scoala = scoala;
    }

    public String getScoala() {
        return scoala;
    }

    public void setScoala(String scoala) {
        this.scoala = scoala;
    }

    @Override
    public String toString() {
        return "Elev{" + "nume=" + getNume() + ", scoala=" + scoala + '}';
    }

    @Override
    public String getTemplate() {
        return template;
    }

    public String adaugaCerere(String data, Cerere cer, String file) throws InvalidRequest, IOException {
        String request = template;
        switch(cer) {
            case INLOCUIRE_BULETIN:
            case INLOCUIRE_CARNET_DE_ELEV:
                return request.replace("<nume>", this.getNume()).replace("<scoala>", this.getScoala()).replace("<tip_cerere>", cer.label);
            default:
                throw new InvalidRequest(this.getClass().getSimpleName(), cer, file);

        }
    }
}
