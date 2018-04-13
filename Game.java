import java.util.Stack;
import java.util.HashMap;

/**
 * Representa el
 * Para jugar, crea una instancia de esta clase.
 * 
 * 
 *  
 * Game pertenece a la aplicacion 'sneak-in-class'.
 * 
 * @author  D4s1ns
 * @version 2018.04.13
 */

public class Game  {
    // Impedimento maximo para todos los personajes.
    private final static int MAX_CARGO = 100;
    
    // Personaje del jugador.
    private Character character;
    // Sala inicial.
    private Room initialRoom;
    // Analizador de sintaxis.
    private Parser parser;
        
    /**
     * Constructor - Crea e inizializa el escenario, el analizador de sintaxis y el personaje.
     */
    public Game() {
        createRooms();
        character = new Character("John Doe", initialRoom, MAX_CARGO);
        parser = new Parser();
    }
    
    // TRIPAS DEL JUEGO.
    /**
     * Crea todas las habitaciones y establece como se unen entre ellas.
     */
    private void createRooms() {
        Room bachiller, pasillo, fp, aula201, aula202, aula203;
        
        String description = "Las aulas estan cerradas y la salida de emergencia, bloqueada.\n" +
                             "No hay nada que hacer aqui aqui";
        String name = "el pasillo de bachiller";
        bachiller = (new Room("00", name, description));
     
        description = "Te encuentras entre el pasillo de FP y el de Bachiller.\n" + 
                      "Hay unos aseos, pero parecen fuera de servicio";
        name = "el pasillo principal"; 
        pasillo =new Room("01", name, description);

        description = "Se puede escuchar ruido en el aula 203 Las clase de Programacion ya  ha comenzado.";
        name = "el pasillo de fp";
        fp = new Room("02", name, description);

        
        description = "Un escalofrio te recorre.\n" +
                      "No puedes sentir compasion por los cientos de almas suspensas en este aula";
        name = "aula de examenes";
        aula201 = new Room("03", name, description);

        
        description = "Hay un par de repetidores abrumados por la tarea de Roberto.\n" +
                      "Una de las ventanas esta abierta y parece conducir al aula 203.";
        name = "aula de dam2";
        aula202 = new Room("04", name, description);

        
        description = "Los alumnos parecen estresados y abatidos\n" +
                      "Una de las ventanas esta abierta y parece conducir al aula 202.\n";
        name = "aula de dam1";
        aula203 = new Room("05", name, description);
        
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
        pasillo.addItem(new Item("alarma", "Una tradicional alarma de incendios", 100, true));
        aula201.addItem(new Item("esqueleto", "Pertenecio a un repetidor que jamas aprobo redes", 80, true));
        
        // Establece la habitacion inicial
        initialRoom = pasillo;
    }
    
    /**
     * Comprueba si un comando es un comando complejo.
     * Los comandos complejos son aquellos que tienen segunda palabra.
     * @param command Comando a comprobar.
     * @return Verdadero si es un comando complejo o, falso, si no lo es.
     */
    private boolean isComplex(Command command) {
        boolean refund = command.hasSecondWord();
        if (! refund) {
            System.out.println("Este comando necesita de una segunda palabra.");
        }
        return refund;
    }

    /**
     * Rutina principal del juego - Se repite hasta el final de la partida.
     */
    public void play() {            
        welcome();

        // Entra en el bucle principal. 
        // Este lee reiterativamente los comandos del jugador y los ejecuta hasta quel juego acaba.
                       
        boolean finished = false;
        while (! finished) {
            Command command = parser.nextCommand();
            finished = processCommand(command);
        }
        System.out.println("Gracias por jugar. Hasta luego!");
    }

    /**
     * Ejecuta el comando indicado.
     * @param command Comando a ejecutar.
     * @return Devuelve verdadero si el comando termina el juego o, falso, en cualquier otro caso.
     */
    private boolean processCommand(Command command) {
        boolean wantToQuit = false;
        if(command.isUnknown()) {
            System.out.println("No se reconoce el comando.");
        }
        else {
            String commandWord = command.getCommandWord();
            if (commandWord.equals("help")) {
                help();
            }
            else if (commandWord.equals("go")) {
                if (isComplex(command)) {
                    character.goTo(command.getSecondWord());
                }
            }
            else if (commandWord.equals("quit")) {
                wantToQuit = quit(command);
            }
            else if (commandWord.equals("look")) {
                character.look();
            }
            else if (commandWord.equals("items")) {
                character.inventory();
            }
            else if (commandWord.equals("take")) {
                if (isComplex(command)) {
                    character.pick(command.getSecondWord());
                }
            }
            else if (commandWord.equals("drop")) {
                if (isComplex(command)) {
                    character.drop(command.getSecondWord());
                }
            }
        }
        return wantToQuit;
    }
    
    /**
     * Muestra por pantalla el mensaje de bienvenida del juego.
     */
    private void welcome() {
        System.out.println("Bienvenido a 'sneak-in-class'");
        System.out.println("Sneak in Class es un juego de rol basado en texto.");
        System.out.println("El objetivo consiste en llegar al aula 203 tarde sin tener que pagar la multa.");
        System.out.println();
        System.out.println("Escribe 'help' si necesitas ayuda.");
        System.out.println("Buena suerte!");
        System.out.println();
        character.look();
    }

    // ACCIONES DE LOS COMANDOS:
    /**
     * Muestra la lista de comandos del juego.
     */
    private void help() {
        System.out.println("Bienvenido a la ayuda del juego.");
        System.out.println("Los comandos disponibles son:");
        parser.commandsList();
    }
    
    /** 
     * Termina la partida.
     * @return Devuelve verdadero si este comando realmente termina la partida o, falso, si no lo hace.
     */
    private boolean quit(Command command) {
        boolean refund = false;
        if(command.hasSecondWord()) {
            System.out.println("Terminar que?");
        }
        else {
            refund = true;
        }
        return refund;
    }
    
}
