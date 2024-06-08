package controller;

public class Notificacion {
    private String mensaje;
    private Ticket ticket;

    // Constructor
    public Notificacion(String mensaje, Ticket ticket) {
        this.mensaje = mensaje;
        this.ticket = ticket;
    }

    // Getters y Setters
    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    // Método para enviar notificación
    public void enviarNotificacion() {
        Persona persona = ticket.getPersona();
        String telefono = persona.getTelefono();
        String mensaje = ticket.getMensaje();
        String url = "https://api.whatsapp.com/send?phone=" + telefono + "&text=" + mensaje.replace(" ", "%20");
        System.out.println("Enlace para notificar: " + url);
    }

    public void enviarNotificacion(String mensajePersonalizado) {
        Persona persona = ticket.getPersona();
        String telefono = persona.getTelefono();
        String url = "https://api.whatsapp.com/send?phone=" + telefono + "&text=" + mensajePersonalizado.replace(" ", "%20");
        System.out.println("Enlace de notificación personalizada: " + url);
    }

}
