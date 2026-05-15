package com.dam.carritocompra;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CarritoAutocorreccionTest {

  private static final double EPS = 1e-6;

  @Test
  void autocorreccion_carrito_poo() {
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

    double puntos = 0.0;

    System.out.println("========================================");
    System.out.println("AUTOCORRECCIÓN - CARRITO POO");
    System.out.println("========================================");

    if (evalProductoBasico()) {
      puntos = puntos + 0.5;
      System.out.println("Producto básico ............ OK (0.5 puntos)");
    } else {
      System.out.println("Producto básico ............ ERROR (0 puntos)");
    }

    if (evalProductosEspecializados()) {
      puntos = puntos + 0.5;
      System.out.println("Productos especializados ... OK (0.5 puntos)");
    } else {
      System.out.println("Productos especializados ... ERROR (0 puntos)");
    }

    if (evalCarritoTotales()) {
      puntos = puntos + 1.5;
      System.out.println("Carrito y totales .......... OK (1.5 puntos)");
    } else {
      System.out.println("Carrito y totales .......... ERROR (0 puntos)");
    }

    if (evalTiendaComprar()) {
      puntos = puntos + 1.5;
      System.out.println("Tienda.comprar ............. OK (1.5 puntos)");
    } else {
      System.out.println("Tienda.comprar ............. ERROR (0 puntos)");
    }

    System.out.println("----------------------------------------");
    System.out.println("PUNTOS OBTENIDOS: " + puntos + " / 4.0");
    System.out.println("========================================");

    assertTrue(puntos >= 0 && puntos <= 4);
  }

  private boolean evalProductoBasico() {
    try {
      Producto p = new Producto("X01", "Prueba", 10.0);

      boolean gettersOk
        = "X01".equals(p.getCodigo())
        && "Prueba".equals(p.getNombre())
        && approx(p.getPrecioBase(), 10.0);

      boolean disponibleInitOk = p.isDisponible();

      boolean desactivarOk = p.desactivar() && !p.isDisponible();
      boolean desactivar2Ok = !p.desactivar() && !p.isDisponible();

      boolean activarOk = p.activar() && p.isDisponible();
      boolean activar2Ok = !p.activar() && p.isDisponible();

      boolean precioVentaOk = approx(p.getPrecioVenta(), 10.0);

      return gettersOk && disponibleInitOk
        && desactivarOk && desactivar2Ok
        && activarOk && activar2Ok
        && precioVentaOk;
    } catch (Throwable t) {
      return false;
    }
  }

  private boolean evalProductosEspecializados() {
    try {
      ProductoUnidad u = new ProductoUnidad("U01", "Cuaderno", 2.5, 5);

      boolean stockGetterOk = (u.getStock() == 5);
      boolean hayStockOk
        = u.hayStock(1)
        && u.hayStock(5)
        && !u.hayStock(6)
        && !u.hayStock(0)
        && !u.hayStock(-1);

      boolean descontarOk = u.descontarStock(3) && (u.getStock() == 2);
      boolean noDescontarOk = !u.descontarStock(5) && (u.getStock() == 2);

      ProductoPeso w = new ProductoPeso("P01", "Manzanas", 0.0, 1.8, 5.0);

      boolean precioVentaOk = approx(w.getPrecioVenta(), 1.8);
      boolean gettersOk
        = approx(w.getPrecioPorKg(), 1.8)
        && approx(w.getMaxKgPorCompra(), 5.0);

      boolean pesoValidoOk
        = w.pesoValido(0.1)
        && w.pesoValido(5.0)
        && !w.pesoValido(0.0)
        && !w.pesoValido(-1.0)
        && !w.pesoValido(5.01);

      return stockGetterOk && hayStockOk
        && descontarOk && noDescontarOk
        && precioVentaOk && gettersOk
        && pesoValidoOk;
    } catch (Throwable t) {
      return false;
    }
  }

  private boolean evalCarritoTotales() {
    try {
      Carrito c = new Carrito();
      ProductoUnidad u = new ProductoUnidad("U10", "Bolígrafo", 1.2, 100);
      ProductoPeso w = new ProductoPeso("P10", "Naranjas", 0.0, 2.0, 5.0);

      boolean rechazaNull = !c.agregar(null, 1);
      boolean rechazaCant0 = !c.agregar(u, 0);
      boolean rechazaCantNeg = !c.agregar(u, -1);
      boolean rechazaDecimalUnidad = !c.agregar(u, 1.5);

      boolean add1 = c.agregar(u, 2);
      boolean add2 = c.agregar(w, 1.5);

      boolean subtotalOk = approx(c.calcularSubtotal(), 5.4);
      boolean descOk = approx(c.calcularDescuento(), 0.0);
      boolean totalOk = approx(c.calcularTotal(), 5.4);

      Carrito c2 = new Carrito();
      ProductoUnidad caro = new ProductoUnidad("U99", "Teclado", 25.0, 10);
      c2.agregar(caro, 2);

      boolean desc2Ok = approx(c2.calcularDescuento(), 2.5);
      boolean total2Ok = approx(c2.calcularTotal(), 47.5);

      return rechazaNull && rechazaCant0 && rechazaCantNeg
        && rechazaDecimalUnidad
        && add1 && add2
        && subtotalOk && descOk && totalOk
        && desc2Ok && total2Ok;
    } catch (Throwable t) {
      return false;
    }
  }

  private boolean evalTiendaComprar() {
    try {
      Tienda t = new Tienda("DAM");

      ProductoUnidad u = new ProductoUnidad("U01", "Cuaderno", 2.5, 2);
      ProductoPeso w = new ProductoPeso("P01", "Manzanas", 0.0, 1.8, 5.0);

      boolean addU = t.agregarProducto(u);
      boolean addW = t.agregarProducto(w);

      ProductoUnidad uDup = new ProductoUnidad("U01", "Otro", 9.9, 99);
      boolean noDup = !t.agregarProducto(uDup);

      Carrito c = new Carrito();

      boolean noExiste = !t.comprar(c, "ZZZ", 1);
      boolean compra1 = t.comprar(c, "U01", 2);
      boolean stock0 = (u.getStock() == 0);
      boolean compraSinStock = !t.comprar(c, "U01", 1);
      boolean compraDecimal = !t.comprar(c, "U01", 1.5);
      boolean compraPesoOk = t.comprar(c, "P01", 1.0);
      boolean compraPesoNoOk = !t.comprar(c, "P01", 0.0) && !t.comprar(c, "P01", 6.0);

      return addU && addW && noDup && noExiste
        && compra1 && stock0
        && compraSinStock && compraDecimal
        && compraPesoOk && compraPesoNoOk;
    } catch (Throwable t) {
      return false;
    }
  }

  private boolean approx(double a, double b) {
    return Math.abs(a - b) <= EPS;
  }
}
