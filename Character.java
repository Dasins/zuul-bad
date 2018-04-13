import java.util.Stack;
import java.util.HashMap;

/**
 * Un personaje del mundo del juego.
 * 
 * Character pertenece a la aplicacion 'sneak-in-class'.
 * 
 * Un personaje es un actor protagonista de la historia del juego.
 * 
 * El jugador puede controlar las acciones del personaje mediante el uso de comandos.
 * Los personajes pueden realizar diversas acciones: moverse, volver atras, interactuar con objetos.
 * Las acciones del personaje deben estar asociadas a comandos reconocidos e implentados en game.
 * 
 * Un personaje puede moverse a traves de las salas cruzando por sus diferentes salidas.
 * Tambien puede recoger objetos (si no exceden su carga maxima), transportarlos o dejar objetos de su inventario en la sala en que se encuentra.
 *
 * @author d4s1ns
 * @version 2018/04/13
 */
public class Character {
    // Carga inicial transportada.
    private final static int INITIAL_CARGO = 0;
    
    // Carga actual del personaje.
    private int cargo;
    // Carga maxima del peronaje.
    private int maxCargo;
    
    // Sala actual.
    private Room currentRoom;
    // Nombre del personaje
    private String name;
    
    // Inventario del personaje.
    private HashMap<String, Item> inventory;
    // Historial de salas visitadas.
    private Stack<Room> roomLog;  
    
    /**
     * Constructor - Inicializa el inventario, el historial de salas visitadas, la carga maxima e inicial y, 
     * determina la sala donde aparecera el personaje por primera vez.
     * 
     * @param name Nombre unico e identificativo para el personaje. 
     * @param spawnRoom Sala donde aparecera el personaje por primera vez.
     * @param maxCargo Carga maxima que puede transportar el personaje.
     */
    public Character(String name, Room spawnRoom, int maxCargo) {
        this.name = name;
        currentRoom = spawnRoom;
        this.maxCargo = maxCargo;
        cargo = INITIAL_CARGO;
        inventory = new HashMap<>();
        roomLog = new Stack<>();
    }
    
    // INFORMACION DEL PERSONAJE.
    /**
     * Devuelve la carga actual del personaje.
     * @return Devuelve la carga actual del personaje.
     */
    public int getCargo() {
        return cargo;
    }
    
    /**
     * Devuelve la carga maxima del personaje.
     * @return Devuelve la carga maxima del personaje.
     */
    public int getMaxCargo() {
        return maxCargo;
    }
    
    /**
     * Devuelve el nombre del personaje.
     * @return Devuelve el nombre del personaje.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Devuelve una cadena de texto con la carga actual, maxima y el contenido del inventario del personaje.
     * @return Devuelve una cadena de texto con la carga actual, maxima y el contenido del inventario del personaje.
     */
    public String inventory() {
        String refund = "Inventario [" + cargo + "/" + maxCargo + "]:";
        for (Item item : inventory.values()) {
            refund += "\n" + item.toString();
        }
        return refund;
    }
    
    /**
     * Devuelve una cadena de texto con toda la informacion del personaje.
     * @return Devuelve una cadena de texto con el nombre, la sala actual y el inventario del personaje.
     */
    public String toString() {
        return "[" + name.toUpperCase() + "]:\nSala actual - " + currentRoom.toString() + "\n" + inventory(); 
    }
    
    /**
     * Devuelve el nombre de la habitacion actual
     * @return Devuelve el nombre de la habitacion actual
     */
    public String actualRoom() {
        return currentRoom.getName();
    }
    
    // ACCIONES DEL PERSONAJE.
    /**
     * El personaje retrocede a la ultima sala visitada - Trata de hacer retroceder al personaje a la ultima sala visitada. 
     * Si no hay una sala anterior a la que volver, no hace nada. Se puede invocar multiples veces.
     */
    public void back() {
        if (!roomLog.isEmpty()) {
            currentRoom = roomLog.pop();
        }
    }
    
    /**
     * El personaje deja un objeto de su inventario en la sala acutal - Trata de dejar un objeto del inventario en la sala.
     * Se debe especificar el nombre del objeto.
     * Sin importar lo sucedido, informa al jugador por pantalla del resultado.
     * @param itemName Nombre del objeto a recoger.
     */
    public void drop(String itemName) {
        Item item = inventory.get(itemName);
        if (item != null) {
            inventory.remove(itemName);
            currentRoom.addItem(item);
            cargo -= item.getWeight();
            System.out.println("Has depositado [" + itemName + "] en [" + currentRoom.getName() + "].");
        }
        else {
            System.out.println("No se ha encontrado [" + itemName + "] en el inventario.");
        }
    }
    
    /**
     * El personaje se mueve en una direccion - Trata de cambiar de sala al personaje.
     * Se debe especificar la direccion hacia donde avanza el personaje.
     * Si no existe una salida de la sala en esa direccion, el personaje continuara en la misma sala.
     * Sea cual sea el resultado, el jugador es informado por pantalla.
     * @param direction La direccion hacia la que se mueve el personaje.
     */
    public void goTo(String direction) {
        Room nextRoom = currentRoom.pathTo(direction);
        if (nextRoom != null) {
            if (!roomLog.isEmpty() && nextRoom.getName().equals(roomLog.peek().getName())) {
                back();
            }
            else {
                roomLog.push(currentRoom);
                currentRoom = nextRoom;
            }
            look();
        }
        else {
            System.out.println("No hay salida en esa direccion: [" + direction + "]");
        }
    }
    
    /**
     * El personaje mira alrededor - Muestra por pantalla toda la informacion sobre la sala actual.
     */
    public void look() {
        System.out.println("Estas en " + currentRoom);
    }
    
    /**
     * El personaje coge un objeto y lo guarda en su inventario - Trata de recoger un objeto de la sala. 
     * Se debe especificar el nombre del objeto a recoger.
     * El objeto debe ser recogible y no exceder la carga maxima al anadir su peso a la carga actual.
     * Sin importar el resultado, informa al jugador de lo sucedido por pantalla.
     * @param itemName Nombre del objeto a recoger.
     */
    public void pick(String itemName) {
        Item item = currentRoom.findItem(itemName);
        if (item != null) {
            if(item.isPickable()) {
                if (cargo + item.getWeight() <= maxCargo) {
                    currentRoom.removeItem(itemName);
                    inventory.put(item.getName(), item);
                    cargo += item.getWeight();
                    System.out.println("Se ha recogido [" + itemName + "]");
                }
                else {
                    System.out.println("[" + itemName + "] pesa demasiado.");
                }
            }
            else {
                System.out.println("No se puede recoger [" + itemName + "]");
            }
        }
        else {
            System.out.println("No se ha encontrado [" + itemName + "] en la sala.");
        }
    }
    
    /**
     * El personaje usa el objeto indicado - Trata de usarse el objeto indicado.
     * Se debe especificar el nombre del objeto. El objeto debe encontrarse en la sala o en el inventario. 
     * @param itemName Nombre del objeto a utilizar.
     */
    public boolean use(String itemName) {
        boolean refund = false;
        Item item = null;
        item = currentRoom.findItem(itemName);
        if (item != null) {
            inventory.get(itemName);
            if (item != null && item.isUsable()) {
                refund = true;
            }
            else{
                System.out.println("[" + itemName + "] no tiene ningun uso]");
            }
        }
        else {
            System.out.println("No hay ningun [" + itemName + "] al alcance.]");
        }
        return refund;
    }
    
    // INUTILISMOS Y PRUEBAS
    /**
     * Representa una futura funcionalidad que permitira al personaje alimentarse.
     * @deprecated
     */
    public void eat() {
        System.out.println("Aun no tienes hambre. Acabas de comer");
    }
    
}
