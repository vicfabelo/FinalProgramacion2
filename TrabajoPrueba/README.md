# Sistema de Gestión Logística 

##  Integrantes del Grupo 
* Victoria Vasquez 
* Thiago Santin 
---

## Alternativa Elegida
Alternativa C: Centro Logistico de Distribucion Avanzada

---


## Estructura del proyecto

* *Main*: inicia la aplicación.
* *menu*: interacción con el usuario por consola.
* *sistema*: lógica principal del negocio.
* *modelo*: clases de dominio (Producto, Pedido, Movimiento, Ubicacion, etc.).
* *tdas*: estructuras de datos implementadas manualmente (árbol, cola, pila, grafo y lista doble).

## Requisitos

* Java JDK 17 o superior.

Verificar instalación:

bash
java -version
javac -version


## Ejecución

### Desde IntelliJ

1. Abrir el proyecto.
2. Abrir Main.java.
3. Ejecutar la clase Main.

### Desde consola

Compilar:

bash
javac -d out src/**/*.java


Ejecutar:

bash
java -cp out Main


## Uso general

El sistema trabaja mediante menús numéricos.

### Productos

Permite:

* Agregar productos.
* Buscar productos.
* Modificar stock.
* Eliminar productos.
* Mostrar productos con stock crítico.

Datos esperados:

* Código: número entero.
* Nombre: texto.
* Stock: número entero.
* Ubicación: sector, estantería y nivel.

### Pedidos

Permite:

* Registrar pedidos.
* Cambiar estado.
* Despachar pedidos.

*Importante:* cambiar el estado a DESPACHADO no realiza el despacho automáticamente. Luego debe utilizarse la opción *Despachar pedido* para confirmar el proceso y descontar stock.

Datos esperados:

* ID pedido: número.
* Código producto: número.
* Cantidad solicitada: número.

### Movimientos

Los movimientos representan entradas, salidas o ajustes de mercadería.

El registro es *manual*:

1. Seleccionar "Registrar observacion".
2. Ingresar código del producto.
3. Ingresar una descripción.

También puede:

* Ver el último observacion.
* Deshacer el último observacion registrado.

### Depósito

Permite administrar el mapa de pasillos mediante un grafo.

Funciones:

* Agregar pasillos.
* Eliminar pasillos.
* Conectar pasillos.
* Mostrar conexiones.
* Recorridos BFS y DFS.

Los códigos de pasillo deben tener formato:

text
P1
P2
P10
P25


Es decir, la letra *P* seguida de uno o más números.

## Prueba rápida

1. Crear algunos pasillos (P1, P2, P3).
2. Conectarlos.
3. Agregar productos.
4. Crear pedidos.
5. Cambiar el estado del pedido.
6. Despacharlo.
7. Registrar movimientos manualmente.
8. Consultar productos con stock bajo.
9. Probar recorridos BFS y DFS del depósito.

## Estructuras utilizadas

* *Árbol ABB:* almacenamiento y búsqueda de productos.
* *Cola:* gestión de pedidos.
* *Pila:* historial de observaciones.
* *Grafo:* representación del depósito.
* *Lista doble:* apoyo para recorridos y listados.


---

## Estructuras de Datos Utilizadas

1. **TDA Árbol Binario de Búsqueda (ABB) para Productos:**
   * **Clase interna:** `ArbolProductos` (implementa `IArbolProductos`).
   * **Funcionamiento:** Administra el catálogo completo de productos ordenados por su código identificador único mediante nodos enlazados (`NodoArbolProducto`). Optimiza los accesos a datos en inserciones, búsquedas y bajas sin las restricciones de tamaño de un arreglo estático. Asimismo, facilita recorridos recursivos para listar y filtrar elementos (como la detección de stock crítico).

2. **TDA Cola Dinámica para Pedidos (Estructura FIFO):**
   * **Clase interna:** `ColaPedidos` (implementa `IColaPedidos`).
   * **Funcionamiento:** Encola las solicitudes de los clientes en estricto orden cronológico de llegada mediante `NodoCola`, asegurando que el primer pedido en ingresar sea el primero en ser despachado.

3. **TDA Pila Dinámica para Auditoría de Observaciones (Estructura LIFO):**
   * **Clase interna:** `PilaObservaciones` (implementa `IPilaObservaciones`).
   * **Funcionamiento:** Actúa como el log de auditoría del negocio. Registra secuencialmente los eventos o comentarios de inventario utilizando `NodoPila`, lo que permite visualizar o deshacer rápidamente la última anotación registrada.

4. **TDA Grafo para la Distribución del Depósito:**
   * **Clase interna:** `GrafoDeposito` (implementa `IGrafoDeposito`).
   * **Funcionamiento:** Representa el mapa físico de los pasillos y sus conexiones bidireccionales mediante **Listas de Adyacencia** dinámicas (`NodoPasillo` y `NodoAdyacente`), optimizando el uso de memoria en comparación con matrices estáticas. Sobre este se ejecutan de forma iterativa y recursiva los algoritmos de exploración BFS y DFS.
   * *Estructura auxiliar:* Cuenta con una implementación interna de `ColaPasillos` (estructurada mediante `NodoColaPasillo`) diseñada específicamente para gestionar los nodos del grafo durante el recorrido BFS.

5. **TDA Lista Doblemente Enlazada:**
   * **Clase interna:** `ListaDoble` (implementa `IListaDoble`).
   * **Funcionamiento:** Estructura auxiliar lineal utilizada para almacenar y manipular colecciones dinámicas de datos de forma eficiente en ambas direcciones (utilizada, por ejemplo, por el sistema logístico para agrupar y retornar los productos con stock crítico).

---

## Funcionalidades implementadas en esta segunda etapa (muchas cosas fueron modificads)
Durante esta segunda etapa se logró la abstracción, modelado e implementación del núcleo de gestión de inventario, cumpliendo con los siguientes puntos:

* **Modelado de la Entidad Principal (`Producto.java`):** Creación de la clase que representa la mercadería del depósito, encapsulando los atributos esenciales para la lógica del negocio: `codigo`, `nombre`, `ubicacion` y `stock`, junto con sus respectivos métodos de acceso y modificación y la sobreescritura de `toString()`.
* **Definición de Contratos de Interfaz (`IDiccionarioProductos.java`):** Abstracción formal del comportamiento del diccionario mediante una interfaz que estipula las operaciones necesarias para la administración del almacén (`insertar`, `eliminar`, `recuperarProducto`, `modificar`, `existe`, `estaVacio`, `tamanio`, `listarClaves` y `mostrar`). 
* **Lógica del Diccionario de Productos (`tdas.implementaciones.ArbolProductos.java` y `DatoProducto.java`):** 
  * **Inserción con validación:** Permite registrar nuevos productos controlando que el diccionario no supere su dimensión máxima y asegurando que no se dupliquen códigos existentes.
  * **Eliminación y reordenamiento:** Borra un producto por su clave y reubica los elementos restantes dentro del arreglo para mantener la contigüidad de los datos.
  * **Búsqueda, Recuperación y Modificación:** Localiza la posición de una clave para retornar el objeto `Producto` asociado o actualizar sus valores en memoria.
* **Entorno de Pruebas y Verificación (`Main.java`):** Simulación por consola de un flujo real de trabajo en el depósito que inicializa el diccionario con capacidad para 100 elementos, ejecuta el registro exitoso de múltiples productos (`Teclado`, `Mouse`, `Monitor`), expone el inventario actual, realiza la baja de mercadería y valida el estado actualizado del sistema.

---

## Link del Repositorio
https://github.com/Thiago-Santin/TrabajoPrueba.git

---

## Actividades realizadas por cada integrantes :
* Victoria Vasquez: Codificacion y Planificacion del programa
* Thiago Santin: Codificacion y Planificacion del programa
