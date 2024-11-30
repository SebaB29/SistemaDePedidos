# 🛒 Sistema de Gestión de Pedidos

<div text-align="justify">
Un sistema diseñado para gestionar el registro, autenticación y creación de pedidos de productos, con funcionalidades tanto para usuarios como administradores. Este proyecto implementa un sistema flexible para el manejo de atributos dinámicos de productos y reglas personalizadas para la gestión de pedidos.
</div>

## 📜 Tabla de Contenidos

- [Características](#características)
- [Tecnologías](#tecnologías)
- [Contribuir](#contribuir)
- [Licencia](#licencia)
- [Sobre Este Proyecto](#sobre-este-proyecto)

## ✨ Características <a name="características"></a>

### Usuarios
- Registro y autenticación con validación de email.
- Gestión de perfil (nombre, apellido, email, foto, edad, género, domicilio).
- Recuperación de contraseña.
- Creación de pedidos con selección de atributos dinámicos y validación de reglas.
- Cancelación de pedidos dentro de las primeras 24 horas (si no están en estado "EN PROCESO").
- Visualización del historial de pedidos realizados.

### Administradores
- Gestión de productos y atributos dinámicos.
- Control y actualización de stock.
- Visualización y gestión de pedidos confirmados.
- Actualización del estado de pedidos a "EN PROCESO" o "ENVIADO".

## 🛠 Tecnologías <a name="tecnologías"></a>

El proyecto utiliza las siguientes tecnologías y herramientas:

- **Lenguaje de programación**: Java
- **Gestión de proyectos**: JIRA
- **Sistema de control de versiones**: GitLab
- **Pruebas unitarias**: Incluidas con JUnit
- **Biblioteca para simulaciones y pruebas de comportamiento**: Mockito

## 🤝 Contribuir <a name="contribuir"></a>

¡Contribuciones son bienvenidas! Si deseas mejorar este proyecto, sigue estos pasos para contribuir:

1. Haz un fork del repositorio.
2. Crea una rama para tu funcionalidad (`git checkout -b feature/TuFuncionalidad`).
3. Realiza tus cambios y crea commits (`git commit -m 'Agrega una nueva funcionalidad'`).
4. Haz un push a la rama (`git push origin feature/TuFuncionalidad`).
5. Abre un pull request.

## 📄 Licencia <a name="licencia"></a>

Distribuido bajo la licencia **MIT**. Consulta el archivo `LICENSE` para más detalles.

## 📚 Sobre Este Proyecto <a name="sobre-este-proyecto"></a>

<div text-align="justify">
Este proyecto fue desarrollado como un trabajo práctico universitario. El objetivo es implementar un sistema completo de gestión de pedidos, aplicando principios de diseño orientado a objetos y buenas prácticas de programación.
</div>
