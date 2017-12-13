package com.company;

import java.util.*;

public class SpecialList extends TreeSet<ProductImport> {

    public SpecialList(ImportComparator importComparator) {
        super(importComparator);
    }

    public void addElement(ProductImport productImport) {
        for(ProductImport p : this){
            if(p.getState().compareTo(productImport.getState()) == 0){
                p.addImportVolume(productImport.getImportVolume());
                return;
            }
        }
        super.add(productImport);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (ProductImport p : this){
            stringBuilder.append(p.toString()).append("\n");
        }
        return stringBuilder.toString();
    }
}
