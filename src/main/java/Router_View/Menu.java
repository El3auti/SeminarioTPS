package Router_View;

import config.ManagerBD;
import controller.Notificacion;
import controller.Persona;
import controller.Producto;
import controller.Ticket;

import java.util.Scanner;

public class Menu {

    static ManagerBD managerdb = ManagerBD.getInstance();
    static Scanner scanner = new Scanner(System.in);
    static Ticket ticketmaster = new Ticket();

    public static ManagerBD getManagerdb() {
        return managerdb;
    }

    // Método para imprimir el menú con estilo y emoticonos
    public static void printOptionsMenu() {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║        🎟️ SupportTicketMaster 🎟️ ║");
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

    public static void printMessageResult(String message) {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println( message );
        System.out.println("╚════════════════════════════════════╝\n");
    }

    // Método para imprimir un ticket con estilo y emoticonos
    public static void printTicketResultResult(Ticket ticket) {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║          🎫 Ticket encontrado 🎫    ║");
        System.out.println("╠════════════════════════════════════╣");
        System.out.println("║ Descripción: " + ticket.getDescripcion());
        System.out.println("║ Mensaje: " + ticket.getMensaje());
        System.out.println("║ Persona: " + ticket.getPersona().getNombre());
        System.out.println("║ Producto: " + ticket.getProducto().getNombre() + " - " + ticket.getProducto().getDescripcionProblema());
        System.out.println("╚════════════════════════════════════╝\n");
    }

    public static void StartMenu(){
        boolean salir = false;
        while (!salir) {
            printOptionsMenu();

            int opcion = scanner.nextInt();
            scanner.nextLine();

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

                    boolean ticketCreado = ticketmaster.create(nuevoTicket);
                    if (ticketCreado) {
                        printMessageResult("Ticket creado exitosamente.");
                    } else {
                        printMessageResult("Error al crear el ticket.");
                    }
                    break;
                case 2:
                    // Leer ticket
                    System.out.println("=============================");
                    System.out.println(ticketmaster.DisplayTicketsIDS());
                    System.out.println("=============================");
                    System.out.print("🔍 Ingrese el ID del ticket a leer: ");
                    int idLeer = scanner.nextInt();
                    scanner.nextLine();
                    Ticket ticketLeido = ticketmaster.read(idLeer);
                    if (ticketLeido != null) {
                        printTicketResultResult(ticketLeido);
                    } else {
                        printMessageResult("No se encontró ningún ticket con ese ID.");
                    }
                    break;
                case 3:
                    // Actualizar ticket
                    System.out.println("=============================");
                    System.out.println(ticketmaster.DisplayTicketsIDS());
                    System.out.println("=============================");
                    System.out.print("✏️ Ingrese el ID del ticket a actualizar: ");
                    int idActualizar = scanner.nextInt();
                    scanner.nextLine();
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
                    boolean actualizado = ticketmaster.update(ticketActualizado, idActualizar);
                    if (actualizado) {
                        printMessageResult("Ticket actualizado exitosamente.");
                    } else {
                        printMessageResult("Error al actualizar el ticket.");
                    }
                    break;
                case 4:
                    // Eliminar ticket
                    System.out.println("=============================");
                    System.out.println(ticketmaster.DisplayTicketsIDS());
                    System.out.println("=============================");
                    System.out.print("🗑️ Ingrese el ID del ticket a eliminar: ");
                    int idEliminar = scanner.nextInt();
                    scanner.nextLine();
                    boolean eliminado = ticketmaster.delete(idEliminar);
                    if (eliminado) {
                        printMessageResult("Ticket eliminado exitosamente.");
                    } else {
                        printMessageResult("Error al eliminar el ticket.");
                    }
                    break;
                case 5:
                    // Enviar notificación
                    System.out.println("=============================");
                    System.out.println(ticketmaster.DisplayTicketsIDS());
                    System.out.println("=============================");
                    System.out.print("📩 Ingrese el ID del ticket para enviar la notificación: ");
                    int idNotificacion = scanner.nextInt();
                    scanner.nextLine();
                    Ticket ticketLeidoParaNotificacion = ticketmaster.read(idNotificacion);
                    if (ticketLeidoParaNotificacion != null) {
                        printMessageResult("El mensaje actual del ticket es: " + ticketLeidoParaNotificacion.getMensaje());
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
                        printMessageResult("No se encontró ningún ticket con ese ID.");
                    }
                    break;
                case 6:
                    salir = true;
                    printMessageResult("Saliendo del programa...");
                    break;
                default:
                    printMessageResult("Opción inválida. Por favor, seleccione una opción válida.");
                    break;
            }
        }

        scanner.close();
    }
}
