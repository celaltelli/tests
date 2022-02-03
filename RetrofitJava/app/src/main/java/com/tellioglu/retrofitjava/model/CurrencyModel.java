package com.tellioglu.retrofitjava.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CurrencyModel {

    @SerializedName("TCMB_AnlikKurBilgileri")
    public List<CurrrencyMod> currrencyModList = new ArrayList<>();
    @SerializedName("Developer")
    public DeveloperModel developer = new DeveloperModel();

 //"TCMB_AnlikKurBilgileri":[{"Isim":"ABD DOLARI","CurrencyName":"US DOLLAR","ForexBuying":13.4287,"ForexSelling":13.4529,"BanknoteBuying":13.4193,"BanknoteSelling":13.4731,"CrossRateUSD":"","CrossRateOther":""}
public class CurrrencyMod {
     @SerializedName("Isim")
     String name;
     @SerializedName("CurrencyName")
     String CurrencyName;
     @SerializedName("ForexBuying")
     String ForexBuying;
     @SerializedName("ForexSelling")
     String ForexSelling;
     @SerializedName("BanknoteBuying")
     String BanknoteBuying;
     @SerializedName("BanknoteSelling")
     String BanknoteSelling;
     @SerializedName("CrossRateUSD")
     String CrossRateUSD;
     @SerializedName("CrossRateOther")
     String CrossRateOther;

     public String getName() {
         return name;
     }

     public void setName(String name) {
         this.name = name;
     }

     public String getCurrencyName() {
         return CurrencyName;
     }

     public void setCurrencyName(String currencyName) {
         CurrencyName = currencyName;
     }

     public String getForexBuying() {
         return ForexBuying;
     }

     public void setForexBuying(String forexBuying) {
         ForexBuying = forexBuying;
     }

     public String getForexSelling() {
         return ForexSelling;
     }

     public void setForexSelling(String forexSelling) {
         ForexSelling = forexSelling;
     }

     public String getBanknoteBuying() {
         return BanknoteBuying;
     }

     public void setBanknoteBuying(String banknoteBuying) {
         BanknoteBuying = banknoteBuying;
     }

     public String getBanknoteSelling() {
         return BanknoteSelling;
     }

     public void setBanknoteSelling(String banknoteSelling) {
         BanknoteSelling = banknoteSelling;
     }

     public String getCrossRateUSD() {
         return CrossRateUSD;
     }

     public void setCrossRateUSD(String crossRateUSD) {
         CrossRateUSD = crossRateUSD;
     }

     public String getCrossRateOther() {
         return CrossRateOther;
     }

     public void setCrossRateOther(String crossRateOther) {
         CrossRateOther = crossRateOther;
     }
 }
}

