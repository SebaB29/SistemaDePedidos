package com.ing_software_grupo8.sistema_de_pedidos.utils;

public enum OrderStateEnum {

    CONFIRMADO,
    PROCESO,
    ENVIADO,
    CANCELADO;

    @Override
    public String toString() {
        switch (this) {
            case CONFIRMADO:
                return "Confirmado";
            case PROCESO:
                return "En proceso";
            case ENVIADO:
                return "Enviado";
            case CANCELADO:
                return "Cancelado";
            default:
                return "";
        }
    }
}
