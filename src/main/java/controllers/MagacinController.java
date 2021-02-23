/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Korisnik;
import beans.Magacin;
import beans.Poljoprivrednik;
import beans.Rasadnik;
import beans.Sadnica;
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
public class MagacinController implements Serializable{
    
    private List<Magacin> sadnice;
    private List<Magacin> preparati;
    private List<Rasadnik> rasadnici;
    
    private Poljoprivrednik currPoljoprivrednik;
    private Rasadnik izabranRasadnik;
    
    private List<Magacin> sadniceFilter;
    private List<Magacin> preparatiFilter;
    
    private List<Magacin> mojiMagacini;
    private List<Magacin> magaciniFilter;
    
    @PostConstruct
    public void init(){
        
       FacesContext context = FacesContext.getCurrentInstance();
       HttpSession session= (HttpSession) context.getExternalContext().getSession(false);
       Korisnik korisnik=(Korisnik) session.getAttribute("loggedInUser");
       currPoljoprivrednik=korisnik.getPoljoprivrednik();
       mojiMagacini = Magacin.dohvatiMagacinePoljoprivrednika(currPoljoprivrednik.getUsernamePoljo());
       }

    public List<Magacin> getMagaciniFilter() {
        return magaciniFilter;
    }

    public void setMagaciniFilter(List<Magacin> magaciniFilter) {
        this.magaciniFilter = magaciniFilter;
    }
    
    

    public List<Magacin> getMojiMagacini() {
        return mojiMagacini;
    }

    public void setMojiMagacini(List<Magacin> mojiMagacini) {
        this.mojiMagacini = mojiMagacini;
    }
    
    

    public List<Magacin> getSadniceFilter() {
        return sadniceFilter;
    }

    public void setSadniceFilter(List<Magacin> sadniceFilter) {
        this.sadniceFilter = sadniceFilter;
    }

    public List<Magacin> getPreparatiFilter() {
        return preparatiFilter;
    }

    public void setPreparatiFilter(List<Magacin> preparatiFilter) {
        this.preparatiFilter = preparatiFilter;
    }
    
    

    public Poljoprivrednik getCurrPoljoprivrednik() {
        return currPoljoprivrednik;
    }

    public void setCurrPoljoprivrednik(Poljoprivrednik currPoljoprivrednik) {
        this.currPoljoprivrednik = currPoljoprivrednik;
    }

    public Rasadnik getIzabranRasadnik() {
        return izabranRasadnik;
    }

    public void setIzabranRasadnik(Rasadnik izabranRasadnik) {
        this.izabranRasadnik = izabranRasadnik;
    }
    
    

    public List<Magacin> getSadnice() {
        return sadnice;
    }

    public void setSadnice(List<Magacin> sadnice) {
        this.sadnice = sadnice;
    }

    public List<Magacin> getPreparati() {
        return preparati;
    }

    public void setPreparati(List<Magacin> preparati) {
        this.preparati = preparati;
    }

    public List<Rasadnik> getRasadnici() {
        return rasadnici;
    }

    public void setRasadnici(List<Rasadnik> rasadnici) {
        this.rasadnici = rasadnici;
    }
    
    public void prikaziMagacin(Rasadnik rasadnik){
        izabranRasadnik=rasadnik;
        sadnice=Magacin.dohvatiSadniceIzMagacina(rasadnik.getIdRas());
        preparati=Magacin.dohvatiPreparateIzMagacina(rasadnik.getIdRas());
        
    }
    
    public void prerenderExecute(){
         mojiMagacini = Magacin.dohvatiMagacinePoljoprivrednika(currPoljoprivrednik.getUsernamePoljo());
      
    }
    
    
}
