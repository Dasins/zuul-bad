import java.util.Stack;
import java.util.HashMap;

/**
 * Representa un personaje del mundo del juego.
 * 
 * Character pertenece a la aplicacion zuul-dani.
 * 
 * Cada personaje tiene un nombre unico identificativo. 
 * Los personajes pueden desplazarse entre salas y observar lo que hay en ellas.
 *
 * @author D4s1ns
 * @version 2018/03/23
 */
public class Character {
    // Nombre del personaje
    private String name;
    // Sala actual.
    private Room currentRoom;
    // Historial de salas visitadas.
    private Stack<Room> roomLog;
    // Inventario del personaje.
    private HashMap<String, Item> inventory;
    // Carga maxima del peronaje.
    private int maxCargo;
    // Carga actual del personaje.
    private int actualCargo;
    
    /**
     * Constructor
     * Para crear un personaje debemos especificar un nombre identificativo unico (ej:character1).
     * Ademas debemos especificar la sala en la que aparece el personaje.
     * 
     * @param name El nombre del personaje.
     * @param startRoom La sala donde aparecera el personaje.
     */
    public Character(String name, Room startRoom, int maxCargo) {
        this.name = name;
        currentRoom = startRoom;
        roomLog = new Stack<>();
        inventory = new HashMap<>();
        this.maxCargo = maxCargo;
        actualCargo = 0;
    }
    
    /**
     * Devuelve el nombre del personaje.
     * @return Devuelve el nombre del personaje.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Trata de mover al personaje en la direccion indicada por parametro.
     * Sin importar el resultado, informa por pantalla de lo que ha sucedido.
     * 
     * @param direction La direcion hacia la que se mueve el personaje.
     */
    public void moveTo(String direction) {
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom != null) {
            String lastRoomName = roomLog.peek().name();
            if (!roomLog.isEmpty() && lastRoomName.equals(nextRoom.name())) {
                currentRoom = roomLog.pop();
            }
            else {
                roomLog.push(currentRoom);
                currentRoom = nextRoom;
            }
            look();
        }
        else {
            System.out.println("No hay ninguna salida");
        }
    }
    
    /**
     * Representa una futura funcionalidad que permitira al personaje alimentarse.
     * @deprecated
     */
    public void eat() {
        System.out.println("Aun no tienes hambre. Acabas de comer");
    }
    
    /**
     * El personaje observa lo que hay a su alrededor en la sala.
     * Se imprimira por pantalla toda la informacion relativa a la sala.
     */
    public void look() {
        System.out.println("Estas en " + currentRoom.info());
    }
    
    /**
     * El personaje retrocede hacia la ultima sala que visito, si no hay salas anteriores, no hace nada.
     */
    public void back() {
        if (!roomLog.isEmpty()) {
            currentRoom = roomLog.pop();
        }
    }
    
    /**
     * Coge el objeto indicado de la sala y lo deposita en el inventario del personaje.
     * Al coger un objeto, este se elimina de la sala.
     * @param itemName El nombre del objeto que se quiere coger.
     */
    public void take(String itemName) {
       Item item = currentRoom.getItem(itemName);
       if (item != null) {
           if (item.isUsable()) {
               if (item.weight() + actualCargo <= maxCargo) { 
                   currentRoom.removeItem(itemName);
                   inventory.put(item.name(), item);
                   actualCargo += item.weight();
                   System.out.println("Has recogido [" + item.name() + "]");
               }
               else {
                   System.out.println("El objeto pesa demasiado");
               }
           }
           else {
               System.out.println("El objeto no se puede recoger");
           }
       }
       else {
           System.out.println("No hay nada asi en la sala actual.");
       }
    }
   
    /**
     * Muestra por pantalla todos los objetos del inventario.
     */
    public void items() {
        System.out.println(actualCargo + "/" + maxCargo);
        for (Item item :  inventory.values()) {
            System.out.println(item.info());
        }
    }
    
    /**
     * Coge el objeto indicado del inventario y lo deposita en la sala actual.
     * Al coger un objeto, este se elimina del inventario.
     * @param itemName El nombre del objeto que se quiere coger.
     */
    public void drop(String itemName) {
       Item item = inventory.get(itemName);
       if (item != null) {
           inventory.remove(itemName);
           currentRoom.addItem(item);
           actualCargo -= item.weight();
           System.out.println("Has depositado [" + item.name() + "] en [" + currentRoom.name() + "]");
       }
       else {
           System.out.println("No tienes ese objeto");
       }
    }
    
    
}
