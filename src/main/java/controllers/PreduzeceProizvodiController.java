/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Korisnik;
import beans.Ocene;
import beans.Onlineprodavnica;
import beans.Preduzece;
import java.io.Serializable;
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
@FacesConfig(version=FacesConfig.Version.JSF_2_3)
public class PreduzeceProizvodiController implements Serializable{
    
    
    private Preduzece currPreduzece;
    private List<Onlineprodavnica> proizvodi;
    
    private Onlineprodavnica izabranProizvod;
    private List<Ocene> ocene;
    
    private LinkedList<SelectItem> proizvodiItems;
    private Integer dopunjenProizvod;
    private String kolicina;
    
    @PostConstruct
    public void init(){
       FacesContext context = FacesContext.getCurrentInstance();
       HttpSession session= (HttpSession) context.getExternalContext().getSession(false);
       Korisnik korisnik=(Korisnik) session.getAttribute("loggedInUser");
       currPreduzece=korisnik.getPreduzece();
       proizvodi=Onlineprodavnica.dohvatiProizvodePreduzeca(currPreduzece.getUsernamePred());
       proizvodiItems = Onlineprodavnica.proizvodiItems(currPreduzece.getUsernamePred());
      
    }

    public String getKolicina() {
        return kolicina;
    }

    public void setKolicina(String kolicina) {
        this.kolicina = kolicina;
    }

    
  

    public Integer getDopunjenProizvod() {
        return dopunjenProizvod;
    }

    public void setDopunjenProizvod(Integer dopunjenProizvod) {
        this.dopunjenProizvod = dopunjenProizvod;
    }

    
    
    

   
    
    

    public LinkedList<SelectItem> getProizvodiItems() {
        return proizvodiItems;
    }

    public void setProizvodiItems(LinkedList<SelectItem> proizvodiItems) {
        this.proizvodiItems = proizvodiItems;
    }
    
    

    public List<Ocene> getOcene() {
        return ocene;
    }

    public void setOcene(List<Ocene> ocene) {
        this.ocene = ocene;
    }

    
    
    

    public Preduzece getCurrPreduzece() {
        return currPreduzece;
    }

    public void setCurrPreduzece(Preduzece currPreduzece) {
        this.currPreduzece = currPreduzece;
    }

    public List<Onlineprodavnica> getProizvodi() {
        return proizvodi;
    }

    public void setProizvodi(List<Onlineprodavnica> proizvodi) {
        this.proizvodi = proizvodi;
    }

    public Onlineprodavnica getIzabranProizvod() {
        return izabranProizvod;
    }

    public void setIzabranProizvod(Onlineprodavnica izabranProizvod) {
        this.izabranProizvod = izabranProizvod;
    }
    
    public String prikaziDetalje(Onlineprodavnica proizvod){
        izabranProizvod=proizvod;
        ocene=Ocene.dohvatiOcene(proizvod.getIdPro());
        return "detaljiProizvod";
    }
    
    public void povuciIzProdaje(Onlineprodavnica proizvod){
        proizvod.setPovucen(1);
        Onlineprodavnica.azurirajProizvod(proizvod);
        
    }
    
    public void dodaj(){
        Onlineprodavnica proizvod=Onlineprodavnica.dohvatiProizvodPoId(dopunjenProizvod);
        int novaKolicina=Integer.valueOf(kolicina) + Integer.valueOf(proizvod.getKolicina());
        proizvod.setKolicina(novaKolicina+"");
        Onlineprodavnica.azurirajProizvod(proizvod);
        
    }
    
    public void preRenderExecute(){
        proizvodi=Onlineprodavnica.dohvatiProizvodePreduzeca(currPreduzece.getUsernamePred());
         proizvodiItems = Onlineprodavnica.proizvodiItems(currPreduzece.getUsernamePred());
    }
    
}
