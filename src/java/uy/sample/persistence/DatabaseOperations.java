package uy.sample.persistence;

import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import uy.sample.core.BeanAfiliado;
import uy.sample.core.BeanSucursal;
import uy.sample.entities.Afiliado;
import uy.sample.entities.Sucursal;

public class DatabaseOperations {

    private static final String PERSISTENCE_UNIT_NAME = "franquigimPU9";
    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);     
    private static EntityTransaction transactionObj;

    public static void crearSucursal(String name) {
        EntityManager em = factory.createEntityManager();
        try {
            if (transactionObj == null) {
                transactionObj = em.getTransaction();
            }
            if (transactionObj != null && !transactionObj.isActive()) {
                transactionObj.begin();
            }

            Sucursal sucursal = new Sucursal();
            sucursal.setNombre(name);
            sucursal.setAirelibre(true);
            sucursal.setLongitud(-58f);
            sucursal.setLatitud(-58f);
            em.persist(sucursal);
            transactionObj.commit();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public static Collection<Sucursal> findAllSucursal() {
        EntityManager em = factory.createEntityManager();
        Collection<Sucursal> suc = null;
        try {
            Query query = em.createQuery("SELECT e FROM Sucursal e");
            suc = query.getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return suc;
    }
    
     public static Collection<Afiliado> findAllAfiliado() {
        EntityManager em = factory.createEntityManager();
        Collection<Afiliado> suc = null;
        try {
            Query query = em.createQuery("SELECT e FROM Afiliado e");
            suc = query.getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return suc;
    }

    public static void crearSucursal(BeanSucursal s) {
        EntityManager em = factory.createEntityManager();

        try {
            transactionObj = em.getTransaction();            
            if (!transactionObj.isActive()) {
                transactionObj.begin();
            }
            Sucursal sucursal = new Sucursal();
            sucursal.setNombre(s.getNombre());
            sucursal.setAirelibre(s.getAirelibre());
            sucursal.setLongitud(s.getLongitud());
            sucursal.setLatitud(s.getLatitud());
            em.persist(sucursal);
            transactionObj.commit();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
    
    public static void crearAfiliado(BeanAfiliado a) {
        EntityManager em = factory.createEntityManager();

        try {
            transactionObj = em.getTransaction();            
            if (!transactionObj.isActive()) {
                transactionObj.begin();
            }
            Afiliado afiliado = new Afiliado();
            afiliado.setNombre(a.getNombre());
            afiliado.setPlan(a.getPlan());
            afiliado.setDescuento(a.getDescuento());
            em.persist(afiliado);
            transactionObj.commit();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public static void actualizarSucursal(BeanSucursal s) {
        EntityManager em = factory.createEntityManager();

        try {
            transactionObj = em.getTransaction();            
            if (!transactionObj.isActive()) {
                transactionObj.begin();
            }
            Sucursal sucursal = em.find(Sucursal.class, s.getId());
            if (sucursal != null) {
                sucursal.setNombre(s.getNombre());
                sucursal.setAirelibre(s.getAirelibre());
                sucursal.setLongitud(s.getLongitud());
                sucursal.setLatitud(s.getLatitud());
                em.merge(sucursal);
            }

            transactionObj.commit();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
    
    public static void actualizarAfiliado(BeanAfiliado a) {
        EntityManager em = factory.createEntityManager();

        try {
            transactionObj = em.getTransaction();            
            if (!transactionObj.isActive()) {
                transactionObj.begin();
            }
            Afiliado afiliado = em.find(Afiliado.class, a.getId());
            if (afiliado != null) {
                afiliado.setNombre(a.getNombre());
                afiliado.setPlan(a.getPlan());
                afiliado.setDescuento(a.getDescuento());                
                em.merge(afiliado);
            }

            transactionObj.commit();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }


    public static void removerAfiliado(int id) {
        EntityManager em = factory.createEntityManager();

        try {
            transactionObj = em.getTransaction();
            if (!transactionObj.isActive()) {
                transactionObj.begin();
            }
            Afiliado afiliado = em.find(Afiliado.class, new Long(id));
            if (afiliado != null) {
                em.remove(afiliado);
            }
            transactionObj.commit();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    
    public static void removerSucursal(int sucursalId) {
        EntityManager em = factory.createEntityManager();

        try {
            transactionObj = em.getTransaction();
            if (!transactionObj.isActive()) {
                transactionObj.begin();
            }
            Sucursal sucursal = em.find(Sucursal.class, new Long(sucursalId));
            if (sucursal != null) {
                em.remove(sucursal);
            }
            transactionObj.commit();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }



    public static Sucursal obtenerSucursal(int sucursalId) {
        Sucursal suc = null;
        EntityManager em = factory.createEntityManager();

        try {
            suc = em.find(Sucursal.class, new Long(sucursalId));
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return suc;
    }
    
     public static Afiliado obtenerAfiliado(int afiliadId) {
        Afiliado suc = null;
        EntityManager em = factory.createEntityManager();

        try {
            suc = em.find(Afiliado.class, new Long(afiliadId));
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return suc;
    }


    
    //    public static void main(String[] args) {
//        System.out.println("Hello, World!");
//        DatabaseOperations.crearSucursal("jacinta");
//        //removerSucursal(10);
//        //actualizarSucursal(new Long(11), "pepe");
//    }

    
   
}
