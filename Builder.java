import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Iterator;

/**
 * Constructor de Escenarios de juego.
 * 
 * Map pertenece a la aplicacion 'sneak-in-class'.
 * 
 * El constructor de escenarios es el encargado de generar el mapa del juego y, 
 * almacenar referencias a todos los elementos del mapa (salas, objetos...).
 * 
 * El escenario crea salas, las vincula entre ellas y aloja objetos en su interior a partir de varios archivos.
 * Los ficheros deben estar alojados en una carpeta llamada 'map' junto a los *.java. Cada escenario debe agrupar sus archivos
 * dentro de una carpeta con el nombre del escenario dentro de 'map'.
 *
 * @author d4s1ns
 * @version 2018/04/13
 */
public class Builder {
    // Directorio donde se alojan los escenarios.
    private final static String MAIN_FOLDER = "map/";
    // Fichero de configuracion de salas.
    private final static String ROOMS_FILE = "/rooms.config";
    // Fichero de configuracion de salidas.
    private final static String EXITS_FILE = "/exits.config";
    // Fichero de configuracion de objetos.
    private final static String ITEMS_FILE = "/items.config";
    // Fichero de configuracion de las ubicaciones de los objetos fijos.
    private final static String STATICITEMS_FILE = "/static_items.config";
    // Fichero de configuracion de la sala de aparicion.
    private final static String SPAWNROOM_FILE = "/spawnroom.config";
    // Fichero de configuracion de la sala de aparicion.
    private final static String RANDOMITEMS_FILE = "/randomitems.config";
    
    // Nombre del escenario elegido.
    private String mapName;
    // Salas del escenario.
    private HashMap<String, Room> rooms;
    // Objetos del escenario.
    private HashMap<String, Item> items;
    
    
    /**
     * Constructor - Crea e inicializa el constructor.
     */
    public Builder() {
        rooms = new HashMap<>();
        items = new HashMap<>();
        mapName = null;
    }
    
    /**
     * Construye el escenario indicado.
     * @param mapName Nombre del escenario a construir.
     */
    public void build(String mapName) {
        if (!mapName.equals(this.mapName)) {
            this.mapName = mapName;
            createRooms();
            createExits();
            createItems();
            placeItem();
            placeRandomItem();
        }
    }
    
    /**
     * Construye las salas del escenario.
     * @return Devuelve la sala de aparicion de este escenario.
     */
    private void createRooms() {
        try {
            File file = new File(MAIN_FOLDER + mapName + ROOMS_FILE);
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String[] roomText = sc.nextLine().replace("\\n","\n").split(":");
                rooms.put(roomText[0], new Room(roomText[0], roomText[1]));
            }
            sc.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Devuelve la sala de aparicion de este escenario.
     * @return Devuelve la sala de aparicion de este escenario.
     */
    public Room getSpawnRoom() {
        Room spawnRoom = null;
        try {
            File file = new File(MAIN_FOLDER + mapName + SPAWNROOM_FILE);
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                spawnRoom = rooms.get(sc.nextLine());
            }
            sc.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return spawnRoom;
    }
    
    /**
     * Construye las conexiones entre salas.
     */
    private void createExits() {
        try {
            File file = new File(MAIN_FOLDER + mapName + EXITS_FILE);
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String[] exitsText = sc.nextLine().split(":");
                Room room = rooms.get(exitsText[0]);
                for (int i = 1; i < exitsText.length; i += 2) {
                    room.addExit(exitsText[i], rooms.get(exitsText[i + 1]));
                }
            }
            sc.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Construye las objetos del escenario.
     */
    private void createItems() {
        try {
            File file = new File(MAIN_FOLDER + mapName + ITEMS_FILE);
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String[] itemText = sc.nextLine().replace("\\n","\n").split(":");
                items.put(itemText[0], new Item(itemText[0], itemText[1], Integer.parseInt(itemText[2]), 
                                                Boolean.valueOf(itemText[3]), Boolean.valueOf(itemText[4])));
            }
            sc.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Aloja los objetos en las salas.
     */
    private void placeItem() {
        try {
            File file = new File(MAIN_FOLDER + mapName + STATICITEMS_FILE);
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String[] itemText = sc.nextLine().split(":");
                Room room = rooms.get(itemText[1]);
                Item item = items.get(itemText[0]);
                room.addItem(item);
            }
            sc.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Obtiene una cadena aleatoria dentro de un coleccion excluyendo los valores de la lista negra.
     * @param collection La coleccion de candenas donde se escogera una aleatoriamente.
     * @param blacklist Las cadenas excluidas de la busqueda.
     * @return Devuelve una cadena aleatoria de una coleccion de cadenas.
     */
    private String randomValue(HashSet<String> collection, HashSet<String> blacklist){
        String refund = null;
        // Crea una coleccion solo con los valores validos.
        HashSet<String> pool = new HashSet();
        for(String chain : collection) {
           if (!blacklist.contains(chain)) {
               pool.add(chain);
           }
        }
        
        Random randomizer = new Random();
        Iterator it = pool.iterator();
        int limit = randomizer.nextInt(pool.size());
        int i = 0;
        while (it.hasNext() && i <= limit) {
            String chain = it.next().toString();
            if (i == limit) {
                refund = chain;
            }
            i++;
        }
        return refund;
    }
    
    /**
     * Coloca los objetos de aparicion aleatoria en una sala aleatoria.
     */
    private void placeRandomItem() {
        try {          
            File file = new File(MAIN_FOLDER + mapName + RANDOMITEMS_FILE);
            Scanner sc = new Scanner(file);        
            while (sc.hasNextLine()) {
                String[] info = sc.nextLine().split(":");
                Item item = items.get(info[0]);   
                HashSet<String> blacklist = new HashSet();
                for (int i = 1; i < info.length; i++) {
                    blacklist.add(info[i]);
                }
                HashSet<String> collection = new HashSet<>(rooms.keySet());
                Room room = rooms.get(randomValue(collection, blacklist));
                room.addItem(item);
            }
            sc.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }      
    }
    /**
     * Cambia la descripcion de una sala.
     * @param roomName Nombre de la salama
     * @param newDescription Nueva descripcion
     */
    public void changeRoomDescription(String roomName, String newDescription) {
        rooms.get(roomName).setDescription(newDescription);
    }
}
