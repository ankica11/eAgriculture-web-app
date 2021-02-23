/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Korisnik;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
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
public class LoginController implements Serializable{
    
    private String username;
    private String password;
    private String message;
    private UIComponent component;
    
    private List<String> images;
    
    @PostConstruct
    public void initImages() {
        images = new ArrayList<String>();
        for (int i = 1; i <= 10; i++) {
            images.add("sadnica" + i + ".jpg");
        }
    }

    public List<String> getImages() {
        return images;
    }
    
    

    public UIComponent getComponent() {
        return component;
    }

    public void setComponent(UIComponent component) {
        this.component = component;
    }
     
     

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
    
    public String login(){
        
            FacesContext context = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        Korisnik korisnik = Korisnik.ulogujKorisnika(username, password);
        if(korisnik==null){
            //message="Pogresni kredencijali!";
            context.addMessage(component.getClientId(), new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Pogresni kredencijali!"));
               
            return null;
        }else{
            session.setAttribute("loggedInUser", korisnik);
            if(korisnik.getTip().equals("admin")){
                //admin
                return "admin";
            }else if(korisnik.getTip().equals("poljoprivrednik")){
                //poljoprivrednik
                return "poljoprivrednik";
            }else{
                //preduzetnik
                return "preduzeceNarudzbine";
            }
        }
    }
    
    
     public String signOut(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        session.invalidate();
        return "login";
    }
    
    
    
    
    
    
    
    
    
}
