
package com.dam.carritocompra;

public class ProductoUnidad extends Producto {

    protected int stock;

    public ProductoUnidad(String codigo, String nombre, double precioBase, int stock) {
        super(codigo, nombre, precioBase);
        this.stock = stock;
    }

    public int getStock() {
        return this.stock;
    }

    public boolean hayStock(int unidades) {
        return stock >= unidades && unidades > 0;
    }

    public boolean descontarStock(int unidades) {
        boolean descuentaStock = false;
        if(hayStock(unidades)){
            this.stock -= unidades;
            descuentaStock = true;
        }
        return descuentaStock;
    }

    @Override
    public void mostrar() {
        super.mostrar();
        System.out.println("Stock:            " + getStock());
    }
}

