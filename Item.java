
/**
 * Un objeto del mundo del juego.
 * 
 * Item pertenece a la aplicacion 'sneak-in-class'.
 * 
 * Los objetos representan elementos del escenario. Existen objetos manipulables con los que el personaje puede interactuar y otros
 * no manipulables con los que no puede interactuar.
 *
 * @author d4s1ns
 * @version 2018/04/13
 */
public class Item {
    // Determina si se puede recoger o no. Recogible - Verdadero, No Recogible - Falso.
    boolean pickable;
    // Peso del objeto.
    int weight;
    
    // Descripcion del objeto.
    String description;
    // Nombre del objeto.
    String name;

    /**
     * Constructor - Crea un objeto indicando su nombre, su descripcion, su peso y si se puede recoger.
     * @param name Nombre unico e identificativo del objeto.
     * @param description Descripcion del objeto.
     * @param weight Peso del objeto.
     * @param usable Verdadero - Se puede recoger, Falso - No se puede recoger.
     */
    public Item(String name, String description, int weight, boolean pickable) {
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.pickable = pickable;
    }
    
    // INFORMACION DEL OBJETO.
    /**
     * Devuelve la descripcion del objeto.
     * @return Devuelve la descripcion del objeto.
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Devuelve el nombre del objeto.
     * @return Devuelve el nombre del objeto.
     */
    public String getName() {
        return name;
    } 
    
    /**
     * Devuelve el peso del objeto.
     * @return Devuelve el peso del objeto.
     */
    public int getWeight() {
        return weight;
    }
    
    /**
     * Devuelve 'true' si el objeto se puede recoger y 'false' si no.
     * @return Devuelve 'true' si el objeto se puede recoger y 'false' si no.
     */
    public boolean isPickable() {
        return pickable;
    }
    
    /**
     * Devuelve una cadena de texto con toda la informacion acerca del objeto.
     * @return Devuelve una cadena de texto con toda la informacion acerca del objeto.
     */
    public String toString() {
        return "[" + name + "]:\n" + description + "\nPeso: " + weight + "\nRecogible: " + pickable;
    }
    
}
