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
public class SadnicaMesto {
   private int row_index;
   private int col_index;
   private Sadnica sadnica;

    public SadnicaMesto() {
    }

    public SadnicaMesto(int row_index, int col_index, Sadnica sadnica) {
        this.row_index = row_index;
        this.col_index = col_index;
        this.sadnica = sadnica;
    }

    public int getRow_index() {
        return row_index;
    }

    public void setRow_index(int row_index) {
        this.row_index = row_index;
    }

    public int getCol_index() {
        return col_index;
    }

    public void setCol_index(int col_index) {
        this.col_index = col_index;
    }

    public Sadnica getSadnica() {
        return sadnica;
    }

    public void setSadnica(Sadnica sadnica) {
        this.sadnica = sadnica;
    }
   
   public String nazivSadnice(Sadnica sadnica){
       if(sadnica==null){
           return "SLOBODNO";
       }
       return sadnica.getMagacin().getOnlineprodavnica().getNaziv();
   }

    
   

   

   
   
   
    
}
