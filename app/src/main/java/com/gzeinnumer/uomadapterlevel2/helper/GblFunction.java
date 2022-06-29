package com.gzeinnumer.uomadapterlevel2.helper;

import java.text.NumberFormat;
import java.util.Locale;

public class GblFunction {

    public static String MATA_UANG = "Rp ";

    public static String s(Object str) {
        return String.valueOf(str);
    }

    public static String saparator(String value) {
        if (value.length()==0){
            return "0";
        }
        if (value == null || value.equals("")) {
            return "0";
        }
        value = idrComma(value);
        if (value.contains(","))
            return value.substring(0, value.indexOf(","));
        else
            return value;
    }

    public static String idrComma(String value) {
        if (value.length()==0){
            return "0";
        }
        if (value == null || value.equals("")) {
            return "0";
        } else {
            Locale localeID = new Locale("in", "ID");
            NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
            return formatRupiah.format(Double.valueOf(value)).replace("Rp", "");
        }
    }

    public static String idr(String value) {
        return MATA_UANG + idrComma(value).replace(",00", "");
    }

    public static String clearAllSymbol(String value) {
        return value.replace(MATA_UANG, "").replace(".", "").replace(",", "");
    }
}
