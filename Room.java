import java.util.HashMap;

/**
 * Una localizacion dentro del escenario del juego.
 * 
 * Room pertenece a la aplicacion 'sneak-in-class'.
 * 
 * Una sala es una localizacion del escenario. Las salas se interconectan con otras a traves de salidas. 
 * Las salidas son referencias a otras salas. Las salas pueden albergar objetos. 
 *
 * @author d4s1ns
 * @version 2018/04/13
 */
public class Room {
    // Descripcion de la sala.
    private String description;
    // Nombre de la sala. Debe ser unico e identificativo.
    private String name;

    // Objetos de la sala.
    private HashMap<String, Item> items;
    // Salidas de la sala.
    private HashMap<String, Room> neighbors;     

    /**
     * Constructor - Crea una sala sin salidas indicando su nombre y su descripcion.
     * @param id Identificador unico de la sala.
     * @param name Nombre de la sala. Debe ser unico e identificativo.
     * @param description Descripcion de la sala.
     */
    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        neighbors = new HashMap<>();
        items = new HashMap<>();
    }
  
    // INFORMACION SOBRE LA SALA.
    /**
     * Devuelve una cadena de texto con las direcciones en las que hay una salida de la sala.
     * @return Devuelve una cadena de texto con todas las salidas de la sala.
     */
    public String exits() {
        String refund = "Salidas: ";
        for (String direction : neighbors.keySet()) {
            refund += "[" + direction + "] ";
        }
        return refund;
    }
    
    /**
     * Busca un objeto por su nombre en la sala y lo devuelve, si no esta en la sala, devuelve 'null'.
     * @param itemName Nombre del objeto buscado.
     * @return Devuelve el objeto buscado o, 'null', si no esta en la sala.
     */
    public Item findItem(String itemName) {   
        return items.get(itemName);
    }
    
    /**
     * Devuelve la descripcion de la sala.
     * @return Devuelve la descripcion de la sala.
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Devuelve el nombre de la sala.
     * @return Devuelve el nombre de la sala.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Devuelve una cadena de texto con la informacion de todos los objetos en la sala.
     * @return Devuelve una cadena de texto con la informacion de todos los objetos en la sala.
     */
    public String items() {
        String refund = "";
        if (!items.isEmpty()) {
            refund = "Objetos en la sala:";
            for (Item item : items.values()) {
                refund += "\n" + item + "\n";
            }
        }
        return refund;
    }
    
    /**
     * Devuelve la sala adyacente en la direccion indicada o 'null' si no existe.
     * @return Devuelve la sala adyacente en la direccion indicada o 'null' si no existe.
     */
    public Room pathTo(String direction) {
        return neighbors.get(direction);
    }
    
    /** 
     * Devuelve una cadena de texto con toda la informacion acerca de la sala.
     * @return Devuelve una cadena de texto con toda la informacion acerca de la sala.
     */
    public String toString() {
        return "[" + name + "].\n" + description + "\n\n" + items() + "\n" + exits();
    }
    
    // OPERACIONES CON LA SALA
    /**
     * Crea una salida en la direccion indicada hacia la sala especificada.
     * Si ya existe una salida en la direccion indicada, se sobreescribe.
     * @param direction Direccion en la que se situara la salida.
     * @param room Sala con la que conecta la sala a traves de esta salida.
     */
    public void addExit(String direction, Room room) {
        neighbors.put(direction, room);
    }
    
    /**
     * Ubicar el objeto indicado en la sala.
     * Si el objeto ya existe, lo reemplaza.
     * @param item El objeto a colocar en la sala.
     */
    public void addItem(Item item) {
        items.put(item.getName(), item);
    }
    
    /**
     * Trata de eliminar una salida de la sala.
     * Se debe especificar la direccion en la que se encuentra la salida.
     * Si no existe una salida en esa direccion, no hace nada.
     * @param direction Direccion de la salida a eliminar.
     */
    public void removeExit(String direction) {
        neighbors.remove(direction);
    }
    
    /**
     * Trata de eliminar un objeto de la sala.
     * Se debe especificar el nombre del objeto.
     * Si no existe un objeto con ese nombre, no hace nada.
     * @param itemName Nombre del objeto buscado.
     */
    public void removeItem(String itemName) {
        items.remove(itemName);
    }
    
    /**
     * Modifica la descripcion de la sala, nueva descripcion
     * @param newDescription Nueva descripcion.
     */
    public void setDescription(String newDescription) {
        description = newDescription;
    }
}
