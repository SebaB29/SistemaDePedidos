package com.ing_software_grupo8.sistema_de_pedidos.utils;

public enum OrderStateEnum {

    CONFIRMADO,
    PROCESO,
    ENVIADO,
    CANCELADO;

    @Override
    public String toString() {
        return switch (this) {
            case CONFIRMADO -> "Confirmado";
            case PROCESO -> "En proceso";
            case ENVIADO -> "Enviado";
            case CANCELADO -> "Cancelado";
        };
    }
}