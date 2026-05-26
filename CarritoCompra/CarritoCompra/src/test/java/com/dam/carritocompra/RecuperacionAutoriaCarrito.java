package com.dam.carritocompra;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RecuperacionAutoriaCarrito {

    private static final double EPS = 1e-6;

    @Test
    void prueba_autoria_recuperacion() {

        double puntos = 0.0;

        System.out.println("========================================");
        System.out.println("PRUEBA DE AUTORÍA DE RECUPERACIÓN");
        System.out.println("CARRITO DE LA COMPRA");
        System.out.println("========================================");

        double p1 = evalApartado1_vaciarProducto();
        puntos += p1;
        System.out.println("Apartado 1 (vaciarProducto) ............ " + formatoResultado(p1, 1));

        double p2 = evalApartado2_contieneProducto();
        puntos += p2;
        System.out.println("Apartado 2 (contieneProducto) .......... " + formatoResultado(p2, 1));

        double p3 = evalApartado3_activarProducto();
        puntos += p3;
        System.out.println("Apartado 3 (activarProducto) ........... " + formatoResultado(p3, 2));

        double p4 = evalApartado4_totalesPorTipo();
        puntos += p4;
        System.out.println("Apartado 4 (totales por tipo) .......... " + formatoResultado(p4, 2));

        System.out.println("----------------------------------------");
        System.out.println("PUNTOS OBTENIDOS: " + quitarCeroSiEntero(puntos) + " / 6");
        System.out.println("========================================");

        assertTrue(puntos >= 0.0 && puntos <= 6.0);
    }

    // APARTADO 1 (1 punto)
    // public boolean vaciarProducto(String codigo)
    private double evalApartado1_vaciarProducto() {
        try {
            Method metodo = Carrito.class.getMethod("vaciarProducto", String.class);

            Carrito c = new Carrito();

            ProductoUnidad u1 = new ProductoUnidad("U01", "Cuaderno", 2.5, 100);
            ProductoUnidad u2 = new ProductoUnidad("U02", "Boligrafo", 1.5, 100);
            ProductoPeso p1 = new ProductoPeso("P01", "Manzanas", 0.0, 2.0, 5.0);

            boolean add1 = c.agregar(u1, 2);
            boolean add2 = c.agregar(p1, 1.5);
            boolean add3 = c.agregar(u2, 3);

            if (!add1 || !add2 || !add3) return 0.0;
            if (c.getNumLineas() != 3) return 0.0;

            boolean nullOk = !(boolean) metodo.invoke(c, (Object) null);
            boolean noExisteOk = !(boolean) metodo.invoke(c, "XXX");
            boolean vaciaOk = (boolean) metodo.invoke(c, "P01");

            if (!nullOk || !noExisteOk || !vaciaOk) return 0.0;
            if (c.getNumLineas() != 2) return 0.0;
            if (c.buscarLinea("P01") != null) return 0.0;
            if (c.buscarLinea("U01") == null) return 0.0;
            if (c.buscarLinea("U02") == null) return 0.0;

            return 1.0;

        } catch (NoSuchMethodException e) {
            return 0.0;
        } catch (Throwable t) {
            return 0.0;
        }
    }

    // APARTADO 2 (1 punto)
    // public boolean contieneProducto(String codigo)
    private double evalApartado2_contieneProducto() {
        try {
            Method metodo = Carrito.class.getMethod("contieneProducto", String.class);

            Carrito c = new Carrito();

            boolean nullEnVacio = !(boolean) metodo.invoke(c, (Object) null);
            boolean noExisteEnVacio = !(boolean) metodo.invoke(c, "U01");

            if (!nullEnVacio || !noExisteEnVacio) return 0.0;

            ProductoUnidad u1 = new ProductoUnidad("U01", "Cuaderno", 2.5, 100);
            ProductoPeso p1 = new ProductoPeso("P01", "Peras", 0.0, 3.0, 5.0);

            c.agregar(u1, 2);
            c.agregar(p1, 1.0);

            boolean contieneU01 = (boolean) metodo.invoke(c, "U01");
            boolean contieneP01 = (boolean) metodo.invoke(c, "P01");
            boolean noContieneU99 = !(boolean) metodo.invoke(c, "U99");
            boolean nullOk = !(boolean) metodo.invoke(c, (Object) null);

            return (contieneU01 && contieneP01 && noContieneU99 && nullOk) ? 1.0 : 0.0;

        } catch (NoSuchMethodException e) {
            return 0.0;
        } catch (Throwable t) {
            return 0.0;
        }
    }

    // APARTADO 3 (2 puntos)
    // public boolean activarProducto(String codigo)
    // puntuación parcial:
    // 1 punto por activar correctamente
    // 1 punto por volver a permitir la compra
    private double evalApartado3_activarProducto() {
        double puntos = 0.0;

        try {
            Method metodo = Tienda.class.getMethod("activarProducto", String.class);

            Tienda t = new Tienda("DAM");
            Carrito c = new Carrito();

            ProductoUnidad u1 = new ProductoUnidad("U01", "Cuaderno", 2.5, 10);
            ProductoPeso p1 = new ProductoPeso("P01", "Naranjas", 0.0, 2.0, 5.0);

            if (!t.agregarProducto(u1)) return 0.0;
            if (!t.agregarProducto(p1)) return 0.0;

            boolean nullOk = !(boolean) metodo.invoke(t, (Object) null);
            boolean noExisteOk = !(boolean) metodo.invoke(t, "XXX");
            boolean yaActivo = !(boolean) metodo.invoke(t, "U01");

            if (!nullOk || !noExisteOk || !yaActivo) return 0.0;

            boolean desactivarOk = u1.desactivar();
            if (!desactivarOk) return 0.0;
            if (u1.isDisponible()) return 0.0;

            boolean compraDesactivado = t.comprar(c, "U01", 1);
            if (compraDesactivado) return 0.0;

            boolean activarOk = (boolean) metodo.invoke(t, "U01");
            boolean activarOtraVez = !(boolean) metodo.invoke(t, "U01");

            if (activarOk && activarOtraVez && u1.isDisponible()) {
                puntos += 1.0;
            }

            boolean compraActivado = t.comprar(c, "U01", 2);
            boolean stockOk = (u1.getStock() == 8);

            if (compraActivado && stockOk) {
                puntos += 1.0;
            }

            return puntos;

        } catch (NoSuchMethodException e) {
            return 0.0;
        } catch (Throwable t) {
            return 0.0;
        }
    }

    // APARTADO 4 (2 puntos)
    // public double totalProductosUnidad() -> 1 punto
    // public double totalProductosPeso()   -> 1 punto
    private double evalApartado4_totalesPorTipo() {
        double puntos = 0.0;

        Method metodoUnidad = null;
        Method metodoPeso = null;

        try {
            metodoUnidad = Carrito.class.getMethod("totalProductosUnidad");
        } catch (Throwable t) {
            metodoUnidad = null;
        }

        try {
            metodoPeso = Carrito.class.getMethod("totalProductosPeso");
        } catch (Throwable t) {
            metodoPeso = null;
        }

        Carrito c = new Carrito();

        ProductoUnidad u1 = new ProductoUnidad("U01", "Cuaderno", 2.5, 100);
        ProductoUnidad u2 = new ProductoUnidad("U02", "Boligrafo", 1.0, 100);
        ProductoPeso p1 = new ProductoPeso("P01", "Manzanas", 0.0, 2.0, 5.0);
        ProductoPeso p2 = new ProductoPeso("P02", "Peras", 0.0, 3.0, 5.0);

        c.agregar(u1, 2);     // 5.0
        c.agregar(p1, 1.5);   // 3.0
        c.agregar(u2, 3);     // 3.0
        c.agregar(p2, 2.0);   // 6.0

        // Método totalProductosUnidad -> 1 punto
        if (metodoUnidad != null) {
            try {
                double totalUnidad = (double) metodoUnidad.invoke(c);
                c.eliminar("P01");
                double totalUnidad2 = (double) metodoUnidad.invoke(c);

                if (approx(totalUnidad, 8.0) && approx(totalUnidad2, 8.0)) {
                    puntos += 1.0;
                }
            } catch (Throwable t) {
                // 0 puntos en esta mitad
            }
        }

        // Reconstruimos carrito para no depender del estado anterior
        c = new Carrito();
        c.agregar(u1, 2);     // 5.0
        c.agregar(p1, 1.5);   // 3.0
        c.agregar(u2, 3);     // 3.0
        c.agregar(p2, 2.0);   // 6.0

        // Método totalProductosPeso -> 1 punto
        if (metodoPeso != null) {
            try {
                double totalPeso = (double) metodoPeso.invoke(c);
                c.eliminar("P01");
                double totalPeso2 = (double) metodoPeso.invoke(c);

                if (approx(totalPeso, 9.0) && approx(totalPeso2, 6.0)) {
                    puntos += 1.0;
                }
            } catch (Throwable t) {
                // 0 puntos en esta mitad
            }
        }

        return puntos;
    }

    private boolean approx(double a, double b) {
        return Math.abs(a - b) <= EPS;
    }

    private String formatoResultado(double obtenidos, double maximos) {
        if (approx(obtenidos, 0.0)) {
            return "ERROR (0 puntos)";
        }
        if (approx(obtenidos, maximos)) {
            return "OK (" + quitarCeroSiEntero(maximos) + (maximos == 1.0 ? " punto)" : " puntos)");
        }
        return "PARCIAL (" + quitarCeroSiEntero(obtenidos) + " de " + quitarCeroSiEntero(maximos) + " puntos)";
    }

    private String quitarCeroSiEntero(double valor) {
        if (valor == (int) valor) {
            return String.valueOf((int) valor);
        }
        return String.valueOf(valor);
    }
}