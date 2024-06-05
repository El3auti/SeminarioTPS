import config.ManagerBD;
import controller.Ticket;
import controller.Persona;
import controller.Producto;
import controller.Notificacion;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Ticket ticketController = new Ticket();
        Persona personaController = new Persona();
        Producto productoController = new Producto();

        boolean salir = false;
        while (!salir) {
            System.out.println("Menú CRUD para Tickets:");
            System.out.println("1. Crear ticket");
            System.out.println("2. Leer ticket");
            System.out.println("3. Actualizar ticket");
            System.out.println("4. Eliminar ticket");
            System.out.println("5. Enviar notificación");
            System.out.println("6. Salir");
            System.out.print("Ingrese una opción: ");

            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    // Crear ticket
                    System.out.print("Ingrese la descripción del ticket: ");
                    String descripcion = scanner.next();
                    System.out.print("Ingrese el mensaje del ticket: ");
                    String mensaje = scanner.next();

                    // Crear persona asociada
                    System.out.print("Ingrese el nombre de la persona: ");
                    String nombrePersona = scanner.next();
                    System.out.print("Ingrese el teléfono de la persona: ");
                    String telefonoPersona = scanner.next();
                    System.out.print("Ingrese el email de la persona: ");
                    String emailPersona = scanner.next();
                    Persona nuevaPersona = new Persona(nombrePersona, telefonoPersona, emailPersona);

                    // Crear producto asociado
                    System.out.print("Ingrese la descripción del producto: ");
                    String descripcionProducto = scanner.next();
                    Producto nuevoProducto = new Producto(descripcionProducto);

                    Ticket nuevoTicket = new Ticket(descripcion, mensaje, nuevaPersona, nuevoProducto);
                    boolean ticketCreado = ticketController.create(nuevoTicket);
                    if (ticketCreado) {
                        System.out.println("Ticket creado exitosamente.");
                    } else {
                        System.out.println("Error al crear el ticket.");
                    }
                    break;
                case 2:
                    // Leer ticket
                    System.out.print("Ingrese el ID del ticket a leer: ");
                    int idLeer = scanner.nextInt();
                    Ticket ticketLeido = ticketController.read(idLeer);
                    if (ticketLeido != null) {
                        System.out.println("Ticket encontrado:");
                        System.out.println("Descripción: " + ticketLeido.getDescripcion());
                        System.out.println("Mensaje: " + ticketLeido.getMensaje());
                        System.out.println("Persona: " + ticketLeido.getPersona().getNombre());
                        System.out.println("Producto: " + ticketLeido.getProducto().getDescripcionProblema());
                    } else {
                        System.out.println("No se encontró ningún ticket con ese ID.");
                    }
                    break;
                case 3:
                    // Actualizar ticket
                    System.out.print("Ingrese el ID del ticket a actualizar: ");
                    int idActualizar = scanner.nextInt();
                    System.out.print("Ingrese la nueva descripción del ticket: ");
                    String nuevaDescripcion = scanner.next();
                    System.out.print("Ingrese el nuevo mensaje del ticket: ");
                    String nuevoMensaje = scanner.next();

                    // Actualizar persona asociada
                    System.out.print("Ingrese el nuevo nombre de la persona: ");
                    String nuevoNombrePersona = scanner.next();
                    System.out.print("Ingrese el nuevo teléfono de la persona: ");
                    String nuevoTelefonoPersona = scanner.next();
                    System.out.print("Ingrese el nuevo email de la persona: ");
                    String nuevoEmailPersona = scanner.next();
                    Persona personaActualizada = new Persona(nuevoNombrePersona, nuevoTelefonoPersona, nuevoEmailPersona);

                    // Actualizar producto asociado
                    System.out.print("Ingrese la nueva descripción del producto: ");
                    String nuevaDescripcionProducto = scanner.next();
                    Producto productoActualizado = new Producto(nuevaDescripcionProducto);

                    Ticket ticketActualizado = new Ticket(nuevaDescripcion, nuevoMensaje, personaActualizada, productoActualizado);
                    boolean actualizado = ticketController.update(ticketActualizado, idActualizar);
                    if (actualizado) {
                        System.out.println("Ticket actualizado exitosamente.");
                    } else {
                        System.out.println("Error al actualizar el ticket.");
                    }
                    break;
                case 4:
                    // Eliminar ticket
                    System.out.print("Ingrese el ID del ticket a eliminar: ");
                    int idEliminar = scanner.nextInt();
                    boolean eliminado = ticketController.delete(idEliminar);
                    if (eliminado) {
                        System.out.println("Ticket eliminado exitosamente.");
                    } else {
                        System.out.println("Error al eliminar el ticket.");
                    }
                    break;
                case 5:
                    // Enviar notificación
                    System.out.print("Ingrese el ID del ticket para enviar la notificación: ");
                    int idNotificacion = scanner.nextInt();
                    Ticket ticketParaNotificacion = ticketController.read(idNotificacion);
                    if (ticketParaNotificacion != null) {
                        System.out.println("El mensaje actual del ticket es: " + ticketParaNotificacion.getMensaje());
                        System.out.print("¿Desea usar un mensaje diferente? (s/n): ");
                        String respuesta = scanner.next();
                        if (respuesta.equalsIgnoreCase("s")) {
                            System.out.print("Ingrese el mensaje personalizado: ");
                            scanner.nextLine();  // Limpiar el buffer
                            String mensajePersonalizado = scanner.nextLine();
                            Notificacion notificacion = new Notificacion(mensajePersonalizado, ticketParaNotificacion);
                            notificacion.enviarNotificacion(mensajePersonalizado);
                        } else {
                            Notificacion notificacion = new Notificacion(ticketParaNotificacion.getMensaje(), ticketParaNotificacion);
                            notificacion.enviarNotificacion();
                        }
                    } else {
                        System.out.println("No se encontró ningún ticket con ese ID.");
                    }
                    break;
                case 6:
                    // Salir
                    salir = true;
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
                    break;
            }
        }

        scanner.close();

        try {
            ManagerBD.getInstance().getConnection().close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión a la base de datos: " + e.getMessage());
        }
    }
}
