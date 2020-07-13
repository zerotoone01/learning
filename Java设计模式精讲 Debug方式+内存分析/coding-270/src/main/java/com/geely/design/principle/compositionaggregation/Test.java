package com.geely.design.principle.compositionaggregation;

import java.util.Arrays;
import java.util.List;

/**
 * Created by geely
 */
public class Test {
    public static void main(String[] args) {
        ProductDao productDao = new ProductDao();
        productDao.setDbConnection(new PostgreSQLConnection());
        productDao.addProduct();

    }
}
