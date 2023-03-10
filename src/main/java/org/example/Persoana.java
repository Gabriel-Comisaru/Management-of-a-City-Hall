package org.example;

import java.io.IOException;

public class Persoana extends Utilizator{

    private String template = "Subsemnatul <nume>, va rog sa-mi aprobati urmatoarea solicitare: <tip_cerere>";
    public Persoana(String nume) {
        super(nume);
    }

    @Override
    public String toString() {
        return "Persoana{" + "nume=" + getNume() + '}';
    }

    @Override
    public String getTemplate() {
        return template;
    }

    public String adaugaCerere(String data, Cerere cer, String file) throws InvalidRequest, IOException {
        String request = template;
        switch(cer) {
            case INLOCUIRE_BULETIN:
            case INLOCUIRE_CARNET_DE_SOFER:
                return request.replace("<nume>", this.getNume()).replace("<tip_cerere>", cer.label);
            default:
                throw new InvalidRequest(this.getClass().getSimpleName(), cer, file);

        }
    }
}
