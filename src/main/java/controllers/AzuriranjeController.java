/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Magacin;
import beans.Narudzbina;
import beans.Poljoprivrednik;
import beans.Preduzece;
import beans.Rasadnik;
import beans.Sadnica;
import beans.Stavka;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.inject.Named;
import javax.mail.*;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;


/**
 *
 * @author Ana
 */
@Named
@SessionScoped
@FacesConfig(version=FacesConfig.Version.JSF_2_3)
public class AzuriranjeController implements Serializable{
    
    
    
    public void azurirajSveRasadnike(){
        List<Rasadnik> rasadnici=new ArrayList<Rasadnik>();
        rasadnici = Rasadnik.dohvatiRasadnikeSistema();
        for(int i=0; i<rasadnici.size(); i++){
            Rasadnik r=rasadnici.get(i);
            if(r.getVoda()>0){
            r.setVoda(r.getVoda()-1);
            }
            if(r.getTemperatura().compareTo(BigDecimal.ZERO)>0){
            r.setTemperatura(r.getTemperatura().subtract(BigDecimal.valueOf(0.5)));
            }
            Rasadnik.azurirajRasadnik(r);
           /* if(r.getVoda()==74 || (r.getTemperatura().equals(BigDecimal.valueOf(11)))){
                posaljiMejl(r.getPoljoprivrednik(), r);
                
            }*/
        }
    }
    
    
    public void azurirajVremena(){
       List<Stavka> stavke= Stavka.dohvatiSveStavke();
       for(int i=0; i<stavke.size(); i++){
           Stavka s=stavke.get(i);
           if(s.getVremeIsporuke()==0){
               //isporucena
               s.setStatus("isporuceno");
               Stavka.azurirajStavku(s);
               Magacin.istovariRobu(s.getNarudzbina().getRasadnik(), s.getOnlineprodavnica(), s.getKolicina());
               if(Narudzbina.proveraIsporukeNarudzbine(s.getNarudzbina().getIdN(), s.getOnlineprodavnica().getPreduzece().getUsernamePred())){
                   //samo ako je ovo poslednja isporucena stavka sa narudzbine ovog preduzeca se povecava broj kurira
                   Preduzece p=s.getOnlineprodavnica().getPreduzece();
                   p.setRaspoloziviKuriri(p.getRaspoloziviKuriri()+1);
                   Preduzece.azurirajPreduzece(p);
               }
               stavke.remove(s);
           }else{
               s.setVremeIsporuke(s.getVremeIsporuke()-1);
               Stavka.azurirajStavku(s);
           }
           
       }
       
    }
    
    public void azurirajVremenaSadnica(){
        List<Sadnica> sadniceZaVadjenje=Sadnica.dohvatiIzvadjeneSadnice();
        for(Sadnica s : sadniceZaVadjenje){
            s.setVremeDoVadjenja(s.getVremeDoVadjenja()-1);
            if(s.getVremeDoVadjenja()==0){
                Sadnica.izvadiSadnicu(s);
            }else{
                Sadnica.azurirajSadnicu(s);
            }
        }
    }
    
    public void posaljiMejl(Poljoprivrednik poljoprivrednik, Rasadnik rasadnik){
      Email email = (Email) EmailBuilder.startingBlank()
              .from("rtiposta@gmail.com")
              .to(poljoprivrednik.getEmail())
              .withSubject("Vazno obavestenje o rasadniku")
              .withPlainText("Vas rasadnik" + rasadnik.getNaziv() + "zahteva hitno odrzavanje")
              .buildEmail();
      
      Mailer mailer=MailerBuilder.buildMailer();
      
      mailer.sendMail(email);
        
    }
    
}
