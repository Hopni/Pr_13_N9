package com.company;

import java.util.StringTokenizer;
import java.util.zip.DataFormatException;

public class ProductImport {
    private String product;
    private String state;
    private int importVolume;


    ProductImport(String s) throws DataFormatException, NumberFormatException {
        StringTokenizer st = new StringTokenizer(s, " ");
        product = st.nextToken();
        state = st.nextToken();
        importVolume = Integer.parseInt(st.nextToken());
    }

    public int getImportVolume() {
        return importVolume;
    }

    public String getState() {
        return state;
    }

    public void addImportVolume(int a){
        importVolume += a;
    }

    @Override
    public String toString() {
        return state + " | Import volume: " + importVolume;
    }
}
