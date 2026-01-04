# Caso Práctico Tema 6: Departamento de Misterios
**Asignatura**: Programación Concurrente
**Contexto**: Implementación de un Sistema de Procesamiento por Lotes en el Departamento de Misterios utilizando Spring Batch.

---

## 1. Integrantes del Grupo
* **María Díaz - Heredero López** NP: 152337
* **Cintia Santillán García** NP: 154184
* **Suren Hashemi Alam** NP: 155168

---

## 2. Tecnologías Utilizadas
Para el desarrollo de este sistema se han empleado las tecnologías y versiones especificadas en la documentación técnica del tema:
**Spring Batch 4**: Framework para el procesamiento por lotes masivo y eficiente.
* **Java 17 (Compatible con JDK 8+)**: Requisito para aprovechar las características de la versión 4 de Spring Batch.
* **Spring Framework 5.x**: Proporciona el soporte de infraestructura y capacidades reactivas.
* **Spring Boot**: Utilizado por su simplicidad de implementación y gestión óptima de dependencias.
* **H2 Database**: Base de datos en memoria para gestionar el `JobRepository` y los datos procesados.
* **Spring Actuator**: Configurado para la monitorización del estado del sistema y los trabajos batch.

---

## 3. Lógica de la Solución
El sistema se ha diseñado siguiendo la arquitectura modular de Spring Batch, descomponiéndose en un lanzador (**Job Launcher**), trabajos (**Jobs**) y etapas (**Steps**) .

### Proceso Batch (Reader-Processor-Writer)
Cada etapa funcional sigue el patrón de tres partes:
1. **Reader**: Lee datos mágicos de entrada desde un archivo plano `hechizos.csv`.
2. **Processor**: Transforma los datos (normalización de nombres a mayúsculas) aplicando la lógica de negocio del Ministerio.
3. **Writer**: Registra los datos procesados en la base de datos H2.

### Eficiencia y Concurrencia
* **Procesamiento por Chunks**: El sistema realiza transacciones en lotes (chunks) de 10 elementos, optimizando el rendimiento y el uso de la memoria.
* **Gestión de Estados**: Se utiliza un `JobRepository` para trazar y persistir cada ejecución en la base de datos, lo que garantiza la reanudación de trabajos fallidos desde el último punto seguro.
* **Configuración con Builders**: Se han empleado los nuevos **Builders** de Spring Batch 4 (como `FlatFileItemReaderBuilder` y `JdbcBatchItemWriterBuilder`) para simplificar y clarificar el código.



---

## 4. Estructura de Archivos de Relevancia
A continuación se detalla el contenido de los archivos necesarios para entender la solución:
* **`Hechizo.java`**: POJO que representa la entidad de datos mágicos (id, nombre, tipo, nivel).
* **`HechizoItemProcessor.java`**: Implementa la lógica de transformación de los ítems.
* **`BatchConfiguration.java`**: Clase de configuración que define los beans para el Job y el Step.
* **`JobCompletionNotificationListener.java`**: Listener que notifica y verifica los resultados al finalizar el ciclo de vida del trabajo.
* **`MagiaApplication.java`**: Clase principal que marca el punto de entrada de la aplicación Spring Boot.
* **`schema-all.sql`**: Script SQL para la creación de las tablas de datos y de gestión del batch.
* **`application.properties`**: Archivo de configuración para exponer los endpoints de monitorización de Spring Actuator.
* **`hechizos.csv`**: Archivo de recursos con los datos de entrada para el procesamiento.

---

## 5. Resultados y Monitorización
El sistema garantiza un alto rendimiento mediante:
* **Monitoreo**: Acceso en tiempo real a la salud del sistema a través de `/actuator/health`.
* **Logging**: Registro detallado de cada transformación y evento para rastrear posibles errores.
