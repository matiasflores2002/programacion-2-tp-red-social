# Red Social Profesional

**Trabajo Práctico Obligatorio — Programación 2**
Alternativa A: Ecosistema de Red Social Profesional

---

## Descripción general

Sistema que simula una red social profesional similar a LinkedIn. Permite registrar usuarios con perfiles, conectarlos entre sí, recomendar nuevos contactos, gestionar postulaciones laborales, deshacer cambios de perfil y organizar habilidades de forma jerárquica.

Cada funcionalidad fue implementada eligiendo la estructura de datos más adecuada para el problema, justificando la decisión en términos de eficiencia y claridad.

---

## Estructuras de datos utilizadas

| Estructura | Clase | Justificación |
|---|---|---|
| HashMap | `RedSocial` | Búsqueda de usuarios por ID en O(1) promedio |
| Grafo (lista de adyacencia) | `Grafo` | Representa conexiones entre usuarios de forma eficiente |
| BFS | `BuscadorRutas` | Calcula el camino mínimo entre dos nodos del grafo |
| Cola FIFO | `Cola<T>`, `GestorPostulaciones` | Procesa postulaciones en orden de llegada |
| Pila LIFO | `Pila<T>`, `Usuario` | Permite deshacer cambios de perfil en orden inverso |
| Árbol | `ArbolHabilidades`, `NodoHabilidad` | Organiza habilidades en categorías jerárquicas |

---

## Estructura del proyecto

```
src/
  modelo/
    Usuario.java          — entidad usuario con historial de perfil
    Perfil.java           — datos del perfil (nombre, email, titular)
    Postulacion.java      — datos de una postulación laboral
    NodoHabilidad.java    — nodo del árbol de habilidades
  tdas/
    Cola.java             — TDA Cola FIFO genérica
    Pila.java             — TDA Pila LIFO genérica
    Grafo.java            — grafo no dirigido con lista de adyacencia
  servicio/
    RedSocial.java        — registro y búsqueda de usuarios (HashMap)
    BuscadorRutas.java    — grado de separación entre usuarios (BFS)
    Recomendador.java     — sugerencias por vínculos comunes
    GestorPostulaciones.java — cola de postulaciones
    ArbolHabilidades.java — árbol jerárquico de habilidades
  main/
    Main.java             — demostración de todas las funcionalidades
Documentacion/
  casos-prueba.md
  propuesta-diseno.md
```

---

## Cómo compilar y ejecutar

Desde la raíz del proyecto, con Java 17 o superior:

**Compilar:**
```bash
javac -d out src/modelo/*.java src/tdas/*.java src/servicio/*.java src/main/*.java
```

**Ejecutar:**
```bash
java -cp out main.Main
```

---

## Funcionalidades implementadas

- Registrar usuarios con perfil (nombre, email, titular)
- Buscar usuarios por ID en tiempo O(1)
- Validar IDs duplicados al registrar
- Conectar usuarios en un grafo no dirigido
- Evitar conexiones duplicadas y auto-conexiones
- Ver contactos directos de un usuario
- Calcular grado de separación entre dos usuarios (BFS)
- Recomendar contactos por vínculos comunes, ordenados por relevancia
- Encolar y procesar postulaciones laborales en orden FIFO
- Actualizar el perfil de un usuario guardando el estado anterior
- Deshacer la última actualización de perfil (LIFO)
- Organizar habilidades en una jerarquía de árbol
- Buscar habilidades por nombre dentro del árbol
- Mostrar la jerarquía de habilidades con indentación
