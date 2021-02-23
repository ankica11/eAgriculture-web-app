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
import java.util.Date;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.inject.Named;

/**
 *
 * @author Ana
 */
@Named
@SessionScoped
@FacesConfig(version=FacesConfig.Version.JSF_2_3)
public class RegistracijaController implements Serializable{
    
    private String selektovanTip;
    
    private String ime;
    private String prezime;
    private String usernamePoljo;
    private String passwordPoljo;
    private Date datumRodjenja;
    private String mestoRodjenja;
    private String confirmPassPoljo;
    private String telefon;
    private String emailPoljo;
    private String message;
    
    private String naziv;
    private String passwordPred;
    private String confirmPassPred;
    private String usernamePred;
    private Date datumOsnivanja;
    private String mesto;
    private String emailPred;
    private String messageP;

    public String getSelektovanTip() {
        return selektovanTip;
    }

    public void setSelektovanTip(String selektovanTip) {
        this.selektovanTip = selektovanTip;
    }
    
    
    

    public String getMessageP() {
        return messageP;
    }

    public void setMessageP(String messageP) {
        this.messageP = messageP;
    }
    
    

    public String getConfirmPassPred() {
        return confirmPassPred;
    }

    public void setConfirmPassPred(String confirmPassPred) {
        this.confirmPassPred = confirmPassPred;
    }
    
    

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getPasswordPred() {
        return passwordPred;
    }

    public void setPasswordPred(String passwordPred) {
        this.passwordPred = passwordPred;
    }

    public String getUsernamePred() {
        return usernamePred;
    }

    public void setUsernamePred(String usernamePred) {
        this.usernamePred = usernamePred;
    }

    public Date getDatumOsnivanja() {
        return datumOsnivanja;
    }

    public void setDatumOsnivanja(Date datumOsnivanja) {
        this.datumOsnivanja = datumOsnivanja;
    }

    public String getMesto() {
        return mesto;
    }

    public void setMesto(String mesto) {
        this.mesto = mesto;
    }

    public String getEmailPred() {
        return emailPred;
    }

    public void setEmailPred(String emailPred) {
        this.emailPred = emailPred;
    }
    
    
    
    

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getUsernamePoljo() {
        return usernamePoljo;
    }

    public void setUsernamePoljo(String usernamePoljo) {
        this.usernamePoljo = usernamePoljo;
    }

    public String getPasswordPoljo() {
        return passwordPoljo;
    }

    public void setPasswordPoljo(String passwordPoljo) {
        this.passwordPoljo = passwordPoljo;
    }

    public Date getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(Date datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public String getMestoRodjenja() {
        return mestoRodjenja;
    }

    public void setMestoRodjenja(String mestoRodjenja) {
        this.mestoRodjenja = mestoRodjenja;
    }

    public String getConfirmPassPoljo() {
        return confirmPassPoljo;
    }

    public void setConfirmPassPoljo(String confirmPassPoljo) {
        this.confirmPassPoljo = confirmPassPoljo;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmailPoljo() {
        return emailPoljo;
    }

    public void setEmailPoljo(String emailPoljo) {
        this.emailPoljo = emailPoljo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public void registerPoljo(){
        
        Korisnik korisnik=Korisnik.dohvatiKorisnika(usernamePoljo);
        if(korisnik==null){
            if(!checkPasswords(passwordPoljo,confirmPassPoljo)){
                message="Lozinka i potvrdjena lozinka nisu iste!";
                return;
            }else{
                message="Uspesno ste se registrovali!";
                Korisnik newKorisnik=new Korisnik(usernamePoljo, passwordPoljo, "poljoprivrednik", 0);
                Poljoprivrednik newPoljoprivrednik=new Poljoprivrednik(newKorisnik,ime,prezime,datumRodjenja,mestoRodjenja,telefon,emailPoljo);
                Korisnik.sacuvajKorisnika(newKorisnik);
                Poljoprivrednik.sacuvajPoljoprivrednika(newPoljoprivrednik);
                return;
            }
        }else{
            message="Korsinicko ime je zauzeto";
        }
        
    }
    
    public boolean checkPasswords(String password, String confirmedPassword){
        boolean status=false;
        if(password.equals(confirmedPassword)){
            status = true;
        }
        return status;
    }
    
    
    public void registerPred(){
        
        Korisnik korisnik=Korisnik.dohvatiKorisnika(usernamePred);
        if(korisnik==null){
            if(!checkPasswords(passwordPred,confirmPassPred)){
                messageP="Lozinka i potvrdjena lozinka nisu iste!";
                return;
            }else{
                messageP="Uspesno ste se registrovali!";
                Korisnik newKorisnik=new Korisnik(usernamePred, passwordPred, "preduzetnik", 0);
                Preduzece newPreduzece = new Preduzece(newKorisnik,naziv,datumOsnivanja,mesto,emailPred,5);
                Korisnik.sacuvajKorisnika(newKorisnik);
                Preduzece.sacuvajPreduzece(newPreduzece);
                return;
            }
        }else{
            messageP="Korsinicko ime je zauzeto";
        }
        
    }
    
    public void prikazi(){
        
   
        
    }
    
    
    
}
