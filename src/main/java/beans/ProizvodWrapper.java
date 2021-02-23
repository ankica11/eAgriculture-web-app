/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 *
 * @author Ana
 */
public class ProizvodWrapper {
    
    private Onlineprodavnica proizvod;
    private String kolicina;

    public ProizvodWrapper(Onlineprodavnica proizvod, String kolicina) {
        this.proizvod = proizvod;
        this.kolicina = kolicina;
    }
    
    

    public Onlineprodavnica getProizvod() {
        return proizvod;
    }

    public void setProizvod(Onlineprodavnica proizvod) {
        this.proizvod = proizvod;
    }

    public String getKolicina() {
        return kolicina;
    }

    public void setKolicina(String kolicina) {
        this.kolicina = kolicina;
    }
    
    
    
}
