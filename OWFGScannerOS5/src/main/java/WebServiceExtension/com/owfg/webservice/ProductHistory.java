package com.owfg.webservice;

import com.owfg.stub.ItemSalesHistoryInfo;

/**
 * Created by IntelliJ IDEA.
 * User: Waz
 * Date: 5/26/11
 * Time: 6:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProductHistory {
    ItemSalesHistoryInfo[] history;
    ProductHistory(ItemSalesHistoryInfo[] history) {
        this.history = history;
    }

    public String[][][] toArray() {
        final int FIELDS = 5;
        int dayOfWeek;
        Double forecast;
        Boolean onPromo;
        Double promoSales;
        Double regularSales;
        String[] fields = new String[FIELDS];
        String[][][] arrayOfStrings = new String[1][history.length][FIELDS];
        for (int i = 0; i != history.length; i++) {
            dayOfWeek = history[i].getDayOfWeek();
            forecast = history[i].getForecast();
            onPromo = history[i].getOnPromo();
            promoSales = history[i].getPromoSales();
            regularSales = history[i].getRegularSales();
            fields[0] = Integer.toString(dayOfWeek);
            fields[1] = (forecast == null) ? "" : forecast.toString();
            fields[2] = (onPromo == null) ? "" : onPromo.toString();
            fields[3] = (promoSales == null) ? "" : promoSales.toString();
            fields[4] = (regularSales == null) ? "" : regularSales.toString();
            arrayOfStrings[0][i] = fields;
		}
        return arrayOfStrings;
    }
}
