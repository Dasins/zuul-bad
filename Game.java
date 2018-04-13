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
    // Mapa inicial
    private final static String INITIAL_MAP = "segunda_planta";
    
    // Constructor de escenarios.
    private Builder builder;
    // Personaje del jugador.
    private Character character;
    // Analizador de sintaxis.
    private Parser parser;
    
    private boolean endable;
        
    /**
     * Constructor - Crea e inizializa el escenario, el analizador de sintaxis y el personaje.
     */
    public Game() {
        builder = new Builder();
        builder.build(INITIAL_MAP);
        character = new Character("John Doe", builder.getSpawnRoom(), MAX_CARGO);
        parser = new Parser();
        endable = false;
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
                System.out.println(character.inventory());
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
            else if (commandWord.equals("use")) {
                if (isComplex(command)) {
                    if(character.use(command.getSecondWord())) {
                        builder.changeRoomDescription("el aula 203", "Todos han salido fuera al sonar la alarma.");
                        System.out.println("La alarma de incendios esta sonando y la gente abandona el aula 203.");
                        endable = true;
                    }
                }
            }
            else if (commandWord.equals("sit")) {
                if (character.actualRoom().equals("el aula 203")) {
                   if(endable) {
                       System.out.println("Enhorabuena, has evadido la multa un dia mas.");
                   }
                   else {
                       System.out.println("La mirada ardiente del Felegado se ha psoado sobre ti. GAME OVER");
                   }
                   wantToQuit = true;
                }
                else {
                    System.out.println("Esta no es tu clase.");
                }
            }
            System.out.println();
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
