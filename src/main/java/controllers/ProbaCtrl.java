/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Korisnik;
import java.io.Serializable;
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
public class ProbaCtrl implements Serializable{
    
    private Korisnik korisnik=Korisnik.dohvatiKorisnika("tamara");

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }
    
    
}
