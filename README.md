# README RamaTom
## Modelo ER
<img width="791" height="511" alt="Captura de pantalla 2025-09-06 164815" src="https://github.com/user-attachments/assets/a82b85be-15dd-41ed-91c0-21a078088ae4" />

## Mockups y Diagramas de Navegación
<img width="1058" height="563" alt="image" src="https://github.com/user-attachments/assets/899bfc27-0550-4884-99e0-555d2cb6374c" />

<img width="1062" height="518" alt="image" src="https://github.com/user-attachments/assets/ebaf2691-02e2-4b7b-9c25-4546d0e34abb" />

<img width="1060" height="514" alt="image" src="https://github.com/user-attachments/assets/408af769-2d92-44b9-80d6-9b9f95344dc7" />

<img width="1014" height="688" alt="image" src="https://github.com/user-attachments/assets/87863876-504f-4fd8-9d58-dc1d5edc701e" />

<img width="1023" height="374" alt="image" src="https://github.com/user-attachments/assets/4ddc54d5-50d9-4e20-ab52-1b438709d93e" />

<img width="1875" height="157" alt="image" src="https://github.com/user-attachments/assets/878c869f-027a-4713-aa21-ccd6addc241b" />

<img width="1840" height="453" alt="image" src="https://github.com/user-attachments/assets/cea49887-e7b5-4ea4-8bf3-ee1a8760b1d2" />

<img width="1840" height="232" alt="image" src="https://github.com/user-attachments/assets/ecc1b101-f83a-4cf1-b5bb-fd8b80e6d5bb" />







## Casos de Uso Regata Online

### Actores del Sistema

**Jugador:** Usuario que participa en las carreras y controla barcos  
**Administrador:** Usuario que gestiona el sistema pero no participa en carreras

### Casos de Uso por Módulo

#### 1. Gestión de Autenticación

**CU-001: Iniciar Sesión**  
**Actor:** Jugador, Administrador  
**Descripción:** Permite a los usuarios autenticarse en el sistema  
**Flujo Principal:**
1. El usuario accede a la pantalla de inicio de sesión
2. Ingresa sus credenciales (usuario y contraseña)
3. El sistema valida las credenciales
4. Si son correctas, redirige al usuario según su rol (dashboard de jugador o administrador)
5. Si son incorrectas, muestra mensaje de error

**Flujos Alternativos:**
- 3a. Credenciales incorrectas: El sistema muestra mensaje de error y permite reintentar

**CU-002: Cerrar Sesión**  
**Actor:** Jugador, Administrador  
**Descripción:** Permite al usuario salir del sistema  
**Flujo Principal:**
1. El usuario selecciona la opción de cerrar sesión
2. El sistema invalida la sesión actual
3. Redirige a la pantalla de inicio de sesión

#### 2. Gestión de Jugadores

**CU-003: Listar Jugadores**  
**Actor:** Administrador  
**Descripción:** Muestra la lista completa de jugadores registrados  
**Flujo Principal:**
1. El administrador accede a la sección de gestión de jugadores
2. El sistema muestra una lista con todos los jugadores (ID, nombre, email, fecha de registro)
3. Se proporcionan opciones para ver detalles, editar o eliminar cada jugador

**CU-004: Ver Detalles de Jugador**  
**Actor:** Administrador  
**Descripción:** Muestra información detallada de un jugador específico  
**Flujo Principal:**
1. El administrador selecciona un jugador de la lista
2. El sistema muestra los detalles completos del jugador
3. Se muestran también los barcos asociados a ese jugador

**CU-005: Crear Jugador**  
**Actor:** Administrador  
**Descripción:** Permite registrar un nuevo jugador en el sistema  
**Flujo Principal:**
1. El administrador accede al formulario de creación de jugador
2. Completa los campos requeridos (nombre, email, contraseña)
3. El sistema valida la información ingresada
4. Si es válida, crea el jugador y muestra mensaje de confirmación
5. Redirige a la lista de jugadores

**Flujos Alternativos:**
- 3a. Datos inválidos: El sistema muestra errores específicos y permite corregir
- 3b. Email duplicado: El sistema informa que el email ya está registrado

**CU-006: Editar Jugador**  
**Actor:** Administrador  
**Descripción:** Permite modificar la información de un jugador existente  
**Flujo Principal:**
1. El administrador selecciona editar un jugador
2. El sistema muestra el formulario pre-llenado con los datos actuales
3. El administrador modifica los campos deseados
4. El sistema valida la información
5. Si es válida, actualiza el jugador y muestra confirmación

**CU-007: Eliminar Jugador**  
**Actor:** Administrador  
**Descripción:** Permite eliminar un jugador del sistema  
**Flujo Principal:**
1. El administrador selecciona eliminar un jugador
2. El sistema solicita confirmación de la acción
3. Si se confirma, elimina el jugador y todos sus barcos asociados
4. Muestra mensaje de confirmación

#### 3. Gestión de Modelos de Barcos

**CU-008: Listar Modelos de Barcos**  
**Actor:** Administrador  
**Descripción:** Muestra todos los modelos de barcos disponibles  
**Flujo Principal:**
1. El administrador accede a la gestión de modelos
2. El sistema muestra la lista de modelos (nombre, color, cantidad de barcos que usan el modelo)
3. Se proporcionan opciones de CRUD para cada modelo

**CU-009: Ver Detalles de Modelo**  
**Actor:** Administrador  
**Descripción:** Muestra información detallada de un modelo específico  
**Flujo Principal:**
1. El administrador selecciona un modelo de la lista
2. El sistema muestra los detalles del modelo
3. Se muestra también la lista de barcos que usan este modelo

**CU-010: Crear Modelo de Barco**  
**Actor:** Administrador  
**Descripción:** Permite crear un nuevo modelo de barco  
**Flujo Principal:**
1. El administrador accede al formulario de creación de modelo
2. Ingresa nombre del modelo y selecciona color
3. El sistema valida que el nombre no esté duplicado
4. Crea el modelo y muestra confirmación

**CU-011: Editar Modelo de Barco**  
**Actor:** Administrador  
**Descripción:** Permite modificar un modelo existente  
**Flujo Principal:**
1. El administrador selecciona editar un modelo
2. Modifica nombre y/o color en el formulario
3. El sistema valida los cambios
4. Actualiza el modelo y confirma la operación

**CU-012: Eliminar Modelo de Barco**  
**Actor:** Administrador  
**Descripción:** Permite eliminar un modelo de barco  
**Flujo Principal:**
1. El administrador selecciona eliminar un modelo
2. El sistema verifica si hay barcos usando el modelo
3. Si no hay barcos asociados, permite la eliminación con confirmación
4. Elimina el modelo y muestra confirmación

**Flujos Alternativos:**
- 2a. Hay barcos asociados: El sistema informa que no se puede eliminar y sugiere reasignar los barcos primero

#### 4. Gestión de Barcos

**CU-013: Listar Barcos**  
**Actor:** Administrador  
**Descripción:** Muestra todos los barcos del sistema  
**Flujo Principal:**
1. El administrador accede a la gestión de barcos
2. El sistema muestra la lista con ID, modelo, propietario, posición y velocidad actual
3. Permite filtrar por jugador o modelo
4. Proporciona opciones de CRUD

**CU-014: Ver Detalles de Barco**  
**Actor:** Administrador  
**Descripción:** Muestra información completa de un barco  
**Flujo Principal:**
1. El administrador selecciona un barco
2. El sistema muestra todos los detalles incluyendo historial de posiciones si está disponible

**CU-015: Crear Barco**  
**Actor:** Administrador  
**Descripción:** Permite crear un nuevo barco  
**Flujo Principal:**
1. El administrador accede al formulario de creación
2. Selecciona modelo de barco y jugador propietario
3. Establece posición inicial (opcional)
4. El sistema crea el barco con velocidad inicial (0,0)
5. Muestra confirmación

**CU-016: Editar Barco**  
**Actor:** Administrador  
**Descripción:** Permite modificar un barco existente  
**Flujo Principal:**
1. El administrador selecciona editar un barco
2. Puede cambiar modelo, propietario, posición y velocidad
3. El sistema valida los cambios
4. Actualiza el barco y confirma

**CU-017: Eliminar Barco**  
**Actor:** Administrador  
**Descripción:** Permite eliminar un barco  
**Flujo Principal:**
1. El administrador selecciona eliminar un barco
2. El sistema solicita confirmación
3. Elimina el barco y muestra confirmación

#### 5. Gestión de Mapas

**CU-018: Ver Mapa Actual**  
**Actor:** Administrador, Jugador  
**Descripción:** Muestra el mapa de carrera actual  
**Flujo Principal:**
1. El usuario accede a la visualización del mapa
2. El sistema muestra la cuadrícula con todos los tipos de celdas
3. Se muestran las posiciones actuales de todos los barcos
4. Se identifican claramente la línea de partida y meta

**CU-019: Cargar Mapa**  
**Actor:** Administrador  
**Descripción:** Permite cargar un nuevo mapa para las carreras  
**Flujo Principal:**
1. El administrador accede a la gestión de mapas
2. Selecciona un archivo de mapa o usa el editor integrado
3. El sistema valida que el mapa tenga al menos una celda de partida y una de meta
4. Carga el mapa y lo establece como activo
5. Reinicia las posiciones de todos los barcos

#### 6. Sistema de Juego

**CU-020: Iniciar Carrera**  
**Actor:** Sistema (automático cuando hay jugadores listos)  
**Descripción:** Inicia una nueva carrera cuando hay jugadores preparados  
**Flujo Principal:**
1. El sistema detecta que hay al menos un jugador conectado
2. Coloca todos los barcos en las celdas de partida disponibles
3. Resetea las velocidades a (0,0)
4. Inicia el primer turno
5. Notifica a todos los jugadores que la carrera ha comenzado

**CU-021: Realizar Movimiento**  
**Actor:** Jugador  
**Descripción:** Permite a un jugador realizar su movimiento en su turno  
**Flujo Principal:**
1. El sistema notifica al jugador que es su turno
2. El jugador visualiza las opciones de movimiento disponibles
3. Selecciona cómo modificar cada componente de velocidad (+1, 0, -1)
4. El sistema calcula la nueva posición basada en la velocidad resultante
5. Valida que la nueva posición sea válida (no sea una pared)
6. Mueve el barco a la nueva posición
7. Actualiza la velocidad del barco
8. Pasa el turno al siguiente jugador

**Flujos Alternativos:**
- 4a. El barco chocaría con una pared: El barco es destruido y el jugador pierde
- 4b. El barco llega a la meta: El jugador gana la carrera si es el primero

**CU-022: Ver Estado de Carrera**  
**Actor:** Jugador  
**Descripción:** Permite ver el estado actual de la carrera  
**Flujo Principal:**
1. El jugador accede a la vista de carrera
2. El sistema muestra el mapa con las posiciones actuales de todos los barcos
3. Se muestra el turno actual y de quién es
4. Se muestran las velocidades actuales de todos los barcos
5. Se indica si algún barco ha sido destruido

**CU-023: Finalizar Carrera**  
**Actor:** Sistema (automático)  
**Descripción:** Finaliza la carrera cuando se cumplen las condiciones  
**Flujo Principal:**
1. El sistema detecta que un barco ha llegado a la meta
2. Declara ganador al primer barco en llegar
3. Finaliza la carrera y registra los resultados
4. Notifica a todos los jugadores el resultado
5. Permite iniciar una nueva carrera

**Flujos Alternativos:**
- 1a. Todos los barcos son destruidos: La carrera termina sin ganador

**CU-024: Abandonar Carrera**  
**Actor:** Jugador  
**Descripción:** Permite a un jugador salir de la carrera actual  
**Flujo Principal:**
1. El jugador selecciona abandonar carrera
2. El sistema solicita confirmación
3. Si se confirma, retira el barco del jugador de la carrera
4. El turno pasa al siguiente jugador si era el turno del que abandonó
5. Se notifica a los demás jugadores

#### 7. Casos de Uso de Consulta

**CU-025: Ver Dashboard de Jugador**  
**Actor:** Jugador  
**Descripción:** Muestra el panel principal del jugador  
**Flujo Principal:**
1. El jugador inicia sesión
2. El sistema muestra su dashboard con:
   - Estado de carrera actual (si hay una activa)
   - Lista de sus barcos
   - Estadísticas personales (carreras ganadas, perdidas)
   - Opción para unirse a carrera

**CU-026: Ver Dashboard de Administrador**  
**Actor:** Administrador  
**Descripción:** Muestra el panel principal del administrador  
**Flujo Principal:**
1. El administrador inicia sesión
2. El sistema muestra su dashboard con:
   - Resumen de entidades (cantidad de jugadores, barcos, modelos)
   - Estado del sistema de carreras
   - Accesos rápidos a funciones de gestión
   - Logs de actividad del sistema

**CU-027: Ver Historial de Carreras**  
**Actor:** Administrador  
**Descripción:** Muestra el historial de todas las carreras realizadas  
**Flujo Principal:**
1. El administrador accede al historial
2. El sistema muestra una lista de carreras con fecha, participantes, ganador y duración
3. Permite filtrar por jugador, fecha o estado
4. Permite ver detalles de cada carrera específica
