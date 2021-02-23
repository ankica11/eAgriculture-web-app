package beans;
// Generated 17.05.2020. 19.35.51 by Hibernate Tools 4.3.1


import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;

/**
 * Preduzece generated by hbm2java
 */
public class Preduzece  implements java.io.Serializable {


     private String usernamePred;
     private Korisnik korisnik;
     private String naziv;
     private Date datumOsnivanja;
     private String mesto;
     private String email;
     private int raspoloziviKuriri;
     private Set<Onlineprodavnica> onlineprodavnicas = new HashSet<Onlineprodavnica>(0);

    public Preduzece() {
    }

	
    public Preduzece(Korisnik korisnik, String naziv, Date datumOsnivanja, String mesto, String email) {
        this.korisnik = korisnik;
        this.naziv = naziv;
        this.datumOsnivanja = datumOsnivanja;
        this.mesto = mesto;
        this.email = email;
    }
     public Preduzece(Korisnik korisnik, String naziv, Date datumOsnivanja, String mesto, String email, int raspoloziviKuriri) {
        this.korisnik = korisnik;
        this.naziv = naziv;
        this.datumOsnivanja = datumOsnivanja;
        this.mesto = mesto;
        this.email = email;
        this.raspoloziviKuriri = raspoloziviKuriri;
    }
    public Preduzece(Korisnik korisnik, String naziv, Date datumOsnivanja, String mesto, String email, Set<Onlineprodavnica> onlineprodavnicas) {
       this.korisnik = korisnik;
       this.naziv = naziv;
       this.datumOsnivanja = datumOsnivanja;
       this.mesto = mesto;
       this.email = email;
       this.onlineprodavnicas = onlineprodavnicas;
    }
   
    public String getUsernamePred() {
        return this.usernamePred;
    }
    
    public void setUsernamePred(String usernamePred) {
        this.usernamePred = usernamePred;
    }
    public Korisnik getKorisnik() {
        return this.korisnik;
    }
    
    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }
    public String getNaziv() {
        return this.naziv;
    }
    
    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
    public Date getDatumOsnivanja() {
        return this.datumOsnivanja;
    }
    
    public void setDatumOsnivanja(Date datumOsnivanja) {
        this.datumOsnivanja = datumOsnivanja;
    }
    public String getMesto() {
        return this.mesto;
    }
    
    public void setMesto(String mesto) {
        this.mesto = mesto;
    }
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public int getRaspoloziviKuriri() {
        return raspoloziviKuriri;
    }

    public void setRaspoloziviKuriri(int raspoloziviKuriri) {
        this.raspoloziviKuriri = raspoloziviKuriri;
    }
    
    
    public Set<Onlineprodavnica> getOnlineprodavnicas() {
        return this.onlineprodavnicas;
    }
    
    public void setOnlineprodavnicas(Set<Onlineprodavnica> onlineprodavnicas) {
        this.onlineprodavnicas = onlineprodavnicas;
    }

    public static void sacuvajPreduzece(Preduzece p){
          SessionFactory sf=HibernateUtil.getSessionFactory();
          Session session = sf.openSession();
          session.beginTransaction();
        
          session.save(p);
          session.getTransaction().commit();
          session.close();
    }
    
    public static void azurirajPreduzece(Preduzece preduzece){
          SessionFactory sf=HibernateUtil.getSessionFactory();
          Session session = sf.openSession();
          session.beginTransaction();
        
          session.update(preduzece);
          session.getTransaction().commit();
          session.close();
    }
    
    public static List<Preduzece> dohvatiSvaPreduzeca(){
        List<Preduzece> preduzeca=new ArrayList<Preduzece>();
          SessionFactory sf=HibernateUtil.getSessionFactory();
          Session session = sf.openSession();
          session.beginTransaction();
        
          String upit="from Preduzece p where p.korisnik.status=1";
          Query query = session.createQuery(upit);
          preduzeca=query.list();
          
          session.getTransaction().commit();
          session.close();
          return preduzeca;
    }

        


}


