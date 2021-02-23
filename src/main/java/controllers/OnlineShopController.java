/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Korisnik;
import beans.Magacin;
import beans.Narudzbina;
import beans.Ocene;
import beans.Onlineprodavnica;
import beans.Poljoprivrednik;
import beans.ProizvodWrapper;
import beans.Rasadnik;
import beans.Stavka;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ana
 */
@Named
@SessionScoped
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
public class OnlineShopController implements Serializable{
    
    private List<ProizvodWrapper> proizvodi;
    private Poljoprivrednik ulogovanPoljoprivrednik;
    
    private List<ProizvodWrapper> korpa;
    
    private List<Ocene> ocene;
    
    private Integer ocenaProizvoda;
    private String komentar;
    
    private LinkedList<SelectItem> rasadnici;
    private Integer izabranRasadnik;
    private String message;
    private String messageDetalji;
    private Onlineprodavnica izabranProizvod;
    
    private List<String> kriticniRasadnici;
    
    @PostConstruct
    public void init(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session= (HttpSession) context.getExternalContext().getSession(false);
        Korisnik korisnik=(Korisnik) session.getAttribute("loggedInUser");
        ulogovanPoljoprivrednik=korisnik.getPoljoprivrednik();
        List<Onlineprodavnica> pr = Onlineprodavnica.dohvatiProizvode();
        proizvodi=new ArrayList<ProizvodWrapper>();
        for(int i=0; i<pr.size(); i++){
            ProizvodWrapper pw = new ProizvodWrapper(pr.get(i),"0");
            proizvodi.add(pw);
        }
        if(korpa!=null){
            korpa.clear();
        }
        korpa=new ArrayList<ProizvodWrapper>();
        rasadnici=Rasadnik.rasadniciItems(korisnik.getPoljoprivrednik().getUsernamePoljo());
       
    }

   
    
    

    public Onlineprodavnica getIzabranProizvod() {
        return izabranProizvod;
    }

    public void setIzabranProizvod(Onlineprodavnica izabranProizvod) {
        this.izabranProizvod = izabranProizvod;
    }
    
    

    public String getMessageDetalji() {
        return messageDetalji;
    }

    public void setMessageDetalji(String messageDetalji) {
        this.messageDetalji = messageDetalji;
    }
    
    

    public List<ProizvodWrapper> getProizvodi() {
        return proizvodi;
    }

    public void setProizvodi(List<ProizvodWrapper> proizvodi) {
        this.proizvodi = proizvodi;
    }

    public Poljoprivrednik getUlogovanPoljoprivrednik() {
        return ulogovanPoljoprivrednik;
    }

    public void setUlogovanPoljoprivrednik(Poljoprivrednik ulogovanPoljoprivrednik) {
        this.ulogovanPoljoprivrednik = ulogovanPoljoprivrednik;
    }

    public List<ProizvodWrapper> getKorpa() {
        return korpa;
    }

    public void setKorpa(List<ProizvodWrapper> korpa) {
        this.korpa = korpa;
    }

    public List<Ocene> getOcene() {
        return ocene;
    }

    public void setOcene(List<Ocene> ocene) {
        this.ocene = ocene;
    }

    public Integer getOcenaProizvoda() {
        return ocenaProizvoda;
    }

    public void setOcenaProizvoda(Integer ocenaProizvoda) {
        this.ocenaProizvoda = ocenaProizvoda;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public LinkedList<SelectItem> getRasadnici() {
        return rasadnici;
    }

    public void setRasadnici(LinkedList<SelectItem> rasadnici) {
        this.rasadnici = rasadnici;
    }

    public Integer getIzabranRasadnik() {
        return izabranRasadnik;
    }

    public void setIzabranRasadnik(Integer izabranRasadnik) {
        this.izabranRasadnik = izabranRasadnik;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    

   
   
    
    

    
    
    
    
    public String prikaziDetalje(ProizvodWrapper proizvod){
        messageDetalji="";
        ocenaProizvoda=0;
        komentar="";
        izabranProizvod=proizvod.getProizvod();
        ocene = Ocene.dohvatiOcene(proizvod.getProizvod().getIdPro());
        return "oceneDetalji";
        
    }
    
    public void dodajUKorpu(ProizvodWrapper proizvod){
        if(Integer.valueOf(proizvod.getKolicina())<=Integer.valueOf(proizvod.getProizvod().getKolicina())){
        korpa.add(proizvod);
        message="";}else{
            message="Nema dovoljno na stanju!";
        }
    }
    
    public void potvrdiPorudzbinu(){
        if(korpa != null){
       Narudzbina narudzbina = new Narudzbina();
       LocalDate now = LocalDate.now();
       ZoneId defaultZoneId=ZoneId.of("Z");
       Date datum=Date.from(now.atStartOfDay(defaultZoneId).toInstant());
       narudzbina.setDatum(datum);
       narudzbina.setRasadnik(Rasadnik.dohvatiRasadnik(izabranRasadnik));
       Narudzbina.sacuvajNarudzbinu(narudzbina);
       for(int i=0; i<korpa.size(); i++){
           Stavka stavka=new Stavka(narudzbina,korpa.get(i).getProizvod(),Integer.valueOf(korpa.get(i).getKolicina()));
           stavka.setStatus("neisporuceno");
           Stavka.doadajStavku(stavka);
           Onlineprodavnica.azurirajProdavnicu(korpa.get(i).getProizvod(), korpa.get(i).getKolicina());
       }
       
       message="Uspesno ste obavili kupovinu! Vasa narudzbina je zavedena!\nHvala na poverenju!";
       korpa.clear();
        }
    }
    
    public void isprazniKorpu(){
       if(korpa !=null) korpa.clear();
        
    }
    
    public void izbaciIzKorpe(ProizvodWrapper proizvod){
        if(korpa!=null){
            korpa.remove(proizvod);
            
        }
    }
    
    public BigDecimal izracunajSrednjuOcenu(){
        BigDecimal srOcena=BigDecimal.ZERO;
        
        int zbir=0;
        for(int i=0; i<ocene.size();i++){
            zbir += ocene.get(i).getOcena();
        }
        srOcena=BigDecimal.valueOf(zbir).divide(BigDecimal.valueOf(ocene.size()));
        return srOcena;
    }
    
    public void ostaviKomentar(){
        messageDetalji="";
        if(!Magacin.provera(ulogovanPoljoprivrednik.getUsernamePoljo(),izabranProizvod.getIdPro())){
            messageDetalji="Mozete ostaviti komentar samo na proizvode koje ste prethodno narucivali";
            return;
        }
        if(!Ocene.proveraKomentara(ulogovanPoljoprivrednik.getUsernamePoljo(),izabranProizvod.getIdPro())){
            messageDetalji="Mozete ostaviti maksimalno jednu ocenu i komentar po proizvodu";
            return;
        }
        Ocene novaOcena=new Ocene(izabranProizvod,ulogovanPoljoprivrednik,ocenaProizvoda,komentar);
        Ocene.dodajOcenu(novaOcena);
        ocene.add(novaOcena);
        izabranProizvod.setSrOcena(izracunajSrednjuOcenu());
        Onlineprodavnica.azurirajProizvod(izabranProizvod);
        komentar="";
        ocenaProizvoda=0;
        
    }
    
    public void preRenderExecute(){
        List<Onlineprodavnica> pr = Onlineprodavnica.dohvatiProizvode();
        proizvodi=new ArrayList<ProizvodWrapper>();
        for(int i=0; i<pr.size(); i++){
            ProizvodWrapper pw = new ProizvodWrapper(pr.get(i),"0");
            proizvodi.add(pw);
        }
       
    }
    
}
