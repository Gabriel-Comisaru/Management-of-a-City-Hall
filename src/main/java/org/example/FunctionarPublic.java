package org.example;

import java.io.*;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class FunctionarPublic {
    private String nume;

    public String tip;
    ArrayList<Request> cereri_rezolvate = new ArrayList<>();
    public FunctionarPublic() {
    }

    public FunctionarPublic(String nume) {
        this.nume = nume;
    }

    public FunctionarPublic(String nume, String tip) {
        this.nume = nume;
        this.tip = tip;
    }
    public String getNume() {
        return nume;
    }


}
