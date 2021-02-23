/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Korisnik;
import beans.Preduzece;
import beans.Rezultat;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

/**
 *
 * @author Ana
 */
@Named
@SessionScoped
@FacesConfig(version=FacesConfig.Version.JSF_2_3)
public class RezultatiPoslovanjaController implements Serializable{
    
    
    private Preduzece currPreduzece;
    private List<Rezultat> rezultati;
    private BarChartModel barModel;
    
    @PostConstruct
    public void init(){
        
       FacesContext context = FacesContext.getCurrentInstance();
       HttpSession session= (HttpSession) context.getExternalContext().getSession(false);
       Korisnik korisnik=(Korisnik) session.getAttribute("loggedInUser");
       currPreduzece=korisnik.getPreduzece();
       rezultati=Rezultat.prikaziRezultate(currPreduzece.getUsernamePred());
       createBarModel();
      
        
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    public void setBarModel(BarChartModel barModel) {
        this.barModel = barModel;
    }
    
    

    public List<Rezultat> getRezultati() {
        return rezultati;
    }

    public void setRezultati(List<Rezultat> rezultati) {
        this.rezultati = rezultati;
    }
    
    

    public Preduzece getCurrPreduzece() {
        return currPreduzece;
    }

    public void setCurrPreduzece(Preduzece currPreduzece) {
        this.currPreduzece = currPreduzece;
    }
    
    private BarChartModel initBarModel(){
        BarChartModel model = new BarChartModel();
        ChartSeries rez=new ChartSeries();
        rez.setLabel("Broj narudzbina");
        for(Rezultat r: rezultati){
            rez.set(r.getDatum().toString(), r.getBrojNarudzbina());
        }
        model.addSeries(rez);
        return model;
    }
    
     private void createBarModel() {
        barModel = initBarModel();
 
        barModel.setTitle("Rezultati");
        barModel.setLegendPosition("ne");
 
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Datum");
 
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Broj narudzbina");
        yAxis.setMin(0);
        yAxis.setMax(10);
    }
     
     
     public void preRenderExecute(){
          FacesContext context = FacesContext.getCurrentInstance();
          HttpSession session= (HttpSession) context.getExternalContext().getSession(false);
          Korisnik korisnik=(Korisnik) session.getAttribute("loggedInUser");
          currPreduzece=korisnik.getPreduzece();
          rezultati=Rezultat.prikaziRezultate(currPreduzece.getUsernamePred());
          createBarModel();
     }
     
    
    
    
    
    
}
