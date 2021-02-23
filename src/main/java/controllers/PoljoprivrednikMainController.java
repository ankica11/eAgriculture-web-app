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
import beans.SadnicaMesto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.PrimeFaces;
import org.primefaces.event.CloseEvent;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

/**
 *
 * @author Ana
 */
@Named
@SessionScoped
@FacesConfig(version=FacesConfig.Version.JSF_2_3)
public class PoljoprivrednikMainController implements Serializable{
    private Poljoprivrednik curPoljoprivrednik;
    private List<Rasadnik> rasadnici;
    private UIComponent component;
    
    private Rasadnik izabranRasadnik;
    private List<Sadnica> sadniceIzabranogRasadnika;
    private List<SadnicaMesto> sadniceMesta;
    private List<Magacin> sadniceMagacin;
    private List<Magacin> preparatiMagacin;
    private SadnicaMesto izabranoMesto;
    private Sadnica izabranaSadnica;
    
    private DashboardModel model;
    
    private List<String> kriticniRasadnici;
    
  
    
    
    @PostConstruct
    public void init(){
       FacesContext context = FacesContext.getCurrentInstance();
       HttpSession session= (HttpSession) context.getExternalContext().getSession(false);
       Korisnik korisnik=(Korisnik) session.getAttribute("loggedInUser");
       curPoljoprivrednik=korisnik.getPoljoprivrednik();
       rasadnici=Rasadnik.dohvatiSveRasadnike(curPoljoprivrednik.getUsernamePoljo());
       sadniceMesta=new ArrayList<SadnicaMesto>();
       model = new DefaultDashboardModel();
       DashboardColumn column1 = new DefaultDashboardColumn();
       column1.addWidget("temperatura");
       column1.addWidget("voda");
       model.addColumn(column1);
       kriticniRasadnici=Rasadnik.dohvatiKriticneRasadnike(curPoljoprivrednik.getUsernamePoljo());
      
       
    }

    public List<String> getKriticniRasadnici() {
        return kriticniRasadnici;
    }

    public void setKriticniRasadnici(List<String> kriticniRasadnici) {
        this.kriticniRasadnici = kriticniRasadnici;
    }
    
    

    public DashboardModel getModel() {
        return model;
    }

    public void setModel(DashboardModel model) {
        this.model = model;
    }

  
    
    
    

    public Sadnica getIzabranaSadnica() {
        return izabranaSadnica;
    }

    public void setIzabranaSadnica(Sadnica izabranaSadnica) {
        this.izabranaSadnica = izabranaSadnica;
    }
    
    

   
    

    public SadnicaMesto getIzabranoMesto() {
        return izabranoMesto;
    }

    public void setIzabranoMesto(SadnicaMesto izabranoMesto) {
        this.izabranoMesto = izabranoMesto;
    }
    
    

    public List<Magacin> getSadniceMagacin() {
        return sadniceMagacin;
    }

    public void setSadniceMagacin(List<Magacin> sadniceMagacin) {
        this.sadniceMagacin = sadniceMagacin;
    }

    public List<Magacin> getPreparatiMagacin() {
        return preparatiMagacin;
    }

    public void setPreparatiMagacin(List<Magacin> preparatiMagacin) {
        this.preparatiMagacin = preparatiMagacin;
    }
    
    
    public List<SadnicaMesto> getSadniceMesta() {
        return sadniceMesta;
    }

    public void setSadniceMesta(List<SadnicaMesto> sadniceMesta) {
        this.sadniceMesta = sadniceMesta;
    }

   
    
    

    public List<Sadnica> getSadniceIzabranogRasadnika() {
        return sadniceIzabranogRasadnika;
    }

    public void setSadniceIzabranogRasadnika(List<Sadnica> sadniceIzabranogRasadnika) {
        this.sadniceIzabranogRasadnika = sadniceIzabranogRasadnika;
    }
    
    

    public Rasadnik getIzabranRasadnik() {
        return izabranRasadnik;
    }

    public void setIzabranRasadnik(Rasadnik izabranRasadnik) {
        this.izabranRasadnik = izabranRasadnik;
    }
    
    

    public Poljoprivrednik getCurPoljoprivrednik() {
        return curPoljoprivrednik;
    }

    public void setCurPoljoprivrednik(Poljoprivrednik curPoljoprivrednik) {
        this.curPoljoprivrednik = curPoljoprivrednik;
    }

    public List<Rasadnik> getRasadnici() {
        return rasadnici;
    }

    public void setRasadnici(List<Rasadnik> rasadnici) {
        this.rasadnici = rasadnici;
    }

    public UIComponent getComponent() {
        return component;
    }

    public void setComponent(UIComponent component) {
        this.component = component;
    }
    
    
    public String prikaziDetalje(Rasadnik rasadnik){
        izabranRasadnik=rasadnik;
        sadniceIzabranogRasadnika=Sadnica.dohvatiSveSadnice(rasadnik.getIdRas());
        sadniceMagacin=Magacin.dohvatiSadniceIzMagacina(rasadnik.getIdRas());
        preparatiMagacin=Magacin.dohvatiPreparateIzMagacina(rasadnik.getIdRas());
        List<SadnicaMesto> pomocna=new ArrayList<SadnicaMesto>();
        for(int i=0; i<rasadnik.getDuzina();i++)
            for(int j=0; j<rasadnik.getSirina();j++){
                Sadnica s=Sadnica.dohvatiSadnicu(i, j, rasadnik.getIdRas());
                SadnicaMesto sm = new SadnicaMesto();
                sm.setRow_index(i);
                sm.setCol_index(j);
                sm.setSadnica(s);
                pomocna.add(sm);
            }
        sadniceMesta=pomocna;
        return "detaljiRasadnik";
    }
    
    public void zasadiSadnicu(Magacin m){
        Sadnica.dodajSadnicu(m, izabranoMesto.getRow_index(), izabranoMesto.getCol_index());
        Magacin.magacinUpdate(m);
        izabranRasadnik.setBrZasadjenih(izabranRasadnik.getBrZasadjenih()+1);
        izabranRasadnik.setBrSlobodnih(izabranRasadnik.getBrSlobodnih()-1);
        Rasadnik.azurirajRasadnik(izabranRasadnik);
        int index = sadniceMesta.indexOf(izabranoMesto);
        izabranoMesto.setSadnica(new Sadnica(m,0,izabranoMesto.getRow_index(),izabranoMesto.getCol_index(),0));
        izabranaSadnica=new Sadnica(m,0,izabranoMesto.getRow_index(),izabranoMesto.getCol_index(),0);
        sadniceMesta.remove(index);
        sadniceMesta.add(index, izabranoMesto);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(component.getClientId(), new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Uspesno ste zasadnili sadnicu!"));
             
        
    }
    
   
    
    public void izvadiSadnicu(){
        //tek posle 24 h mesto treba da postane zvanicno slobodno!!!!!!
        //vadjenje izabrane sadnice ako je postala spremna
        izabranRasadnik.setBrZasadjenih(izabranRasadnik.getBrZasadjenih()-1);
        izabranRasadnik.setBrSlobodnih(izabranRasadnik.getBrSlobodnih()+1);
        Rasadnik.azurirajRasadnik(izabranRasadnik);
        izabranaSadnica.setVremeDoVadjenja(24);
        Sadnica.azurirajSadnicu(izabranaSadnica);
        
             
        
    }
    
    public void dodajPreparat(Magacin preparat){
        
        izabranaSadnica.setNapredak(izabranaSadnica.getNapredak()+preparat.getOnlineprodavnica().getUputstvo());
        Sadnica.azurirajSadnicu(izabranaSadnica);
        Magacin.magacinUpdate(preparat);
        FacesContext context = FacesContext.getCurrentInstance();
       context.addMessage(component.getClientId(), new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Ubrzan je napredak sadnice : "+izabranaSadnica.getMagacin().getOnlineprodavnica().getNaziv()+" je uspesno izvadjena"+"!\nRed: "+izabranoMesto.getRow_index()+" Kolona: "+izabranoMesto.getCol_index()+" !"));
      
        
    }
    
    public void handleClose(CloseEvent event) {
        izabranoMesto=null;
    }
    
    public void prihvati(SadnicaMesto mesto){
        izabranoMesto=mesto;
        izabranaSadnica=mesto.getSadnica();
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('sadnicaInfo').show();");
    }
    
    public int trenutniNapredak(){
        int ret = izabranaSadnica.getNapredak()*100/izabranaSadnica.getMagacin().getOnlineprodavnica().getUputstvo();
        return ret;
    }
   
   public void povecajTemp(){
       Rasadnik.povecajTemp(izabranRasadnik);
       //izabranRasadnik.setTemperatura(izabranRasadnik.getTemperatura().add(BigDecimal.ONE));
       if(izabranRasadnik.getTemperatura().compareTo(BigDecimal.valueOf(12))>=0 && izabranRasadnik.getVoda()>=75){
           kriticniRasadnici.remove(izabranRasadnik.getNaziv());
       }
       
   }
   public void smanjiTemp(){
       Rasadnik.smanjiTemp(izabranRasadnik);
       if(izabranRasadnik.getTemperatura().compareTo(BigDecimal.valueOf(12))<0 || izabranRasadnik.getVoda()<75){
            if(!kriticniRasadnici.contains(izabranRasadnik.getNaziv())){
            kriticniRasadnici.add(izabranRasadnik.getNaziv());}
       }
       /*if(izabranRasadnik.getTemperatura().equals(BigDecimal.valueOf(11)) && izabranRasadnik.getVoda()>75){
           posaljiMejl(curPoljoprivrednik, izabranRasadnik);
           
       }*/
       }
       //izabranRasadnik.setTemperatura(izabranRasadnik.getTemperatura().subtract(BigDecimal.ONE));
   
   public void dodajVodu(){
       Rasadnik.dodajVodu(izabranRasadnik);
       //izabranRasadnik.setVoda(izabranRasadnik.getVoda()+1);
       if(izabranRasadnik.getVoda()>=75 && (izabranRasadnik.getTemperatura().compareTo(BigDecimal.valueOf(12))>=0)){
           kriticniRasadnici.remove(izabranRasadnik.getNaziv());
       }
       
   }
   public void smanjiVodu(){
       Rasadnik.smanjiVodu(izabranRasadnik);
       if(izabranRasadnik.getTemperatura().compareTo(BigDecimal.valueOf(12))<0 || izabranRasadnik.getVoda()<75){
          if(!kriticniRasadnici.contains(izabranRasadnik.getNaziv())){
           kriticniRasadnici.add(izabranRasadnik.getNaziv());}
       }
       /*if(izabranRasadnik.getTemperatura().compareTo(BigDecimal.valueOf(12))>=0 && izabranRasadnik.getVoda()==74){
           posaljiMejl(curPoljoprivrednik, izabranRasadnik);
           
       }*/
       //izabranRasadnik.setVoda(izabranRasadnik.getVoda()-1);
   }
   
   public void preRenderExecute(){
       rasadnici=Rasadnik.dohvatiSveRasadnike(curPoljoprivrednik.getUsernamePoljo());
       kriticniRasadnici=Rasadnik.dohvatiKriticneRasadnike(curPoljoprivrednik.getUsernamePoljo());
   }
    
   
       
    public void posaljiMejl(Poljoprivrednik poljoprivrednik, Rasadnik rasadnik){
      Email email = (Email) EmailBuilder.startingBlank()
              
              .to(poljoprivrednik.getEmail())
              .withSubject("Vazno obavestenje o rasadniku")
              .withPlainText("Vas rasadnik" + rasadnik.getNaziv() + "zahteva hitno odrzavanje")
              .buildEmail();
      
      Mailer mailer=MailerBuilder
              .withSMTPServer("smtp.gmail.com", 587)
              .withTransportStrategy(TransportStrategy.SMTP_TLS)
              .buildMailer();
      
      mailer.sendMail(email);
        
    }
    
}
