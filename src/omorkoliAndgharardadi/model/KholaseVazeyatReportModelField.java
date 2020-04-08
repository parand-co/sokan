package omorkoliAndgharardadi.model;

import com.google.gson.annotations.Expose;

public class KholaseVazeyatReportModelField {
   @Expose
    private String Stringsemat;
   @Expose
    private String StringdarajeEmza;
   @Expose
    private String StringnameNeshanEmza;
   @Expose
    private String Stringemza;

    public String getStringsemat() {
        return Stringsemat;
    }

    public void setStringsemat(String stringsemat) {
        Stringsemat = stringsemat;
    }

    public String getStringdarajeEmza() {
        return StringdarajeEmza;
    }

    public void setStringdarajeEmza(String stringdarajeEmza) {
        StringdarajeEmza = stringdarajeEmza;
    }

    public String getStringnameNeshanEmza() {
        return StringnameNeshanEmza;
    }

    public void setStringnameNeshanEmza(String stringnameNeshanEmza) {
        StringnameNeshanEmza = stringnameNeshanEmza;
    }

    public String getStringemza() {
        return Stringemza;
    }

    public void setStringemza(String stringemza) {
        Stringemza = stringemza;
    }
}
