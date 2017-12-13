package com.company;

import java.util.Comparator;

public class ImportComparator implements Comparator<ProductImport> {
    @Override
    public int compare(ProductImport o1, ProductImport o2) {
        if(o2.getImportVolume() == o1.getImportVolume()){
            return o1.getState().compareTo(o2.getState());
        } else {
            return o2.getImportVolume() - o1.getImportVolume();
        }
    }
}
