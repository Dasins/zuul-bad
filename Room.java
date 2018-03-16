import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */
public class Room 
{
    private String description;
    private HashMap<String, Room> salidas;
    private HashMap<String, Item> objetos; 

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     * @param item El item contenido en esta sala, null si no contiene nada.
     */
    public Room(String description) 
    {
        this.description = description;
        salidas = new HashMap<>();
        objetos = new HashMap<>();
    }
    
    /**
     * Devuelve la habitacion asociada a la direccion introducida por parametro o, null, en cualquier otro caso.
     * @param direccion Una cadena que contiene una direccion cardinal.
     * @return Devuelve la habitacion asociada a la direccion introducida por parametro o, null, en cualquier otro caso.
     */
    public Room getExit(String direccion) {
        Room retorno = null;
        boolean buscando = true;
        Iterator<Map.Entry<String, Room>> entries = salidas.entrySet().iterator();
        while (entries.hasNext() && buscando) {
            Map.Entry<String, Room> entry = entries.next();
            if (direccion.equals(entry.getKey())) {
               retorno = entry.getValue();
               buscando = false;
            }
        }
        return retorno; 
    }
    
    /**
     * Anade un objeto a la sala.
     * @param nombre El nombre del objeto.
     * @param item El objeto que sera anadido a la sala.
     */
    public void setItem(String nombre, Item item) {
        objetos.put(nombre, item);
    }
    
    /**
     * Crea una salida en la direccion indicada hacia la habitacion especificada. 
     * Si la direccion ya conecta con otro sala, la sobreescribe (se da por hecho que se introducen direcciones validas).
     * 
     * @param direccion La direccion en la que se encuentra la salida.
     * @param sala La habitacion con la que conecta la salida.
     */
    public void setExit(String direccion, Room salaVecina) {
        salidas.put(direccion, salaVecina);
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }
    
    /**
     * Devuelve una cadena con la descripcion de todos los objetos de la sala o una cadena vacia si no ha objetos.
     * @return Devuelve una cadena con la descripcion de todos los objetos de la sala o una cadena vacia si no ha objetos.
     */
    public String getAllItems() {
        String retorno = "";
        for (Map.Entry<String, Item> entries : objetos.entrySet()) {
                retorno += entries.getValue().getInfo() + "\n";
        }
        return retorno;
    }
    
    /**
     * Return a description of the room's exits.
     * For example: "Exits: north east west"
     *
     * @ return A description of the available exits.
     */
    public String getExitString() {
        String retorno = "Exits: ";
        for (String key : salidas.keySet()) {
                retorno += key + " ";
        }
        retorno += "\n";
        return retorno;
    }
    
    /**
     * Return a long description of this room, of the form:
     * You are in the 'name of room'
     * Exits: north west southwest
     * @return A description of the room, including exits.
     */
    public String getLongDescription() {
        return description + "\n" + getAllItems() + "\n" +  getExitString();
    }

}
