package com.example.proyecto.Model;

public class User {

    public Boolean ok;
    public String msg;

    public String _id;
    public String nombre;
    public String apellido;
    public String correo;
    public String nacionalidad;
    public String numero;
    public String contra;
    public String confcontra;

    public String get_id() {return _id;}


    public void set_id(String _id) {this._id = _id;}
    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public String getNumero() {
        return numero;
    }

    public String getContra() {
        return contra;
    }

    public String getConfcontra() {
        return confcontra;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setNacionalida(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public void setConfcontra(String confcontra) {
        this.confcontra = confcontra;
    }
}
