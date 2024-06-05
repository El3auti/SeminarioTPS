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
        String url = "https://api.whatsapp.com/send?phone=" + telefono + "&text=" + mensaje;
        System.out.println("Enlace de notificación: " + url);
    }

    // Método para enviar notificación con mensaje personalizado
    public void enviarNotificacion(String mensajePersonalizado) {
        Persona persona = ticket.getPersona();
        String telefono = persona.getTelefono();
        String url = "https://api.whatsapp.com/send?phone=" + telefono + "&text=" + mensajePersonalizado;
        System.out.println("Enlace de notificación personalizada: " + url);
    }
}
