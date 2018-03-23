import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

/**
 * Representa una sala del escenario del juego.
 *
 * Room pertenece a la aplicacion zuul-dani. 
 *
 * Una sala es una localizacion dentro del escenario del juego.
 * Las salas conectan con otras mediante salidas (referencias a otras salas).
 * Las salas son capaces de alojar objetos.
 * 
 * @author  d4sºns
 * @version 2018.03.23
 */
public class Room 
{
    // El nombre de la sala.
    private String name;
    // La descripcion de la sala.
    private String description;
    // El conjunto de salas y, su direccion asociada, con las que conecta esta sala. 
    private HashMap<String, Room> neighbors;
    // Los objetos que hay en la sala.
    private HashMap<String, Item> items; 

    /**
     * Constructor
     * Para crear una sala es necesario indicar un nombre unico e identificativo y una descripcion de la misma.
     * Por defecto, las salas creadas no tendran salidas.
     * @param description The room's description.
     * @param item El item contenido en esta sala, null si no contiene nada.
     */
    public Room(String name, String description) {
        this.description = description;
        neighbors = new HashMap<>();
        items = new HashMap<>();
    }
    
    /**
     * Busca una salida en la direccion indicada por parametro.
     * Si existe una salida, devuelve la sala que se encuentra al otro lado. En cualquier otro caso devuelve null.
     * 
     * @param direction La direccion en la que se busca una salida.
     * @return Si existe una salida, devuelve la sala que se encuentra al otro lado. En cualquier otro caso devuelve null.
     */
    public Room getExit(String direction) {
        Room refund = null;
        boolean searching = true;
        Iterator<Map.Entry<String, Room>> rooms = neighbors.entrySet().iterator();
        while (rooms.hasNext() && searching) {
            Map.Entry<String, Room> room = rooms.next();
            if (direction.equals(room.getKey())) {
               refund = room.getValue();
               searching = false;
            }
        }
        return refund; 
    }
    
    /**
     * Ubicar el objeto indicado en la sala.
     * Si el objeto ya existe, lo reemplaza.
     * @param item El objeto a colocar en la sala.
     */
    public void addItem(Item item) {
        items.put(item.name(), item);
    }
    
    /**
     * Crea una salida en la direccion indicada hacia la sala indicada.
     * Si ya existe una salida en esa direccion, la sobreescribe.
     * 
     * @param direction La direccion en la se quiere crear la salida.
     * @param room La sala con la que se quiere conectar.
     */
    public void addExit(String direction, Room room) {
        neighbors.put(direction, room);
    }
    
    /**
     * Devuelve la descripcion de la sala.
     * @return Devuelve la descripcion de la sala.
     */
    public String description() {
        return description;
    }
    
    /**
     * Devuelve el nombre de la sala.
     * @return Devuelve el nombre de la sala.
     */
    public String name() {
        return name;
    }
    
    /**
     * Devuelve la informacion de los objetos alojados en la sala o null, si no hay objetos en la sala. 
     * @return Devuelve la informacion de los objetos alojados en la sala o null, en cualquier otro caso. 
     */
    public String itemsInfo() {
        String refund = null;
        for (Item item : items.values()) {
                refund += item.info() + "\n";
        }
        return refund;
    }
    
    /**
     * Devuelve la informacion de las salidas disponibles en la sala, o null, si no hay salidas en la sala.
     * For example: "Exits: north east west"
     *
     * @return Devuelve la informacion de las salidas disponibles en la sala, o null, si no hay salidas en la sala.
     */
    public String exitsInfo() {
        String refund = null;
        if (!neighbors.isEmpty()) {
            for (String key : neighbors.keySet()) {
                    refund += "[" + key + "] ";
            }
            refund = "Exits:\n" + refund + "\n";
        }
        return refund;
    }
    
    /**
     * Devuelve la informacion sobre la sala.
     * @return Devuelve la informacion sobre la sala.
     */
    public String roomInfo() {
        return "[" + name.toUpperCase() + "] " + ":\n" + description;
    }
    
    /**
     * Devuelve toda la informacion disponible sobre la sala.
     * @return Devuelve toda la informacion disponible sobre la sala.
     */
    public String info() {
        String refund = roomInfo();
        if (exitsInfo() != null) {
            refund += "/n" + roomInfo();
        }
        if (itemsInfo() != null) {
            refund += "/n" + itemsInfo();
        }
        return refund;
    }

}
