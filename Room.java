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

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        salidas = new HashMap<>();
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
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     * @param southEast The southEast exit.
     */
    public void setExits(Room north, Room northWest, Room east, Room south, Room southEast, Room west) 
    {
        salidas.put("north", north);
        salidas.put("northwest", northWest);
        salidas.put("east", east);
        salidas.put("south", south);
        salidas.put("southeast", southEast);
        salidas.put("west", west);
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
        for (Map.Entry<String, Room> entry : salidas.entrySet()) {
            if (entry.getValue() != null) {
                retorno += entry.getKey() + " ";
            }
        }
        retorno += "\n";
        return retorno;
     }

}
