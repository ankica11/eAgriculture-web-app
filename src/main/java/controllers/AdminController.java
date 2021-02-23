/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Korisnik;
import beans.Poljoprivrednik;
import beans.Preduzece;
import java.io.Serializable;
import java.util.List;
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
public class AdminController implements Serializable{
    
    
    private List<Korisnik> korisnici=Korisnik.dohvatiSveKorisnike();
    private Korisnik currAdmin;
    private Korisnik izabranKorisnik;
    
    private Korisnik novi=new Korisnik();
    private Poljoprivrednik noviPoljo = new Poljoprivrednik();
    private Preduzece novoPred = new Preduzece();
    private String novTip;
    
    private String message;
    
    private List<Korisnik> registrovaniKorisnici;
    private String selektovanTip;
    private List<Poljoprivrednik> poljoprivrednici;
    private List<Preduzece> preduzeca;
    
    
    @PostConstruct
    public void init(){
        
       FacesContext context = FacesContext.getCurrentInstance();
       HttpSession session= (HttpSession) context.getExternalContext().getSession(false);
       currAdmin =(Korisnik) session.getAttribute("loggedInUser");
       poljoprivrednici=Poljoprivrednik.dohvatiPoljoprivrednike();
       preduzeca=Preduzece.dohvatiSvaPreduzeca();
       registrovaniKorisnici=Korisnik.dohvatiRegistrovaneKorisnike();
      
       
      
        
    }

    public Poljoprivrednik getNoviPoljo() {
        return noviPoljo;
    }

    public void setNoviPoljo(Poljoprivrednik noviPoljo) {
        this.noviPoljo = noviPoljo;
    }

    public Preduzece getNovoPred() {
        return novoPred;
    }

    public void setNovoPred(Preduzece novoPred) {
        this.novoPred = novoPred;
    }

    public String getNovTip() {
        return novTip;
    }

    public void setNovTip(String novTip) {
        this.novTip = novTip;
    }
    
    

    public List<Poljoprivrednik> getPoljoprivrednici() {
        return poljoprivrednici;
    }

    public void setPoljoprivrednici(List<Poljoprivrednik> poljoprivrednici) {
        this.poljoprivrednici = poljoprivrednici;
    }

    public List<Preduzece> getPreduzeca() {
        return preduzeca;
    }

    public void setPreduzeca(List<Preduzece> preduzeca) {
        this.preduzeca = preduzeca;
    }
    
    

    public String getSelektovanTip() {
        return selektovanTip;
    }

    public void setSelektovanTip(String selektovanTip) {
        this.selektovanTip = selektovanTip;
    }
    
    

    public List<Korisnik> getRegistrovaniKorisnici() {
        return registrovaniKorisnici;
    }

    public void setRegistrovaniKorisnici(List<Korisnik> registrovaniKorisnici) {
        this.registrovaniKorisnici = registrovaniKorisnici;
    }
    
    

    public Korisnik getNovi() {
        return novi;
    }

    public void setNovi(Korisnik novi) {
        this.novi = novi;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    

    public Korisnik getIzabranKorisnik() {
        return izabranKorisnik;
    }

    public void setIzabranKorisnik(Korisnik izabranKorisnik) {
        this.izabranKorisnik = izabranKorisnik;
    }
    
    

    public Korisnik getCurrAdmin() {
        return currAdmin;
    }

    public void setCurrAdmin(Korisnik currAdmin) {
        this.currAdmin = currAdmin;
    }
    
    

    public List<Korisnik> getKorisnici() {
        return korisnici;
    }

    public void setKorisnici(List<Korisnik> korisnici) {
        this.korisnici = korisnici;
    }
    
    
    public void prihvatiZahtev(Korisnik k){
         Korisnik.prihvatiZahtev(k);
       
         korisnici.remove(k);
        
    }
    
    public void odbijZahtev(Korisnik k){
        Korisnik.odbijZahtev(k);
        //Korisnik.obrisiKorisnika(k);
        korisnici.remove(k);
    }
    
    public void azurirajPoljo(Poljoprivrednik p){
        Poljoprivrednik.azuriraj(p);
        
    }
    public void azurirajPreduzece(Preduzece p){
        Preduzece.azurirajPreduzece(p);
    }
    
    public void dodaj(){
        novi.setStatus(1);
        novi.setTip(selektovanTip);
        Korisnik.sacuvajKorisnika(novi);
        if(selektovanTip.equals("poljoprivrednik")){
            noviPoljo.setKorisnik(novi);
            Poljoprivrednik.sacuvajPoljoprivrednika(noviPoljo);
        }
        if(selektovanTip.equals("preduzetnik")){
            novoPred.setKorisnik(novi);
            Preduzece.sacuvajPreduzece(novoPred);
        }
        
        message="Uspešno ste dodali korisnika";
    }
    
    public void obrisi(Korisnik k){
        k.setStatus(2);
        Korisnik.azurirajKorisnika(k);
        registrovaniKorisnici.remove(k);
    }
    
    public void prikazi(){
        
    }

    
}
