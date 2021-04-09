package uy.sample.core;

import com.google.gson.Gson;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import uy.sample.entities.Sucursal;
import uy.sample.persistence.DatabaseOperations;
import uy.sample.utils.MarkerInfo;

//deprecation ver: https://stackoverflow.com/questions/38040947/the-type-managedbean-is-deprecated
@ManagedBean
@RequestScoped
public class BeanSucursal implements Serializable {

    Long id;
    String nombre;
    Boolean airelibre;
    Float longitud;
    Float latitud;
    ArrayList<BeanSucursal> sucursalesList;
    private final Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getAirelibre() {
        return airelibre;
    }

    public void setAirelibre(Boolean airelibre) {
        this.airelibre = airelibre;
    }

    public Float getLongitud() {
        return longitud;
    }

    public void setLongitud(Float longitud) {
        this.longitud = longitud;
    }

    public Float getLatitud() {
        return latitud;
    }

    public void setLatitud(Float latitud) {
        this.latitud = latitud;
    }

    public String getJsonList() {
        ArrayList<BeanSucursal> sucursales = sucursalesList();
        ArrayList<MarkerInfo> marcadores = new ArrayList<>();
        for (BeanSucursal sucursal : sucursales) {
            MarkerInfo marcador = new MarkerInfo();
            marcador.setLatitud(sucursal.getLatitud());
            marcador.setLongitud(sucursal.getLongitud());
            marcador.setHabilitada(sucursal.getAirelibre());
            marcadores.add(marcador);
        }
        Gson gson = new Gson();
        return (marcadores.isEmpty()) ? "" : gson.toJson(marcadores);
    }

    public ArrayList<BeanSucursal> sucursalesList() {
        sucursalesList = new ArrayList<>();
        Collection<Sucursal> sucursales = DatabaseOperations.findAllSucursal();
        Iterator<Sucursal> iterator = sucursales.iterator();
        while (iterator.hasNext()) {
            Sucursal sucursal = iterator.next();
            BeanSucursal suc = new BeanSucursal();
            suc.setId(sucursal.getId());
            suc.setNombre(sucursal.getNombre());
            suc.setAirelibre(sucursal.getAirelibre());
            suc.setLongitud(sucursal.getLongitud());
            suc.setLatitud(sucursal.getLatitud());
            sucursalesList.add(suc);
        }

        return sucursalesList;
    }

    // Used to save user record
    public String save() throws ClassNotFoundException {

        DatabaseOperations.crearSucursal(this);

        return "index.xhtml?faces-redirect=true";
    }

    // Used to fetch record to update
    public String edit(int id) {
        try {
            Sucursal rs = DatabaseOperations.obtenerSucursal(id);
            BeanSucursal sucursal = new BeanSucursal();
            sucursal.setId(rs.getId());
            sucursal.setNombre(rs.getNombre());
            sucursal.setAirelibre(rs.getAirelibre());
            sucursal.setLongitud(rs.getLongitud());
            sucursal.setLatitud(rs.getLatitud());

            sessionMap.put("editSucursal", sucursal);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "/edit.xhtml?faces-redirect=true";
    }

    // Used to update user record
    public String update(BeanSucursal s) {

        try {
            DatabaseOperations.actualizarSucursal(s);
        } catch (Exception e) {
            System.out.println(e);
        }
        return "/index.xhtml?faces-redirect=true";
    }
    
    public void delete(int id) {
        DatabaseOperations.removerSucursal(id);
    }

    // Utility - front end
    public String obtenerAireLibreMsg(Boolean airelibre) {
        if (airelibre == null || !airelibre) {
            return "Normal";
        } else {
            return "Habilitada para aire libre";
        }
    }
}
