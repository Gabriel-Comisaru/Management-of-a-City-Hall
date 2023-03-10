package org.example;

import java.io.IOException;

public class EntitateJuridica extends Utilizator{
    private String reprezentant;

    private String template = "Subsemnatul <reprezentant>, reprezentant legal al companiei <companie>, va rog sa-mi aprobati urmatoarea solicitare: <tip_cerere>";
    public EntitateJuridica(String nume, String reprezentant) {
        super(nume);
        this.reprezentant = reprezentant;
    }

    public String getReprezentant() {
        return reprezentant;
    }

    public void setReprezentant(String reprezentant) {
        this.reprezentant = reprezentant;
    }

    @Override
    public String toString() {
        return "EntitateJuridica{" + "nume=" + getNume() + ", reprezentant=" + reprezentant + '}';
    }

    @Override
    public String getTemplate() {
        return template;
    }

    public String adaugaCerere(String data, Cerere cer, String file) throws InvalidRequest, IOException {
        String request = template;
        switch(cer) {
            case CREARE_ACT_CONSTITUTIV:
            case REINNOIRE_AUTORIZATIE:
                return request.replace("<reprezentant>", this.getReprezentant()).replace("<companie>", this.getNume()).replace("<tip_cerere>", cer.label);
            default:
                throw new InvalidRequest(this.getClass().getSimpleName().replaceAll("([J-Z])", " $1"), cer, file);

        }
    }
}
