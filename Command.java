
/**
 * Comando.
 * 
 * Command pertenece a la aplicacion 'sneak-in-class'.
 * 
 * Un comando es un conjunto de, como maximo, dos palabras que el juego puede procesar para realizar una accion. 
 * La primera palabra debe ser una palabra reservada y estar incluida en la lista de comandos validos.
 * La segunda palabra solo es utilizada cuando el juego necesita de un parametro adicional para ejecutar el comando.
 * 
 * Un comando cuya primera palabra sea 'null' se considera un comando desconocido.
 * Un comando cuya segunda palabra sea 'null' se considera un comando simple.
 *
 * @author d4s1ns
 * @version 2018/04/13
 */
public class Command {
    // Palabra reservada - determina la accion que debe realizar el juego.
    private String commandWord;
    // Parametro del comando - utilizado en comandos complejos.
    private String secondWord;
    
    /**
     * Constructor - Crea un comando especificando la primera palabra y la segunda, cualquiera de ellas o ambas pueden ser 'null'.
     * Si la primera palabra es 'null' se trata de un comando desconocido - no procesable.
     * @param firstWord Primera palabra del comando.
     * @param secondWord Segunda palabra del comando.
     */
    public Command(String firstWord, String secondWord) {
        commandWord = firstWord;
        this.secondWord = secondWord;
    }
    
    // INFORMACION DEL COMANDO.
    /**
     * Devuelve la primera palabra del comando si hay, si no hay, devuelve 'null'.
     * @return Devuelve la primera palabra del comando.
     */
    public String getCommandWord() {
        return commandWord;
    }
    
    /**
     * Devuelve la segunda palabra del comando si hay, si no hay, devuelve 'null'.
     * @return Devuelve la segunda palabra del comando si hay, si no hay, devuelve 'null'.
     */
    public String getSecondWord() {
        return secondWord;
    }
    
    /**
     * Devuelve verdadero si el comando tiene segunda palabra o, falso, si no la tiene.
     * @return Devuelve verdadero si el comando tiene segunda palabra o, falso, si no la tiene.
     */
    public boolean hasSecondWord() {
        return (secondWord != null);
    }
    
    /**
     * Devuelve verdadero si el comando es desconocido o, falso, si no lo es.
     * @return Devuelve verdadero si el comando es desconocido o, falso, si no lo es.
     */
    public boolean isUnknown() {
        return (commandWord == null);
    }
        
}
