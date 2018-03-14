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
    private Item item;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     * @param item El item contenido en esta sala, null si no contiene nada.
     */
    public Room(String description, Item item) 
    {
        this.description = description;
        salidas = new HashMap<>();
        this.item = item;
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
        return description + "\n" + item.getInfo() +  getExitString();
    }

}
