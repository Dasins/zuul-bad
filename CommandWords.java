/**
 * Lista de los comandos conocidos por el juego.
 * 
 * CommandsList pertenece a la aplicacion 'sneak-in-class'.
 * 
 * @author  d4s1ns
 * @version 2018/03/25
 */

public class CommandWords {
    // Lista de comandos conocidos.
    private static final String[] validCommands = {
        "go", "quit", "help", "look", "take", "drop", "items"
    };

    /**
     * Constructor.
     */
    public CommandWords() {
        // Nada que hacer aqui por ahora.
    }

    /**
     * Comprueba si la palabra indicada esta en la lista de comandos validos.
     * @param commandWord La palabra que se comprueba si es un comando valido.
     * @return Devuelve verdadero si la palabra indicada esta en la lista de comandos validos o falso, si no lo esta.
     */
    public boolean isCommand(String commandWord) {
        boolean searching = true, refund = false;
        int i = 0; 
        while (searching && i < validCommands.length) {
            if (validCommands[i].equals(commandWord)) {
                searching = false;
                refund = true;
            }
            i++;
        }
        return refund;
    }
    
    /**
     * Devuelve como una cadena de texto todos los comandos conocidos por el juego.
     * @return Devuelve como una cadena de texto todos los comandos conocidos por el juego.
     */
    public String toString() {
        String refund = "";
        for (String commandWord : validCommands) {
            refund += commandWord + ", ";
        }        
        return refund;
    }
}
