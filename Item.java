
/**
 * Clase que modela objetos que simulan objetos de nuestro mundo.
 * 
 * @author d4s1ns
 * @version 2018/03/12
 */
public class Item {
    // ID
    String id;
    // Nombre
    String nombre;
    // Peso
    int peso;
    
    /**
     * Constructor de objetos Item
     * 
     * @param nombre El nombre del objeto, por ejemplo "cofre".
     * @param peso El peso del objeto en cuestion.
     */
    public Item(String nombre, int peso) {
        this.nombre = nombre;
        this.peso = peso;
        id = nombre.split(" ")[0].toLowerCase();
    }
    
    /**
     * Devuelve el nombre del item.
     * @return Devuelve el nombre del item.
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Devuelve el id del item.
     * @return Devuelve el id del item.
     */
     public String getID() {
        return id;
    }
    
    /**
     * Devuelve el peso del item.
     * @return Devuelve el peso del item.
     */
    public int getPeso() {
        return peso;
    }
    
    /**
     * Devuelve una cadena con toda la informacion del item.
     * @return Devuelve una cadena con toda la informacion del item.
     */
    public String getInfo() {
        return "[" + id + "] " + nombre + " peso: " + peso;
    }
}
