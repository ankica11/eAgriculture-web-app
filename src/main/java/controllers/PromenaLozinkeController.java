/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Korisnik;
import java.io.Serializable;
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
public class PromenaLozinkeController implements Serializable{
    
    private String staraLozinka;
    private String novaLozinka;
    private String potvrdaNove;
    
    
    private Korisnik currKorisnik;
    private String message;
    private boolean status=false;
    
    @PostConstruct
    public void init(){
       FacesContext context = FacesContext.getCurrentInstance();
       HttpSession session= (HttpSession) context.getExternalContext().getSession(false);
       currKorisnik=(Korisnik) session.getAttribute("loggedInUser");
      
        
    }

    public String getStaraLozinka() {
        return staraLozinka;
    }

    public void setStaraLozinka(String staraLozinka) {
        this.staraLozinka = staraLozinka;
    }

    public String getNovaLozinka() {
        return novaLozinka;
    }

    public void setNovaLozinka(String novaLozinka) {
        this.novaLozinka = novaLozinka;
    }

    public String getPotvrdaNove() {
        return potvrdaNove;
    }

    public void setPotvrdaNove(String potvrdaNove) {
        this.potvrdaNove = potvrdaNove;
    }

    public Korisnik getCurrKorisnik() {
        return currKorisnik;
    }

    public void setCurrKorisnik(Korisnik currKorisnik) {
        this.currKorisnik = currKorisnik;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    public void promeni(){
        if(staraLozinka.equals(currKorisnik.getUsername())){
            status=true;
        }else{
            status=false;
            message="Pogrešna lozinka!";
        }
    }
    
    public String potvrdi(){
        String ret=null;
         if(!staraLozinka.equals(currKorisnik.getPassword())){
             message="Pogrešna lozinka";
             return null;
         }
        
        if(novaLozinka.equals(currKorisnik.getPassword())){
            message="Nova i stara lozinka moraju da se razlikuju!";
            
        }else{
            if(!novaLozinka.equals(potvrdaNove)){
                message="Nova i ponovljena lozinka se razlikuju!";
                
            }else{
                currKorisnik.setPassword(novaLozinka);
                Korisnik.azurirajKorisnika(currKorisnik);
                message="Uspešno ste promenili lozinku";
                
            }
        }
        return ret;
    }
    
    public String logout(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        session.invalidate();
        return "login";
    }
    
    
}
