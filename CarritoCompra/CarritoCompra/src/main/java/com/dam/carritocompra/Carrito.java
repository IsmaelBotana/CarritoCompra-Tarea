package com.dam.carritocompra;

public class Carrito {

    private final int MAX_LINEA_CARRITO = 20;
    protected LineaCarrito[] lineas;
    protected int numLineas;

    public Carrito() {
        this.lineas = new LineaCarrito[MAX_LINEA_CARRITO];
        this.numLineas = 0;
    }

    public int getNumLineas() {
        return this.numLineas;
    }

    public boolean estaVacio() {
        return numLineas == 0;
    }

    public LineaCarrito buscarLinea(String codigo) {
        LineaCarrito ln = null;
        for (LineaCarrito linea : lineas) {
            if (linea != null) {
                if (linea.getProducto().getCodigo().equals(codigo)) {
                    ln = linea;
                }
            }
        }
        return ln;
    }

    public boolean agregar(Producto p, double cantidad) {
        boolean esAgregado = false;
        if (p != null && p.isDisponible() && cantidad > 0) {
            if (p instanceof ProductoUnidad) {
                if (cantidad == (int) cantidad) {
                    for (LineaCarrito l : lineas) {
                        if (l != null) {
                            if (l.getProducto().equals(p)) {
                                l.cantidad = (l.getCantidad() + cantidad);
                                esAgregado = true;
                            }
                        }
                    }

                    if (!esAgregado) {
                        if (numLineas < MAX_LINEA_CARRITO) {
                            numLineas++;
                            lineas[numLineas - 1] = new LineaCarrito(p, cantidad);
                            esAgregado = true;
                        }
                    }
                }
            } else if (p instanceof ProductoPeso) {
                for (LineaCarrito l : lineas) {
                    if (l != null) {
                        if (l.getProducto().equals(p) && ((ProductoPeso) p).pesoValido(l.getCantidad() + cantidad)) {
                            l.cantidad = (l.getCantidad() + cantidad);
                            esAgregado = true;
                        }
                    }
                }

                if (!esAgregado) {
                    if (numLineas < MAX_LINEA_CARRITO && ((ProductoPeso) p).pesoValido(cantidad)) {
                        numLineas++;
                        lineas[numLineas - 1] = new LineaCarrito(p, cantidad);
                        esAgregado = true;
                    }
                }
            }

        }

        return esAgregado;
    }

    public boolean eliminar(String codigo) {
        boolean seElimino = false;
        if (codigo != null) {
            for (int i = 0; i < numLineas && !seElimino; i++) {
                if (lineas[i].getProducto().getCodigo().equals(codigo)) {
                    lineas[i] = lineas[numLineas - 1];
                    lineas[numLineas - 1] = null;
                    numLineas--;
                    seElimino = true;
                }
            }
        }
        return seElimino;
    }

    public void vaciar() {
        while (numLineas > 0) {
            lineas[numLineas - 1] = null;
            numLineas--;
        }
    }

    public double calcularSubtotal() {
        double subTotal = 0;
        for (int i = 0; i < numLineas; i++) {
            subTotal += lineas[i].getSubtotal();
        }
        return subTotal;
    }

    public double calcularDescuento() {
        double descuento = 0;
        if (calcularSubtotal() >= 50) {
            descuento = this.calcularSubtotal() * 0.05;
        }
        return descuento;
    }

    public double calcularTotal() {
        return (calcularSubtotal() - calcularDescuento());
    }

    public void mostrarTicket() {
        System.out.println("Lineas de carrito: " + this.numLineas);
        System.out.println("Sub. Total:        " + calcularSubtotal());
        System.out.println("Descuento:         " + calcularDescuento());
        System.out.println("Total:             " + calcularTotal());
    }

    //Estudio Recuperación autoria 1
    public boolean vaciarProducto(String codigo) {
        boolean seVacia = false;
        if (codigo != null) {
            for (int i = 0; i < numLineas && !seVacia; i++) {
                if (lineas[i].getProducto().getCodigo().equals(codigo)) {
                    for (int j = i; j < numLineas - 1; j++) {
                        lineas[j] = lineas[j + 1];
                    }
                    lineas[numLineas - 1] = null;
                    numLineas--;
                    seVacia = true;
                }
            }
        }
        return seVacia;
    }

    //Estudio Recuperación autoria 2
    public boolean contieneProducto(String codigo) {
        boolean loContiene = false;
        if (codigo != null && !codigo.isBlank() && !codigo.equals("")) {
            for (int i = 0; i < numLineas && !loContiene; i++) {
                if (lineas[i] != null) {
                    if (lineas[i].getProducto().getCodigo().equals(codigo)) {
                        loContiene = true;
                    }
                }
            }
        }
        return loContiene;
    }

    //Estudio Recuperación autoria 4.1
    public double totalProductosUnidad() {
        double subTotalUnidad = 0;
        if (!estaVacio()) {
            for (int i = 0; i < numLineas; i++) {
                if (lineas[i] != null) {
                    if (lineas[i].getProducto() instanceof ProductoUnidad) {
                        subTotalUnidad += lineas[i].getSubtotal();
                    }
                }
            }
        }
        return subTotalUnidad;
    }
    
    //Estudio Recuperación autoria 4.2
    public double totalProductosPeso() {
        double subTotalPeso = 0;
        if (!estaVacio()) {
            for (int i = 0; i < numLineas; i++) {
                if (lineas[i] != null) {
                    if (lineas[i].getProducto() instanceof ProductoPeso) {
                        subTotalPeso += lineas[i].getSubtotal();
                    }
                }
            }
        }
        return subTotalPeso;
    }

}
