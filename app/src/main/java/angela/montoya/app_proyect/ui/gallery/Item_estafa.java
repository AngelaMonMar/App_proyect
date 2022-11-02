package angela.montoya.app_proyect.ui.gallery;

import java.io.Serializable;

public class Item_estafa implements Serializable {
    private String color;
    int id;
    private String category;
    private String titulo;
    private String fecha;
    private String contenido;

    public Item_estafa(int id, String color,String titulo ,String category,  String fecha, String contenido) {
        this.id=id;
        this.color = color;
        this.titulo = titulo;
        this.category = category;
        this.fecha = fecha;
        this.contenido=contenido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
