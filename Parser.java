import java.util.Scanner;

/**
 * Analizador de sintaxis.
 * 
 * Parser pertenece a la aplicacion 'sneak-in-class';
 * 
 * Un analizador sintactico lee las entradas del jugador y trata de interpretarlas como un comando del juego.
 * Cada vez que es llamado, lee la primera linea de la terminal y trata de interpretarla como un comando formado por dos palabras.
 * 
 * Si la primera palabra no se encuentra en la lista de comandos conocidos, el comando se interpreta como nulo.
 * 
 * El analizador sintactico tiene una lista de comandos conocidos. Las entradas de terminal se verifican contra esta lista.
 * 
 * @author d4s1ns
 * @version 2018/04/13
 */
public class Parser {
    // Lista de los comandos conocidos.
    private CommandWords commands;
    // Fuente de las entradas del analizador.
    private Scanner reader;

    /**
     * Constructor - Crea un analizador sintactico que lea las entradas de la terminal de texto.
     */
    public Parser() {
        commands = new CommandWords();
        reader = new Scanner(System.in);
    }

    /**
     * Devuelve el siguiente comando del jugador.
     * @return Devuelve el siguiente comando del jugador.
     */
    public Command nextCommand() {
        String inputLine;   // Entrada de texto completa.
        String word1 = null;
        String word2 = null;

        System.out.print("> ");     // Prompt.

        inputLine = reader.nextLine();

        // Encuentra dos palabras en la entrada de texto.
        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next();      // Primera palabra.
            if(tokenizer.hasNext()) {
                word2 = tokenizer.next();      // Segunda palabra.
                // Se ignoran el resto de palabras de la linea..
            }
        }

        // Comprueba si la primera palabra es un comando conocido.
        // Si lo es, crea un comando con ella, si no, crea un comando desconocido.
        Command refund;
        if(commands.isCommand(word1)) {
            refund = new Command(word1, word2);
        }
        else {
            refund = new Command(null, word2); 
        }
        return refund;
    }
    
    /**
     * Muestra por terminal una lista con todos los comandos conocidos.
     */
    public void commandsList() {
        System.out.println(commands);
    }
}
