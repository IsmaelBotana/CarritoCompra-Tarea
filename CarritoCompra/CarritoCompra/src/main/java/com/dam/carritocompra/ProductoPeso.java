package com.dam.carritocompra;

public class ProductoPeso extends Producto {

    protected double precioPorKg;
    protected double maxKgPorCompra;

    public ProductoPeso(String codigo, String nombre, double precioBase, double precioPorKg, double maxKgPorCompra) {
        super(codigo, nombre, precioBase);
        this.precioPorKg = precioPorKg;
        this.maxKgPorCompra = maxKgPorCompra;
    }

    public double getPrecioPorKg() {
        return this.precioPorKg;
    }

    public double getMaxKgPorCompra() {
        return this.maxKgPorCompra;
    }

    @Override
    public double getPrecioVenta() {
        return getPrecioPorKg();
    }

    public boolean pesoValido(double kg) {
        return kg > 0 && kg <= maxKgPorCompra;
    }

    @Override
    public void mostrar() {
        super.mostrar();
        System.out.println("Precio por kg:    " + getPrecioPorKg());
        System.out.println("Máximo permitido: " + getMaxKgPorCompra());
    }
}

