/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Korisnik;
import beans.Poljoprivrednik;
import beans.Rasadnik;
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
@FacesConfig(version=FacesConfig.Version.JSF_2_3)
public class DodavanjeRasadnikController implements Serializable{
    private String naziv;
    private String mesto;
    private String duzina;
    private String sirina;
    private Poljoprivrednik ulogovanPoljoprivrednik;
    private String message;
    
    @PostConstruct
    public void init(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session= (HttpSession) context.getExternalContext().getSession(false);
        Korisnik korisnik=(Korisnik) session.getAttribute("loggedInUser");
        ulogovanPoljoprivrednik=korisnik.getPoljoprivrednik();
       
    }

    public Poljoprivrednik getUlogovanPoljoprivrednik() {
        return ulogovanPoljoprivrednik;
    }

    public void setUlogovanPoljoprivrednik(Poljoprivrednik ulogovanPoljoprivrednik) {
        this.ulogovanPoljoprivrednik = ulogovanPoljoprivrednik;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getMesto() {
        return mesto;
    }

    public void setMesto(String mesto) {
        this.mesto = mesto;
    }

    public String getDuzina() {
        return duzina;
    }

    public void setDuzina(String duzina) {
        this.duzina = duzina;
    }

    public String getSirina() {
        return sirina;
    }

    public void setSirina(String sirina) {
        this.sirina = sirina;
    }
    
    public void dodajRasadnik(){
        int brojSlobodnih=Integer.valueOf(sirina)*Integer.valueOf(duzina);
        int brojZasadjenih=0;
        Rasadnik novi=new Rasadnik(ulogovanPoljoprivrednik,naziv,mesto,brojZasadjenih,brojSlobodnih,200,BigDecimal.valueOf(18),Integer.valueOf(duzina),Integer.valueOf(sirina));
        Rasadnik.dodajRasadnik(novi);
        message="Uspesno ste dodali rasadnik";
        
    }
    
    public void preRenderExecute(){
        message="";
        naziv="";
        mesto="";
        duzina="";
        sirina="";
    }
    
    
}
