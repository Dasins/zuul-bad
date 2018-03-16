import java.util.Stack;
import java.util.HashMap;
import java.util.Map;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 * @author  D4s1ns
 * @version 2018.03.13
 */

public class Game  {
    // Analizador de sintaxis.
    private Parser parser;
    // Habitacion actual.
    private Room currentRoom;
    // Ultima habitacion.
    private Stack<Room> roomLog;
    // Coleccion de objetos.
    private HashMap<String,Item> items;
    // Peso maximo del personaje.
    private static final int cargaMax = 100;
    private int cargaActual;
        
    /**
     * Crea el juego e inicializa su mapa interno.
     */
    public Game() {
        createRooms();
        parser = new Parser();
        roomLog = new Stack<>();
        items = new HashMap<>();
        cargaActual = 0;
    }

    /**
     * Crea todas las habitaciones y establece como se unen entre ellas.
     */
    private void createRooms() {
        // Habitaciones:
        Room bachiller, pasillo, fp, aula201, aula202, aula203;
        
        // Creacion de las habitaciones:
        bachiller = new Room("Pasillo de Bachiller.\n"
                            + "La salida de emergencia y las aulas de esta zona estan cerradas.\n");               
        pasillo = new Room("Pasillo principal.\n" 
                          + "En los extremos de la sala se encuentran: el pasillo de FP y el pasillo de Bachiller.\n" 
                          + "En el medio, puedes ver las escaleras que conducen a la primera planta.\n" 
                          + "Al fondo de la sala, se encuentran los aseos (que parecen fuera de servicio) y, el ascensor\n"
                          + "que requiere de una llave para funcionar.\n");              
        fp = new Room("Pasillo de FP.\n" + "La zona esta desierta, pero se escucha actividad en las aulas.\n");
        aula201 = new Room("Aula de Examenes.\n" + "Un escalofrio recorre tu espalda.\n"
                          + "No puedes evitar sentirte vigilado" 
                          +  "por las cientos de almas que han sido suspendidas entre estos muros.\n");            
        aula202 = new Room("Aula de Segundo.\n" 
                          + "Hay un par de repetidores que parecen abrumados por la tarea que les ha mandado Roberto.\n");             
        aula203 = new Room("Aula de Programacion.\n" + "Los alumnos parecen estresados y abatidos.\n");
        
        // Vincular salidas:
        bachiller.setExit("east", pasillo);
        pasillo.setExit("east", fp);
        pasillo.setExit("west", bachiller);
        fp.setExit("east", aula203);
        fp.setExit("south", aula201);
        fp.setExit("southeast", aula202);
        fp.setExit("west", pasillo);
        aula201.setExit("north", fp);
        aula202.setExit("northwest",fp);
        aula202.setExit("south",aula203);
        aula203.setExit("east",aula202);
        aula203.setExit("west",fp); 
        
        // Anadir objetos
        pasillo.setItem(new Item("Alarma de incendios", 1000));
        aula201.setItem(new Item("Esqueleto de repetidor", 80));
        aula201.setItem(new Item("Examen de redes", 10));
        
        
        // Establece la habitacion inicial:
        currentRoom = pasillo;
    }
    
    /**
     * Imprime la descripcion de la habitacion actual y sus salidas.
     */
    private void printLocationInfo() {
        System.out.println("\nEstas en el " + currentRoom.getLongDescription());
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printLocationInfo();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("look")) {
            look();
        }
        else if (commandWord.equals("eat")) {
            eat();
        }
        else if (commandWord.equals("back")) {
            back();
        }
        else if (commandWord.equals("items")) {
            items();
        }
        else if (commandWord.equals("drop")) {
            drop(command);
        }
        else if (commandWord.equals("take")) {
            take(command);
        }
        else if (commandWord.equals("back")) {
            back();
        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Los comandos disponibles son:");
        System.out.println(parser.getAllCommands());
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            roomLog.push(currentRoom);
            currentRoom = nextRoom;
            printLocationInfo();
        }
    }
    
    /**
     * Mueve al personaje a la ultima habitacion visitada, si el personaje no se ha cambiado de sala o, ya ha regresado
     * a la habitacion anterior no hace nada.
     */
    private void back() {
        if (!roomLog.empty()) {
            currentRoom = roomLog.pop();
            printLocationInfo();
        }
    }
    
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    /**
     * Muestra la informacion de la habitacion actual.
     */
    private void look() {
        printLocationInfo();
    }
    
    /**
     * No hace mucho aun
     */
    private void eat() {
        System.out.println("Acabas de comer, aun no tienes hambre");
    }
    
    /**
     * Coge un item de la sala si existe.
     * @param nombreItem El nombre del objeto que queremos coger.
     */
    private void take(Command command) {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to take...
            System.out.println("Coger que?");
        }
        else {
            String nombreItem = command.getSecondWord();
            Item item = currentRoom.pop(nombreItem);
            if (item == null) {
                System.out.println("No hay nada asi en la sala");
            }
            else {
                if(cargaActual + item.getPeso() > cargaMax) {
                    System.out.println("Demasiado peso, no puedes con ello");
                } 
                else {
                    items.put(item.getID(),item);
                }
            }
        } 
    }
    
    /**
     * Deja el objeto especificado en la sala actual.
     * @param nombreItem El nombre del objeto que quieres dejar.
     */
    private void drop(Command command) {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to drop...
            System.out.println("dejar que?");
        }
        else {
            String nombreItem = command.getSecondWord();
            Item item = items.get(nombreItem);
            if (item == null) {
                System.out.println("No llevas ese objeto encima");
            }
            else {
                currentRoom.setItem(item);
                items.remove(item);
            }
        } 
    }
    
    /**
     * Imprime todos los items del inventario.
     */
    private void items() {
        for (Map.Entry<String, Item> entries : items.entrySet()) {
            System.out.println(entries.getValue().getInfo());
        }
    }
}
