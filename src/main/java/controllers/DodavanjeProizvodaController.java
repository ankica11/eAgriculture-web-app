/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Korisnik;
import beans.Onlineprodavnica;
import beans.Preduzece;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ana
 */
@Named
@SessionScoped
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
public class DodavanjeProizvodaController implements Serializable{
    
    private Preduzece currPreduzece;
    private int activestep;
    private String naziv;
    private String kolicina;
    private String cena;
    private String tip;
    private String uputstvo;
    private String message;
    
    
    @PostConstruct
    public void init(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session= (HttpSession) context.getExternalContext().getSession(false);
        Korisnik korisnik=(Korisnik) session.getAttribute("loggedInUser");
        currPreduzece=korisnik.getPreduzece();
        activestep=0;
        
        
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    

    public Preduzece getCurrPreduzece() {
        return currPreduzece;
    }

    public void setCurrPreduzece(Preduzece currPreduzece) {
        this.currPreduzece = currPreduzece;
    }

    public int getActivestep() {
        return activestep;
    }

    public void setActivestep(int activestep) {
        this.activestep = activestep;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getKolicina() {
        return kolicina;
    }

    public void setKolicina(String kolicina) {
        this.kolicina = kolicina;
    }

    public String getCena() {
        return cena;
    }

    public void setCena(String cena) {
        this.cena = cena;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getUputstvo() {
        return uputstvo;
    }

    public void setUputstvo(String uputstvo) {
        this.uputstvo = uputstvo;
    }
    
    
    
    public void sledeci(){
        message="";
        activestep++;
        
    }
    
    public void prethodni(){
        
        activestep--;
    }
    
    public void dodajProizvod(){
        activestep=0;
        BigDecimal cena1= BigDecimal.valueOf(Integer.valueOf(cena));
       
        Onlineprodavnica novi=new Onlineprodavnica(currPreduzece, naziv, kolicina, tip, Integer.valueOf(uputstvo), cena1, BigDecimal.ZERO);
        Onlineprodavnica.dodajProizvod(novi);
        message="Uspesno ste dodali proizvod";
        naziv="";
        kolicina="";
        cena="";
        uputstvo="";
        
        
    }
    
    
    
    
}
