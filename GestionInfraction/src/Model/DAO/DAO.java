package Model.DAO;

import java.util.List;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public abstract class DAO<T> {
	
	final WebResource webResource;
	ClientConfig config = new DefaultClientConfig();
	
	public DAO(){
		Client client = Client.create(config);
	    webResource = client.resource(InfractionConnection.getBaseUri());
	}
	
    /**
     * Permet de cr�er une entr�e dans la base de donn�es
     * @param obj
     */
    public abstract boolean creer(T obj);
    
    /**
     * Permet de mettre � jour les donn�es d'une entr�e dans la base 
     * @param obj
     */ 
    public abstract boolean modifier(T obj);

    /**
     * Permet la suppression d'une entr�e de la base
     * @param obj
     */  
    public abstract boolean supprimer(T obj);
    
    /**
     * Permet de r�cup�rer un objet via son ID
     * @param id
     * @return
     */  
    public abstract T rechercher(int id);
  
    /**
     * Permet d'obtenir tous les objets
     * @return
     */   
    public abstract List<T> lister();
}
