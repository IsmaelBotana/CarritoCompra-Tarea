package com.dam.carritocompra;

public class Producto {

    protected String codigo;
    protected String nombre;
    protected double precioBase;
    protected boolean disponible;

    public Producto(String codigo, String nombre, double precioBase) {
      this.codigo = codigo;
      this.nombre = nombre;
      this.precioBase = precioBase;
      this.disponible = true;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public String getNombre() {
        return this.nombre;
    }

    public double getPrecioBase() {
        return this.precioBase;
    }

    public boolean isDisponible() {
        return this.disponible;
    }

    public boolean desactivar() {
      boolean desactivado = false;
      if(isDisponible()){
        this.disponible = false;
        desactivado = true;
      }
        return desactivado;
    }

    public boolean activar() {
      boolean activado = false;
      if(!isDisponible()){
        this.disponible = true;
        activado = true;
      }
        return activado;
    }

    public double getPrecioVenta() {
        return getPrecioBase();
    }

    public void mostrar() {
      System.out.println("Código:           " + getCodigo());
      System.out.println("Nombre:           " + getNombre());
      System.out.println("Precio base:      " + getPrecioBase());
      System.out.println("Estado:           ");
      if(!isDisponible()){
        System.out.print("No ");
      }
      System.out.println("Disponible");
    }
}
