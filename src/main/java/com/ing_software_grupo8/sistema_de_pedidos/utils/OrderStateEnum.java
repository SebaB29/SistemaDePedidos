package com.ing_software_grupo8.sistema_de_pedidos.utils;

public enum OrderStateEnum {

    CREADO,
    PROCESO,
    ENVIADO;

    @Override
    public String toString() {
        switch (this) {
            case CREADO:
                return "Creado";
            case PROCESO:
                return "En proceso";
            case ENVIADO:
                return "Enviado";
            default:
                return "";
        }
    }
}
