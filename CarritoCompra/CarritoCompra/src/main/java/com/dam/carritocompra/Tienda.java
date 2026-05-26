package com.dam.carritocompra;

public class Tienda {
    private final int MAX_PRODUCTOS = 50;
    protected String nombre;
    protected Producto[] catalogo;
    protected int numProductos;

    public Tienda(String nombre) {
        this.nombre = nombre;
        this.catalogo = new Producto[MAX_PRODUCTOS];
        this.numProductos = 0;
    }

    public boolean agregarProducto(Producto p) {
        boolean seAgrego = false;
        if (p != null && numProductos < MAX_PRODUCTOS && buscarProducto(p.codigo) == null) {
            catalogo[numProductos++] = p;
            seAgrego = true;
        }
        return seAgrego;
    }

    public Producto buscarProducto(String codigo) {
        for (Producto p : catalogo) {
            if(p != null) {
            if (p.getCodigo().equals(codigo)) {
                return p;
            }
        }
        }
        return null;
    }

    public void mostrarCatalogo() {
        for (int i = 0; i < numProductos; i++) {
            System.out.println("Producto " + (i + 1));
            System.out.println("--------------------");
            catalogo[i].mostrar();
            System.out.println("....................");
        }
    }

    public boolean comprar(Carrito c, String codigo, double cantidad) {
        //el producto debe existir y estar disponible.
        //si es ProductoUnidad, debe haber stock suficiente antes de añadirlo al carrito y se descuenta tras la compra.
        //si es ProductoPeso, se valida el peso permitido.
        //si todo es correcto devuelve true.
        boolean seCompra = false;
        if (c != null && codigo != null && cantidad > 0) {
    
        Producto pd = this.buscarProducto(codigo);
        if (pd != null && pd.isDisponible()) {
            if(pd instanceof ProductoUnidad){
                if (((ProductoUnidad) pd).hayStock((int) cantidad)) {
                    if(c.agregar(pd, cantidad)){
                    c.calcularTotal();
                    ((ProductoUnidad) buscarProducto(codigo)).stock -= cantidad;
                    seCompra = true;
                    }
                }
            }
            else if (pd instanceof ProductoPeso){
                if (((ProductoPeso) pd).pesoValido(cantidad)) {
                    if(c.agregar(pd, cantidad)){
                    seCompra = true;
                    }
                }
                
            }
        }
      }
        return seCompra;
    }
    
    //Estudio Recuperación autoria 3
    public boolean activarProducto(String codigo){
        boolean seActiva = false;
        if (codigo != null && !codigo.isBlank() && !codigo.equals("")) {
            for (int i = 0; i < numProductos && !seActiva; i++) {
                if (catalogo[i] != null) {
                    if (catalogo[i].getCodigo().equals(codigo)) {
                        seActiva = catalogo[i].activar();
                    }
                }
            }
        }
        return seActiva;
    }
}
