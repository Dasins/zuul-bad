
/**
 * Representa un objeto del mundo del juego.
 * 
 * Item pertenece a la aplicacion zuul-dani.
 * 
 * Los objetos tiene un peso y pueden ser manipuables o no.
 * 
 * @author d4s1ns
 * @version 2018/03/12
 */
public class Item {
    // Nombre del objeto.
    String name;
    // Descripcion del objeto.
    String description;
    // Peso del objeto.
    int weight;
    // Determina si se puede manipular o no. (True, manipulable).
    boolean usable;
    
    /**
     * Constructor
     * 
     * Construye objetos que por defecto son manipuables.
     * Para crear un objeto se debe especificar un nombre unico e identificativo, la descripcion del objeto y su peso.
     * 
     * @param name El nombre del objeto.
     * @param weight El peso del objeto en cuestion.
     */
    public Item(String name, String description, int weight) {
        this.name = name;
        this.description = description;
        this.weight = weight;
        usable = true;
    }
    
    /**
     * Constructor
     * 
     * Construye objetos personalizados
     * Para crear un objeto se debe especificar un nombre unico e identificativo, la descripcion del objeto su peso
     * y si es manipulable.
     * 
     * 
     * @param name El nombre del objeto.
     * @param weight El peso del objeto en cuestion.
     */
    public Item(String name, String description, int weight, boolean usable) {
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.usable = usable;
    }
    
    /**
     * Devuelve el nombre del objeto.
     * @return Devuelve el nombre del objeto.
     */
    public String name() {
        return name;
    }
    
    /**
     * Devuelve la descripcion del objeto.
     * @return Devuelve la descripcion del objeto.
     */
    public String description() {
        return description;
    }
    
    /**
     * Devuelve el peso del objeto.
     * @return Devuelve el peso del objeto.
     */
    public int weight() {
        return weight;
    }
    
    /**
     * Devuelve toda la informacion del objeto.
     * @return Devuelve toda la informacion del objeto.
     */
    public String info() {
        return "[" + name.toUpperCase() + "]:\n" + description + "\n" + weight;
    }
    
    /**
     * Devuelve true si el objeto es manipuable y false si no.
     * @return Devuelve true si el objeto es manipuable y false si no.
     */
    public boolean isUsable() {
        return usable;
    }
}
