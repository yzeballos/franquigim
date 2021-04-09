package uy.sample.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import uy.sample.entities.Afiliado;
import uy.sample.persistence.DatabaseOperations;

//deprecation ver: https://stackoverflow.com/questions/38040947/the-type-managedbean-is-deprecated
@ManagedBean
@RequestScoped
public class BeanAfiliado implements Serializable {

    Long id;
    String nombre;
    String plan;
    Float descuento;    
    ArrayList<BeanAfiliado> afiliadosList;
    
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

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public Float getDescuento() {
        return descuento;
    }

    public void setDescuento(Float descuento) {
        this.descuento = descuento;
    }

    public ArrayList<BeanAfiliado> afiliadosList() {
        //TODO: devuelvo la misma que genero, lo mismo en beanSucursal
        afiliadosList = new ArrayList<>();
        Collection<Afiliado> afiliados = DatabaseOperations.findAllAfiliado();
        Iterator<Afiliado> iterator = afiliados.iterator();
        while (iterator.hasNext()) {
            Afiliado afiliado = iterator.next();
            BeanAfiliado suc = new BeanAfiliado();
            suc.setId(afiliado.getId());
            suc.setNombre(afiliado.getNombre());
            suc.setPlan(afiliado.getPlan());
            suc.setDescuento(afiliado.getDescuento());
            
            afiliadosList.add(suc);
        }

        return afiliadosList;
    }

    public String save() {
        DatabaseOperations.crearAfiliado(this);

        return "index.xhtml?faces-redirect=true";
    }

    // Used to fetch record to update
    public String edit(int id) {
        try {
            Afiliado rs = DatabaseOperations.obtenerAfiliado(id);
            BeanAfiliado afiliado = new BeanAfiliado();
            afiliado.setId(rs.getId());
            afiliado.setNombre(rs.getNombre());
            afiliado.setPlan(rs.getPlan());
            afiliado.setDescuento(rs.getDescuento());
            
            sessionMap.put("editAfiliado", afiliado);

        } catch (Exception e) {
            System.out.println(e);
        }
        return "/editAfiliado.xhtml?faces-redirect=true";
    }

    // Used to update user record
    public String update(BeanAfiliado s) {
        try {
            DatabaseOperations.actualizarAfiliado(s);
        } catch (Exception e) {
            System.out.println(e);
        }
        return "/index.xhtml?faces-redirect=true";
    }

    public void delete(int id) {
        DatabaseOperations.removerAfiliado(id);
    }

    public String getPlanNombre(char plan) {
        if (plan == 'B') {
            return "Basico";
        } else {
            return "Completo";
        }
    }   
}
