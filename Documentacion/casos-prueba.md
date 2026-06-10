# Casos de prueba

---

## 1. Registro y búsqueda de usuarios (HashMap)

| # | Descripción | Entrada | Resultado esperado |
|---|---|---|---|
| 1.1 | Registrar un usuario válido | `new Usuario("u1", new Perfil(...))` | Usuario registrado sin error |
| 1.2 | Registrar dos usuarios distintos | IDs `"u1"` y `"u2"` | Ambos registrados; `cantidadUsuarios() == 2` |
| 1.3 | Registrar ID duplicado | Dos usuarios con ID `"u1"` | Lanza `IllegalStateException` |
| 1.4 | Buscar usuario existente | `buscarPorId("u1")` | Devuelve el usuario correspondiente |
| 1.5 | Buscar usuario inexistente | `buscarPorId("u99")` | Devuelve `null` |
| 1.6 | Registrar usuario con ID vacío | `new Usuario("", perfil)` | Lanza `IllegalArgumentException` |
| 1.7 | Registrar usuario con perfil nulo | `new Usuario("u1", null)` | Lanza `IllegalArgumentException` |

---

## 2. Conexiones del grafo

| # | Descripción | Entrada | Resultado esperado |
|---|---|---|---|
| 2.1 | Conectar dos usuarios válidos | `conectar("u1", "u2")` | Ambos aparecen en los contactos del otro |
| 2.2 | Conectar en ambas direcciones | `conectar("u1", "u2")` | `contactos("u2")` contiene `"u1"` |
| 2.3 | Evitar conexión duplicada | `conectar("u1","u2")` dos veces | `contactos("u1")` contiene `"u2"` una sola vez |
| 2.4 | Evitar auto-conexión | `conectar("u1", "u1")` | Lanza `IllegalArgumentException` |
| 2.5 | Conectar usuario no registrado | `conectar("u1", "uX")` | Lanza `IllegalStateException` |
| 2.6 | Ver contactos de usuario sin conexiones | `obtenerContactos("u4")` | Devuelve conjunto vacío |

---

## 3. Grado de separación (BFS)

| # | Descripción | Red | Resultado esperado |
|---|---|---|---|
| 3.1 | Mismo usuario | `gradoSeparacion("u1","u1")` | `0` |
| 3.2 | Conexión directa | u1—u2 | `gradoSeparacion("u1","u2") == 1` |
| 3.3 | Dos saltos | u1—u2—u3 | `gradoSeparacion("u1","u3") == 2` |
| 3.4 | Sin camino | u1—u2 y u3 aislado | `gradoSeparacion("u1","u3") == -1` |
| 3.5 | Camino más corto entre varios | u1—u2—u3 y u1—u3 | `gradoSeparacion("u1","u3") == 1` |
| 3.6 | Usuario no existe en el grafo | `gradoSeparacion("u1","uX")` | Lanza `IllegalArgumentException` |

---

## 4. Recomendador de contactos

| # | Descripción | Red | Resultado esperado |
|---|---|---|---|
| 4.1 | Recomendar por vínculo común | u1—u2—u3 | u3 aparece en recomendaciones de u1 |
| 4.2 | No recomendar contacto directo | u1—u2 | u2 no aparece en recomendaciones de u1 |
| 4.3 | No recomendarse a sí mismo | u1—u2—u1 | u1 no aparece en sus propias recomendaciones |
| 4.4 | Ordenar por más vínculos comunes | u3 comparte 2, u5 comparte 1 | u3 aparece antes que u5 |
| 4.5 | Sin recomendaciones posibles | u1 sin conexiones | Lista vacía |
| 4.6 | Usuario no existe | `recomendar("uX")` | Lanza `IllegalArgumentException` |

---

## 5. Postulaciones FIFO

| # | Descripción | Acción | Resultado esperado |
|---|---|---|---|
| 5.1 | Encolar una postulación | `encolar(p1)` | `cantidadPendientes() == 1` |
| 5.2 | Encolar varias y procesar | Encolar p1, p2, p3; procesar | Se procesa p1 primero (orden de llegada) |
| 5.3 | Procesar cola vacía | `procesarSiguiente()` sin postulaciones | Devuelve `null` y muestra mensaje |
| 5.4 | Mostrar pendientes | Encolar 3; mostrar | Se listan las 3 en orden de llegada |
| 5.5 | Encolar postulación nula | `encolar(null)` | Lanza `IllegalArgumentException` |
| 5.6 | Postulación con puesto vacío | `new Postulacion("u1", "")` | Lanza `IllegalArgumentException` |

---

## 6. Historial de perfil (Pila LIFO)

| # | Descripción | Acción | Resultado esperado |
|---|---|---|---|
| 6.1 | Actualizar perfil una vez | `actualizarPerfil(nuevo)` | `getPerfil()` devuelve el nuevo perfil |
| 6.2 | Deshacer único cambio | `actualizarPerfil` + `deshacerCambio` | Se restaura el perfil original |
| 6.3 | Deshacer en orden inverso | Tres cambios; deshacer | Se restauran en orden LIFO |
| 6.4 | Deshacer sin historial | `deshacerCambio()` sin cambios previos | Devuelve `false` |
| 6.5 | Múltiples deshacer | Dos cambios; dos deshacer | Vuelve al perfil inicial |
| 6.6 | Actualizar con perfil nulo | `actualizarPerfil(null)` | Lanza `IllegalArgumentException` |

---

## 7. Árbol de habilidades

| # | Descripción | Acción | Resultado esperado |
|---|---|---|---|
| 7.1 | Crear árbol con raíz | `new ArbolHabilidades("Tecnología")` | Raíz con nombre "Tecnología" |
| 7.2 | Agregar habilidad hija | `agregar("Tecnología", "Desarrollo")` | "Desarrollo" aparece como hijo de "Tecnología" |
| 7.3 | Agregar en profundidad | `agregar("Desarrollo", "Java")` | "Java" aparece como hijo de "Desarrollo" |
| 7.4 | Buscar habilidad existente | `buscar("Java")` | Devuelve el nodo correspondiente |
| 7.5 | Buscar habilidad inexistente | `buscar("Diseño")` | Devuelve `null` |
| 7.6 | Agregar bajo padre inexistente | `agregar("Inexistente", "X")` | Lanza `IllegalArgumentException` |
| 7.7 | Mostrar jerarquía | `mostrarJerarquia()` | Imprime árbol con indentación correcta |
| 7.8 | Búsqueda insensible a mayúsculas | `buscar("java")` | Encuentra el nodo "Java" |
