/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Korisnik;
import beans.Narudzbina;
import beans.Onlineprodavnica;
import beans.Poljoprivrednik;
import beans.Rasadnik;
import beans.Sadnica;
import beans.Stavka;
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
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
public class NarudzbinePoljoprivrednikController implements Serializable{
    
    private List<Rasadnik> rasadnici;
    
    private Rasadnik izabranRasadnik;
    private List<Stavka> stavke;
    
    
    private List<Stavka> naruceniProizvodi;
    private List<Stavka> proizvodiFilter;
    private Poljoprivrednik curPoljoprivrednik;
    
    @PostConstruct
    public void init(){
       FacesContext context = FacesContext.getCurrentInstance();
       HttpSession session= (HttpSession) context.getExternalContext().getSession(false);
       Korisnik korisnik=(Korisnik) session.getAttribute("loggedInUser");
       curPoljoprivrednik=korisnik.getPoljoprivrednik();
       naruceniProizvodi=Stavka.dohvatiNaruceneProizvode(korisnik.getPoljoprivrednik().getUsernamePoljo());
    }

    public Poljoprivrednik getCurPoljoprivrednik() {
        return curPoljoprivrednik;
    }

    public void setCurPoljoprivrednik(Poljoprivrednik curPoljoprivrednik) {
        this.curPoljoprivrednik = curPoljoprivrednik;
    }
    

    public List<Stavka> getProizvodiFilter() {
        return proizvodiFilter;
    }

    public void setProizvodiFilter(List<Stavka> proizvodiFilter) {
        this.proizvodiFilter = proizvodiFilter;
    }
    
    

    public List<Stavka> getNaruceniProizvodi() {
        return naruceniProizvodi;
    }

    public void setNaruceniProizvodi(List<Stavka> naruceniProizvodi) {
        this.naruceniProizvodi = naruceniProizvodi;
    }
    
    

    public Rasadnik getIzabranRasadnik() {
        return izabranRasadnik;
    }

    public void setIzabranRasadnik(Rasadnik izabranRasadnik) {
        this.izabranRasadnik = izabranRasadnik;
    }
    
    

    public List<Rasadnik> getRasadnici() {
        return rasadnici;
    }

    public void setRasadnici(List<Rasadnik> rasadnici) {
        this.rasadnici = rasadnici;
    }

    public List<Stavka> getStavke() {
        return stavke;
    }

    public void setStavke(List<Stavka> stavke) {
        this.stavke = stavke;
    }
    
    
    
    
    public void prikaziNarudzbine(Rasadnik rasadnik){
        izabranRasadnik=rasadnik;
        stavke=Stavka.dohvatiStavke(rasadnik.getIdRas());
    }
    
    
    public void otkaziNarudzbinu(Stavka stavka){
        Stavka.obrisiStavku(stavka);
        Onlineprodavnica proizvod = stavka.getOnlineprodavnica();
        int kolicina1=Integer.valueOf(proizvod.getKolicina())+stavka.getKolicina();
        proizvod.setKolicina(kolicina1+"");
        Onlineprodavnica.azurirajProizvod(proizvod);
        naruceniProizvodi.remove(stavka);
        if(Stavka.dohvatiStavkeNarudzbine(stavka.getNarudzbina().getIdN()).isEmpty()){
            Narudzbina.obrisiNarudzbinu(stavka.getNarudzbina());
        }
        
    }
    
    public void preRenderExecute(){
        naruceniProizvodi=Stavka.dohvatiNaruceneProizvode(curPoljoprivrednik.getUsernamePoljo());
    }
    
    
    
}
