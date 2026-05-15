package com.dam.carritocompra;

public class LineaCarrito {

    protected Producto producto;
    protected double cantidad;

    public LineaCarrito(Producto producto, double cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return this.producto;
    }

    public double getCantidad() {
        return this.cantidad;
    }

    public double getSubtotal() {
        return (this.producto.getPrecioVenta() * this.cantidad);
    }

    public void mostrar() {
        //código, nombre, cantidad, precio de venta y subtotal.
        System.out.println("Código: " + getProducto().getCodigo());
        System.out.println("Nombre: " + getProducto().getNombre());
        System.out.println("Cantidad: " + getCantidad());
        System.out.println("Precio de venta: " + getProducto().getPrecioVenta());
        System.out.println("Código: " + getSubtotal());

    }
}
