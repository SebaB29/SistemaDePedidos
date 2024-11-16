package com.ing_software_grupo8.sistema_de_pedidos.service;

import jakarta.servlet.http.HttpServletRequest;

public interface IBasicService {

    String getEmailFromToken(HttpServletRequest request);

    String getPasswordFromRequest(HttpServletRequest request);
}
