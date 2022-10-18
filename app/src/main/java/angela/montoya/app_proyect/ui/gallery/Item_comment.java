package angela.montoya.app_proyect.ui.gallery;

public class Item_comment {
    String fecha;
    String comment;
    String nick;

    public Item_comment(String fecha, String comment, String nick) {
        this.fecha = fecha;
        this.comment = comment;
        this.nick = nick;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Item_comment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
