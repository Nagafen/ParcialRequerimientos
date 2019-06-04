package resources;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import java.util.ArrayList;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("perros")
public class PerrosService {

    private final static String HOST = "localhost";
    private final static int PORT = 27017;
    private DB mongo = null;

    public void Conectar() {
        try {
            MongoClient mongoClient = new MongoClient(HOST, PORT);
            mongo = mongoClient.getDB("Parcial");
            System.out.println("Conectado");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getPerros() {
        Conectar();
        ArrayList<String> array = new ArrayList<>();
        try {
            DBCollection coll = mongo.getCollection("perros");
            DBCursor cursor = coll.find();
            try {
                while (cursor.hasNext()) {
                    DBObject object = cursor.next();
                    System.out.println(object.toString());
                    array.add(object.toString());
                }
                return array.toString();
            } finally {
                cursor.close();
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return "No hay perros en la base de datos";
    }
    
    @GET
    @Path("/comentario")
    @Produces({MediaType.APPLICATION_JSON})
    public String getComentarios() {
        Conectar();
        ArrayList<String> array = new ArrayList<>();
        try {
            DBCollection coll = mongo.getCollection("comentarios");
            DBCursor cursor = coll.find();
            try {
                while (cursor.hasNext()) {
                    DBObject object = cursor.next();
                    System.out.println(object.toString());
                    array.add(object.toString());
                }
                return array.toString();
            } finally {
                cursor.close();
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return "No hay comentarios";
    }
    @POST
    @Path("/comentario")
    @Produces({MediaType.APPLICATION_JSON})
    public String validateUser(String comentario) {
        Conectar();
        try {
            DBCollection coll = mongo.getCollection("comentarios");
            DBObject query = new BasicDBObject("comentario", comentario);
            coll.insert(query);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        
        return "No ingresado";
    }
}
