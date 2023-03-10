package org.example;

public enum Cerere {
    INLOCUIRE_BULETIN("inlocuire buletin"),
    INREGISTRARE_VENIT_SALARIAL("inregistrare venit salarial"),
    INLOCUIRE_CARNET_DE_SOFER("inlocuire carnet de sofer"),
    INLOCUIRE_CARNET_DE_ELEV("inlocuire carnet de elev"),
    CREARE_ACT_CONSTITUTIV("creare act constitutiv"),
    REINNOIRE_AUTORIZATIE("reinnoire autorizatie"),
    INREGISTRARE_CUPOANE_DE_PENSIE("inregistrare cupoane de pensie");

    public final String label;

    Cerere(String label) {
        this.label = label;
    }
}
