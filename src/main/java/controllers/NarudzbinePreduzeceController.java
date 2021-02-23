/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Korisnik;
import beans.Narudzbina;
import beans.Onlineprodavnica;
import beans.Preduzece;
import beans.Stavka;
import java.io.Serializable;
import java.util.ArrayList;
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
public class NarudzbinePreduzeceController implements Serializable{
    
    
    private Preduzece currPreduzece;
    private List<Narudzbina> narudzbine;
 
    private String message;
    
    @PostConstruct
    public void init(){
       FacesContext context = FacesContext.getCurrentInstance();
       HttpSession session= (HttpSession) context.getExternalContext().getSession(false);
       Korisnik korisnik=(Korisnik) session.getAttribute("loggedInUser");
       currPreduzece = korisnik.getPreduzece();
       narudzbine=Narudzbina.dohvatiNarudzbinePreduzeca(currPreduzece.getUsernamePred());
       
        
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

    public List<Narudzbina> getNarudzbine() {
        return narudzbine;
    }

    public void setNarudzbine(List<Narudzbina> narudzbine) {
        this.narudzbine = narudzbine;
    }
    
    public void odbijNarudzbinu(Narudzbina narudzbina){
        List<Stavka> stavkeNarudzbine = Stavka.dohvatiStavkeNarudzbine(narudzbina.getIdN());
        for(int i=0; i<stavkeNarudzbine.size(); i++){
            if(stavkeNarudzbine.get(i).getOnlineprodavnica().getPreduzece().getUsernamePred().equals(currPreduzece.getUsernamePred())){
            Onlineprodavnica proizvod = stavkeNarudzbine.get(i).getOnlineprodavnica();
            int kolicina1=Integer.valueOf(proizvod.getKolicina())+stavkeNarudzbine.get(i).getKolicina();
            proizvod.setKolicina(kolicina1+"");
            Onlineprodavnica.azurirajProizvod(proizvod);
            Stavka.obrisiStavku(stavkeNarudzbine.get(i));
            stavkeNarudzbine.remove(stavkeNarudzbine.get(i));

            if(stavkeNarudzbine.isEmpty()){
                Narudzbina.obrisiNarudzbinu(narudzbina);
            }
            }
            
        }
        narudzbine.remove(narudzbina);
    }
    
    public void uposliKurira(Narudzbina narudzbina){
      //dohvatamo sve stavke konkretne narudzbine konkretnig preduzeca
        List<Stavka> mojestavke = Stavka.stavkePreduzeca(narudzbina.getIdN(), currPreduzece.getUsernamePred());
       
        if(currPreduzece.getRaspoloziviKuriri()==0){
            
            
            //nema trenutno kurira na raspolaganju sve stavke narudzbine dobijaju status na cekanju
            for(int i=0; i<mojestavke.size(); i++){
                mojestavke.get(i).setStatus("na cekanju");
                Stavka.azurirajStavku(mojestavke.get(i));
               
               
                
            }
        }else{
            currPreduzece.setRaspoloziviKuriri(currPreduzece.getRaspoloziviKuriri()-1);
            Preduzece.azurirajPreduzece(currPreduzece);
            //treba da se izracuna potrebno vreme za isporuku narudzbine
            int vreme=odrediVremeIsporuke();
            for(int i=0; i<mojestavke.size(); i++){
                mojestavke.get(i).setStatus("u toku");
                mojestavke.get(i).setVremeIsporuke(vreme);
                Stavka.azurirajStavku(mojestavke.get(i));
            }
           
            narudzbine.remove(narudzbina);
        }
    }
    
    
    public int odrediVremeIsporuke(){
        int ret = (int) (Math.random()*5+5);
        return ret;
    }
    
    public String odrediStatus(Narudzbina n){
        return Stavka.status(n.getIdN(), currPreduzece.getUsernamePred());
    }
    
    public void preRenderExecute(){
        narudzbine=Narudzbina.dohvatiNarudzbinePreduzeca(currPreduzece.getUsernamePred());
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session= (HttpSession) context.getExternalContext().getSession(false);
        Korisnik korisnik=(Korisnik) session.getAttribute("loggedInUser");
        currPreduzece = korisnik.getPreduzece();
      
    }
    
    
    
    
 
}
