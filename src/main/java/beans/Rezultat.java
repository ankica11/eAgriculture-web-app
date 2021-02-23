/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;

/**
 *
 * @author Ana
 */
public class Rezultat {
    
    private Date datum;
    private int brojNarudzbina;

    public Rezultat() {
    }
    
    

    public Rezultat(Date datum, int brojNarudzbina) {
        this.datum = datum;
        this.brojNarudzbina = brojNarudzbina;
    }
    
    

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public int getBrojNarudzbina() {
        return brojNarudzbina;
    }

    public void setBrojNarudzbina(int brojNarudzbina) {
        this.brojNarudzbina = brojNarudzbina;
    }
    
    
    public static List<Rezultat> prikaziRezultate(String preduzece){
        List<Rezultat> rezultati=new ArrayList<Rezultat>();
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();
         
        String upit="select n.datum, count(*) as broj "
                + "from Narudzbina n "
                + "where n.datum<=current_date() and n.datum>=:datum "
                + "and n.idN in ("
                + "select s.narudzbina.idN "
                + "from Stavka s "
                + "where s.status='isporuceno' and s.onlineprodavnica.preduzece.usernamePred=:preduzece) "
                + "group by n.datum";
       
        
        Query query = session.createQuery(upit);
        query.setParameter("preduzece", preduzece);
        LocalDate datum=LocalDate.now().minus(Period.ofDays(30));
        Date datumj=Date.from(datum.atStartOfDay(ZoneId.of("Z")).toInstant());
        query.setParameter("datum", datumj);
        List<Object[]> lista= query.list();
        for(Object[] row: lista){
            Rezultat r=new Rezultat();
            r.setDatum((java.util.Date)row[0]);
            r.setBrojNarudzbina(Integer.parseInt(row[1].toString()));
            rezultati.add(r);
            
        }
         
        session.getTransaction().commit();
        session.close();
        
        return rezultati;
        
    }
    
    
    
}
