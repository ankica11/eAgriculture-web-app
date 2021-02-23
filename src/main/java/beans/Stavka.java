package beans;
// Generated 17.05.2020. 19.35.51 by Hibernate Tools 4.3.1

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;




/**
 * Stavka generated by hbm2java
 */
public class Stavka  implements java.io.Serializable {


     private Integer idSta;
     private Narudzbina narudzbina;
     private Onlineprodavnica onlineprodavnica;
     private int kolicina;
     private String status;
     private int vremeIsporuke;

    public Stavka() {
    }

    public Stavka(Narudzbina narudzbina, Onlineprodavnica onlineprodavnica, int kolicina) {
       this.narudzbina = narudzbina;
       this.onlineprodavnica = onlineprodavnica;
       this.kolicina = kolicina;
    }
   
    public Integer getIdSta() {
        return this.idSta;
    }
    
    public void setIdSta(Integer idSta) {
        this.idSta = idSta;
    }
    public Narudzbina getNarudzbina() {
        return this.narudzbina;
    }
    
    public void setNarudzbina(Narudzbina narudzbina) {
        this.narudzbina = narudzbina;
    }
    public Onlineprodavnica getOnlineprodavnica() {
        return this.onlineprodavnica;
    }
    
    public void setOnlineprodavnica(Onlineprodavnica onlineprodavnica) {
        this.onlineprodavnica = onlineprodavnica;
    }
    public int getKolicina() {
        return this.kolicina;
    }
    
    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getVremeIsporuke() {
        return vremeIsporuke;
    }

    public void setVremeIsporuke(int vremeIsporuke) {
        this.vremeIsporuke = vremeIsporuke;
    }
    
    

    public static List<Stavka> dohvatiStavke(int idRasadnik){
        List<Stavka> stavke=new ArrayList<Stavka>();
         SessionFactory sf = HibernateUtil.getSessionFactory();
         Session session = sf.openSession();
         session.beginTransaction();
         String upit="from Stavka s where s.narudzbina.rasadnik.idRas = :idRasadnik";
         Query query = session.createQuery(upit);
         query.setParameter("idRasadnik", idRasadnik);
         
         stavke=query.list();
         session.getTransaction().commit();
         session.close();
         return stavke;
    }
    
    public static void obrisiStavku(Stavka stavka){
         SessionFactory sf = HibernateUtil.getSessionFactory();
         Session session = sf.openSession();
         session.beginTransaction();
         
         session.delete(stavka);
         
         session.getTransaction().commit();
         session.close();
    }
    
    public static void doadajStavku(Stavka stavka){
        SessionFactory sf=HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();
        
        session.save(stavka);
        session.getTransaction().commit();
        session.close();
    }
    
    public static List<Stavka> dohvatiStavkeNarudzbine(int id){
        List<Stavka> stavke=new ArrayList<Stavka>();
        SessionFactory sf=HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();
        String upit="from Stavka s where s.narudzbina.idN=:id";
         Query query = session.createQuery(upit);
         query.setParameter("id", id);
         stavke=query.list();
        
        session.getTransaction().commit();
        session.close();  
        return stavke;
        
    }
    
    
    public static void azurirajStavku(Stavka s){
          SessionFactory sf=HibernateUtil.getSessionFactory();
          Session session = sf.openSession();
          session.beginTransaction();
        
          session.update(s);
          session.getTransaction().commit();
          session.close();
    }
    
    
    public static List<Stavka> dohvatiSveStavke(){
        List<Stavka> stavke = new ArrayList<Stavka>();
          SessionFactory sf=HibernateUtil.getSessionFactory();
          Session session = sf.openSession();
          session.beginTransaction();
        
          String upit = "from Stavka s where s.status = 'u toku'";
          Query query = session.createQuery(upit);
          stavke = query.list();
          
          session.getTransaction().commit();
          session.close();
          
          return stavke;
        
    }
    
    public static String status(int narudzbina, String preduzece){
          String status=" ";
          List<Stavka> stavke=new ArrayList<Stavka>();
          SessionFactory sf=HibernateUtil.getSessionFactory();
          Session session = sf.openSession();
          session.beginTransaction();
        
          String upit="from Stavka s where s.narudzbina.idN = :narudzbina and s.status='na cekanju' and s.onlineprodavnica.preduzece.usernamePred = :preduzece";
          Query query = session.createQuery(upit);
          query.setParameter("narudzbina", narudzbina);
          query.setParameter("preduzece", preduzece);
          stavke = query.list();
          
          session.getTransaction().commit();
          session.close();
          if(!stavke.isEmpty()){
              status="NA ČEKANJU";
          }
          return status;
        
    }
    
    public static List<Stavka> stavkePreduzeca(int idNarudzbina, String preduzece){
        List<Stavka> stavke=new ArrayList<Stavka>();
          SessionFactory sf=HibernateUtil.getSessionFactory();
          Session session = sf.openSession();
          session.beginTransaction();
       
          String upit = "from Stavka s where s.narudzbina.idN = :id and s.onlineprodavnica.preduzece.usernamePred = :username";
          Query query = session.createQuery(upit);
          query.setParameter("id", idNarudzbina);
          query.setParameter("username", preduzece);
          stavke = query.list();
          
          session.getTransaction().commit();
          session.close();
          return stavke;
        
    }
    
    public static List<Stavka> dohvatiNaruceneProizvode(String poljoprivrednik){
          List<Stavka> proizvodi = new ArrayList<Stavka>();
        
          SessionFactory sf=HibernateUtil.getSessionFactory();
          Session session = sf.openSession();
          session.beginTransaction();
       
          String upit = "from Stavka s where s.narudzbina.rasadnik.poljoprivrednik.usernamePoljo = :username and (s.status = 'neisporuceno' or s.status = 'na cekanju')";
          Query query = session.createQuery(upit);
          query.setParameter("username", poljoprivrednik);
          proizvodi = query.list();
          
          session.getTransaction().commit();
          session.close();
          
          return proizvodi;
        
    }


}


