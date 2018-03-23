import java.util.Stack;
import java.util.HashMap;

/**
 *  Representa la interfaz con la que se comunicara el jugador.
 * 
 *  Para jugar, crea una instancia de esta clase.
 * 
 * 
 * @author  D4s1ns
 * @version 2018.03.13
 */

public class Game  {
    // Analizador de sintaxis.
    private Parser parser;
    private Room initialRoom;
    private Character character;
        
    /**
     * Crea el juego e inicializa su mapa interno.
     */
    public Game() {
        createRooms();
        character = new Character("player1", initialRoom);
        parser = new Parser();
    }

    /**
     * Crea todas las habitaciones y establece como se unen entre ellas.
     */
    private void createRooms() {
        Room bachiller, pasillo, fp, aula201, aula202, aula203;
        
        String description = "Las aulas estan cerradas y la salida de emergencia, bloqueada.\n" +
                             "No hay nada que hacer aqui aqui";
        String name = "el pasillo de bachiller";
        bachiller = (new Room(name, description));
     
        description = "Te encuentras entre el pasillo de FP y el de Bachiller.\n" + 
                      "Hay unos aseos, pero parecen fuera de servicio";
        name = "el pasillo principal"; 
        pasillo =new Room(name, description);

        description = "Se puede escuchar ruido en el aula 203 Las clase de Programacion ya  ha comenzado.";
        name = "el pasillo de fp";
        fp = new Room(name, description);

        
        description = "Un escalofrio te recorre.\n" +
                      "No puedes sentir compasion por los cientos de almas suspensas en este aula";
        name = "aula de examenes";
        aula201 = new Room(name, description);

        
        description = "Hay un par de repetidores abrumados por la tarea de Roberto.\n" +
                      "Una de las ventanas esta abierta y parece conducir al aula 203.";
        name = "aula de dam2";
        aula202 = new Room(name, description);

        
        description = "Los alumnos parecen estresados y abatidos\n" +
                      "Una de las ventanas esta abierta y parece conducir al aula 202.\n";
        name = "aula de dam1";
        aula203 = new Room(name, description);
        
        // Vincular salidas:
        bachiller.addExit("east", pasillo);
        pasillo.addExit("east", fp);
        pasillo.addExit("west", bachiller);
        fp.addExit("east", aula203);
        fp.addExit("south", aula201);
        fp.addExit("southeast", aula202);
        fp.addExit("west", pasillo);
        aula201.addExit("north", fp);
        aula202.addExit("northwest",fp);
        aula202.addExit("south",aula203);
        aula203.addExit("east",aula202);
        aula203.addExit("west",fp); 
        
        // Anadir objetos
        pasillo.addItem(new Item("alarma", "Una tradicional alarma de incendios", 100));
        aula201.addItem(new Item("esqueleto", "Pertenecio a un repetidor que jamas aprobo redes", 80));
        
        // Establece la habitacion inicial
        initialRoom = pasillo;
    }
    
    
    /**
     * Imprime la descripcion de la habitacion actual y sus salidas.
     */
    private void info() {
        character.look();
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Gracias por jugar. Hasta luego!");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() {
        System.out.println();
        System.out.println("Bienvenido a 'Zuul-Dani':");
        System.out.println("Escribe 'help' si necesitas ayuda.");
        System.out.println();
        info();
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
            character.look();
        }
        else if (commandWord.equals("eat")) {
            character.eat();
        }
        else if (commandWord.equals("back")) {
            character.back();
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
        character.moveTo(direction);
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
    
}
