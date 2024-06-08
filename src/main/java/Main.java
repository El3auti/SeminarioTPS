import config.ManagerBD;
import controller.Ticket;
import controller.Persona;
import controller.Producto;
import controller.Notificacion;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    // Método para imprimir el menú con estilo y emoticonos
    private static void printMenu() {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║        🎟️ Menú CRUD para Tickets 🎟️      ║");
        System.out.println("╠════════════════════════════════════╣");
        System.out.println("║ 1. 📝 Crear ticket                   ║");
        System.out.println("║ 2. 🔍 Leer ticket                    ║");
        System.out.println("║ 3. ✏️ Actualizar ticket              ║");
        System.out.println("║ 4. 🗑️ Eliminar ticket                ║");
        System.out.println("║ 5. 📩 Enviar notificación            ║");
        System.out.println("║ 6. 🚪 Salir                          ║");
        System.out.println("╚════════════════════════════════════╝");
        System.out.print("Ingrese una opción: ");
    }

    // Método para imprimir mensajes con estilo y emoticonos
    private static void printMessage(String message) {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║ " + message + " 😊" +             "║");
        System.out.println("╚════════════════════════════════════╝\n");
    }

    // Método para imprimir un ticket con estilo y emoticonos
    private static void printTicket(Ticket ticket) {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║          🎫 Ticket encontrado 🎫     ║");
        System.out.println("╠════════════════════════════════════╣");
        System.out.println("║ Descripción: " + ticket.getDescripcion());
        System.out.println("║ Mensaje: " + ticket.getMensaje());
        System.out.println("║ Persona: " + ticket.getPersona().getNombre());
        System.out.println("║ Producto: " + ticket.getProducto().getNombre() + " - " + ticket.getProducto().getDescripcionProblema());
        System.out.println("╚════════════════════════════════════╝\n");
    }

    public static void main(String[] args) {

        ManagerBD managerdb = ManagerBD.getInstance();
        Scanner scanner = new Scanner(System.in);

        boolean salir = false;
        while (!salir) {
            printMenu();

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    // Crear ticket
                    System.out.print("📝 Ingrese la descripción del ticket: ");
                    String descripcion = scanner.nextLine();
                    System.out.print("📝 Ingrese el mensaje del ticket: ");
                    String mensaje = scanner.nextLine();

                    // Crear persona asociada
                    System.out.print("📝 Ingrese el nombre de la persona: ");
                    String nombrePersona = scanner.nextLine();
                    System.out.print("📝 Ingrese el teléfono de la persona: ");
                    String telefonoPersona = scanner.nextLine();
                    System.out.print("📝 Ingrese el email de la persona: ");
                    String emailPersona = scanner.nextLine();
                    Persona nuevaPersona = new Persona(nombrePersona, telefonoPersona, emailPersona);

                    // Crear producto asociado
                    System.out.print("📝 Ingrese el nombre del producto: ");
                    String nombreProducto = scanner.nextLine();
                    System.out.print("📝 Ingrese la descripción del problema del producto: ");
                    String descripcionProducto = scanner.nextLine();
                    Producto nuevoProducto = new Producto(nombreProducto, descripcionProducto);

                    Ticket nuevoTicket = new Ticket(descripcion, mensaje, nuevaPersona, nuevoProducto);
                    boolean ticketCreado = nuevoTicket.create(nuevoTicket);
                    if (ticketCreado) {
                        printMessage("Ticket creado exitosamente.");
                    } else {
                        printMessage("Error al crear el ticket.");
                    }
                    break;
                case 2:
                    // Leer ticket
                    System.out.print("🔍 Ingrese el ID del ticket a leer: ");
                    int idLeer = scanner.nextInt();
                    scanner.nextLine(); // Limpiar el buffer
                    Ticket ticketParaLeer = new Ticket(null, null, null, null);
                    Ticket ticketLeido = ticketParaLeer.read(idLeer);
                    if (ticketLeido != null) {
                        printTicket(ticketLeido);
                    } else {
                        printMessage("No se encontró ningún ticket con ese ID.");
                    }
                    break;
                case 3:
                    // Actualizar ticket
                    System.out.print("✏️ Ingrese el ID del ticket a actualizar: ");
                    int idActualizar = scanner.nextInt();
                    scanner.nextLine(); // Limpiar el buffer
                    System.out.print("✏️ Ingrese la nueva descripción del ticket: ");
                    String nuevaDescripcion = scanner.nextLine();
                    System.out.print("✏️ Ingrese el nuevo mensaje del ticket: ");
                    String nuevoMensaje = scanner.nextLine();

                    // Actualizar persona asociada
                    System.out.print("✏️ Ingrese el nuevo nombre de la persona: ");
                    String nuevoNombrePersona = scanner.nextLine();
                    System.out.print("✏️ Ingrese el nuevo teléfono de la persona: ");
                    String nuevoTelefonoPersona = scanner.nextLine();
                    System.out.print("✏️ Ingrese el nuevo email de la persona: ");
                    String nuevoEmailPersona = scanner.nextLine();
                    Persona personaActualizada = new Persona(nuevoNombrePersona, nuevoTelefonoPersona, nuevoEmailPersona);

                    // Actualizar producto asociado
                    System.out.print("✏️ Ingrese el nuevo nombre del producto: ");
                    String nuevoNombreProducto = scanner.nextLine();
                    System.out.print("✏️ Ingrese la nueva descripción del problema del producto: ");
                    String nuevaDescripcionProducto = scanner.nextLine();
                    Producto productoActualizado = new Producto(nuevoNombreProducto, nuevaDescripcionProducto);

                    Ticket ticketActualizado = new Ticket(nuevaDescripcion, nuevoMensaje, personaActualizada, productoActualizado);
                    boolean actualizado = ticketActualizado.update(ticketActualizado, idActualizar);
                    if (actualizado) {
                        printMessage("Ticket actualizado exitosamente.");
                    } else {
                        printMessage("Error al actualizar el ticket.");
                    }
                    break;
                case 4:
                    // Eliminar ticket
                    System.out.print("🗑️ Ingrese el ID del ticket a eliminar: ");
                    int idEliminar = scanner.nextInt();
                    scanner.nextLine(); // Limpiar el buffer
                    Ticket ticketParaEliminar = new Ticket(null, null, null, null);
                    boolean eliminado = ticketParaEliminar.delete(idEliminar);
                    if (eliminado) {
                        printMessage("Ticket eliminado exitosamente.");
                    } else {
                        printMessage("Error al eliminar el ticket.");
                    }
                    break;
                case 5:
                    // Enviar notificación
                    System.out.print("📩 Ingrese el ID del ticket para enviar la notificación: ");
                    int idNotificacion = scanner.nextInt();
                    scanner.nextLine(); // Limpiar el buffer
                    Ticket ticketParaNotificacion = new Ticket(null, null, null, null);
                    Ticket ticketLeidoParaNotificacion = ticketParaNotificacion.read(idNotificacion);
                    if (ticketLeidoParaNotificacion != null) {
                        printMessage("El mensaje actual del ticket es: " + ticketLeidoParaNotificacion.getMensaje());
                        System.out.print("¿Desea usar un mensaje diferente? (s/n): ");
                        String respuesta = scanner.nextLine();
                        if (respuesta.equalsIgnoreCase("s")) {
                            System.out.print("📩 Ingrese el mensaje personalizado: ");
                            String mensajePersonalizado = scanner.nextLine();
                            Notificacion notificacion = new Notificacion(mensajePersonalizado, ticketLeidoParaNotificacion);
                            notificacion.enviarNotificacion(mensajePersonalizado);
                        } else {
                            Notificacion notificacion = new Notificacion(ticketLeidoParaNotificacion.getMensaje(), ticketLeidoParaNotificacion);
                            notificacion.enviarNotificacion();
                        }
                    } else {
                        printMessage("No se encontró ningún ticket con ese ID.");
                    }
                    break;
                case 6:
                    salir = true;
                    printMessage("Saliendo del programa...");
                    break;
                default:
                    printMessage("Opción inválida. Por favor, seleccione una opción válida.");
                    break;
            }
        }

        scanner.close();

        try {
            managerdb.getConnection().close();
        } catch (SQLException e) {
            printMessage("Error al cerrar la conexión a la base de datos: " + e.getMessage());
        }
    }
}