/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.dam.carritocompra;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author javier
 */
public class CarritoCompra {

    public static void main(String[] args) {
        // Nombre del autor
        String autor = "Ismael Botana Castro";
        
        // Obtener fecha y hora actual
        LocalDateTime ahora = LocalDateTime.now();
        
        // Formatear la fecha y hora
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String fechaHora = ahora.format(formato);
        
        // Mostrar por pantalla
        System.out.println("Autor: " + autor);
        System.out.println("Fecha: " + fechaHora);
        System.out.println("=====================================\n");
        
        // Aquí va el resto de tu programa...
        // Para que puedan realizar pruebas.
        Tienda tienda = new Tienda("Tienda DAM");

        ProductoUnidad p1 = new ProductoUnidad("U01", "Cuaderno", 2.5, 20);
        ProductoUnidad p2 = new ProductoUnidad("U02", "Bolígrafo", 1.2, 50);
        ProductoPeso p3 = new ProductoPeso("P01", "Manzanas", 0, 1.8, 5.0);

        tienda.agregarProducto(p1);
        tienda.agregarProducto(p2);
        tienda.agregarProducto(p3);

        tienda.mostrarCatalogo();

        Carrito carrito = new Carrito();

        tienda.comprar(carrito, "U01", 2);
        tienda.comprar(carrito, "P01", 1.5);
        tienda.comprar(carrito, "U02", 3);

        carrito.mostrarTicket();
    }
}
