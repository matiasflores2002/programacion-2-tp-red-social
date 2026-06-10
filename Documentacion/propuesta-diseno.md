# Propuesta de diseño

**Materia:** Programación 2
**Alternativa:** A — Ecosistema de Red Social Profesional

---

## Problema elegido

Diseñar e implementar un sistema que simule una red social profesional. El sistema debe gestionar usuarios, sus conexiones, recomendaciones de contactos, postulaciones laborales, historial de cambios de perfil y una jerarquía de habilidades.

Cada módulo fue resuelto con la estructura de datos más adecuada, priorizando eficiencia y claridad para el nivel de la materia.

---

## Módulos y lógica de resolución

### 1. Registro y búsqueda de usuarios — HashMap

**Problema:** registrar usuarios y recuperarlos por ID de forma rápida.

**Solución:** `HashMap<String, Usuario>` donde la clave es el ID. Permite búsqueda, inserción y validación de duplicados en O(1) promedio, independientemente de la cantidad de usuarios.

**Validaciones:** ID no vacío, perfil no nulo, no permitir IDs duplicados.

---

### 2. Conexiones profesionales — Grafo no dirigido

**Problema:** representar relaciones entre usuarios (una conexión es mutua).

**Solución:** lista de adyacencia implementada con `HashMap<String, Set<String>>`. Cada clave es un ID de usuario y su valor es el conjunto de IDs con quienes está conectado. Usar `HashSet` para los vecinos permite verificar si ya existe una conexión en O(1).

**Validaciones:** no conectar un usuario consigo mismo, no crear aristas duplicadas, verificar que ambos nodos existan.

---

### 3. Grado de separación — BFS

**Problema:** encontrar la menor cantidad de pasos entre dos usuarios.

**Solución:** recorrido BFS (Breadth-First Search) desde el nodo origen. BFS garantiza encontrar el camino más corto en grafos no ponderados. Se mantiene un mapa de distancias y un conjunto de visitados. Si el destino no es alcanzable, se devuelve -1.

**Complejidad:** O(V + E) donde V son usuarios y E son conexiones.

---

### 4. Recomendador de contactos — vínculos comunes

**Problema:** sugerir usuarios relevantes que no son contactos directos.

**Solución:** para cada contacto directo del usuario A, se recorren sus vecinos. Si un vecino no es A ni contacto directo de A, se incrementa su contador de vínculos comunes. Al final se ordena el resultado de mayor a menor usando `List.sort`. Complejidad O(k²) donde k es el grado promedio del nodo.

---

### 5. Postulaciones laborales — Cola FIFO

**Problema:** procesar postulaciones en el orden en que fueron recibidas.

**Solución:** TDA `Cola<T>` implementada sobre `LinkedList`. `addLast` para encolar y `removeFirst` para desencolar garantizan orden FIFO. Las operaciones de encolar y desencolar son O(1).

---

### 6. Historial de perfil — Pila LIFO

**Problema:** poder deshacer la última actualización del perfil de un usuario.

**Solución:** TDA `Pila<T>` implementada sobre `LinkedList`. Antes de reemplazar el perfil actual, se apila el estado anterior. Para deshacer, se desapila y restaura. Apilar y desapilar son O(1). La pila vive dentro del `Usuario` porque el historial es parte de su estado.

---

### 7. Jerarquía de habilidades — Árbol

**Problema:** organizar habilidades en categorías y subcategorías.

**Solución:** árbol general (cada nodo puede tener N hijos) implementado con `NodoHabilidad`, que contiene una `List<NodoHabilidad>`. La búsqueda es recursiva (DFS). La visualización también es recursiva, aumentando el nivel de indentación en cada nivel. Complejidad de búsqueda: O(N) donde N es la cantidad de nodos.

---

## Diagramas de las estructuras

### HashMap de usuarios

```
"u1" ──► Usuario[u1] { perfil, historial }
"u2" ──► Usuario[u2] { perfil, historial }
"u3" ──► Usuario[u3] { perfil, historial }
```

---

### Grafo (lista de adyacencia)

```
u1 ──► { u2, u3 }
u2 ──► { u1, u4 }
u3 ──► { u1 }
u4 ──► { u2 }
```

Conexión u1—u2 aparece en ambas listas (grafo no dirigido).

---

### BFS — grado de separación u1 → u4

```
Nivel 0: [ u1 ]
Nivel 1: [ u2, u3 ]       ← contactos directos de u1
Nivel 2: [ u4 ]            ← contactos de u2 no visitados
                             → u4 encontrado en nivel 2
```

---

### Cola FIFO — postulaciones

```
Entrada ──► [ p1 | p2 | p3 ] ──► Salida
               ↑                    ↑
           encolar               desencolar (p1 primero)
```

---

### Pila LIFO — historial de perfil

```
apilar ──►  [ perfilC ]  ← tope (último guardado)
            [ perfilB ]
            [ perfilA ]  ← base (primer guardado)
desapilar ◄── perfilC   (restaura el más reciente)
```

---

### Árbol de habilidades

```
Tecnología
├── Desarrollo
│   ├── Java
│   │   └── Spring Boot
│   └── Python
└── Infraestructura
    └── Redes
```

---

## Justificación de rendimiento

| Operación | Estructura | Complejidad |
|---|---|---|
| Buscar usuario por ID | HashMap | O(1) promedio |
| Verificar conexión existente | HashSet en Grafo | O(1) promedio |
| Grado de separación | BFS | O(V + E) |
| Recomendar contactos | Recorrido de vecinos | O(k²) — k = grado promedio |
| Encolar / desencolar | Cola sobre LinkedList | O(1) |
| Apilar / desapilar | Pila sobre LinkedList | O(1) |
| Buscar habilidad en árbol | DFS recursivo | O(N) |

---

## Decisiones de diseño

- Los TDAs (`Cola`, `Pila`, `Grafo`) son genéricos o parametrizados para ser reutilizables y no depender del dominio.
- La `Pila` del historial vive dentro de `Usuario` porque el historial es parte del estado del usuario, no de un servicio externo.
- `BuscadorRutas` y `Recomendador` son servicios separados que reciben el `Grafo` por constructor (inyección simple), facilitando su uso independiente.
- No se usaron frameworks externos; todas las estructuras se implementaron usando las colecciones estándar de Java (`LinkedList`, `HashMap`, `HashSet`, `ArrayList`) como soporte interno.
