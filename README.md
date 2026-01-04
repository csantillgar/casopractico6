# Caso Práctico Tema 6: Departamento de Misterios
**Asignatura**: Programación Concurrente
[cite_start]**Contexto**: Implementación de un Sistema de Procesamiento por Lotes en el Departamento de Misterios utilizando Spring Batch[cite: 4, 59].

---

## 1. Integrantes del Grupo
* **María Díaz - Heredero López** NP: 152337
* **Cintia Santillán García** NP: 154184
* **Suren Hashemi Alam** NP: 155168

---

## 2. Tecnologías Utilizadas
[cite_start]Para el desarrollo de este sistema se han empleado las tecnologías y versiones especificadas en la documentación técnica del tema[cite: 474, 475]:
* [cite_start]**Spring Batch 4**: Framework para el procesamiento por lotes masivo y eficiente[cite: 61, 185].
* [cite_start]**Java 17 (Compatible con JDK 8+)**: Requisito para aprovechar las características de la versión 4 de Spring Batch[cite: 482, 519].
* [cite_start]**Spring Framework 5.x**: Proporciona el soporte de infraestructura y capacidades reactivas[cite: 484, 520].
* [cite_start]**Spring Boot**: Utilizado por su simplicidad de implementación y gestión óptima de dependencias[cite: 159, 160].
* [cite_start]**H2 Database**: Base de datos en memoria para gestionar el `JobRepository` y los datos procesados[cite: 275, 471].
* [cite_start]**Spring Actuator**: Configurado para la monitorización del estado del sistema y los trabajos batch[cite: 617, 657].

---

## 3. Lógica de la Solución
[cite_start]El sistema se ha diseñado siguiendo la arquitectura modular de Spring Batch, descomponiéndose en un lanzador (**Job Launcher**), trabajos (**Jobs**) y etapas (**Steps**) [cite: 161, 163, 167-169].

### Proceso Batch (Reader-Processor-Writer)
[cite_start]Cada etapa funcional sigue el patrón de tres partes [cite: 175-179, 182-183]:
1. [cite_start]**Reader**: Lee datos mágicos de entrada desde un archivo plano `hechizos.csv`[cite: 120, 276].
2. [cite_start]**Processor**: Transforma los datos (normalización de nombres a mayúsculas) aplicando la lógica de negocio del Ministerio[cite: 121, 400].
3. [cite_start]**Writer**: Registra los datos procesados en la base de datos H2[cite: 121, 276].

### Eficiencia y Concurrencia
* [cite_start]**Procesamiento por Chunks**: El sistema realiza transacciones en lotes (chunks) de 10 elementos, optimizando el rendimiento y el uso de la memoria[cite: 125, 392].
* [cite_start]**Gestión de Estados**: Se utiliza un `JobRepository` para trazar y persistir cada ejecución en la base de datos, lo que garantiza la reanudación de trabajos fallidos desde el último punto seguro [cite: 122, 164, 463-469].
* [cite_start]**Configuración con Builders**: Se han empleado los nuevos **Builders** de Spring Batch 4 (como `FlatFileItemReaderBuilder` y `JdbcBatchItemWriterBuilder`) para simplificar y clarificar el código[cite: 64, 539, 607].



---

## 4. Estructura de Archivos de Relevancia
A continuación se detalla el contenido de los archivos necesarios para entender la solución:
* [cite_start]**`Hechizo.java`**: POJO que representa la entidad de datos mágicos (id, nombre, tipo, nivel)[cite: 315].
* [cite_start]**`HechizoItemProcessor.java`**: Implementa la lógica de transformación de los ítems[cite: 404, 421].
* [cite_start]**`BatchConfiguration.java`**: Clase de configuración que define los beans para el Job y el Step[cite: 232, 338, 340].
* [cite_start]**`JobCompletionNotificationListener.java`**: Listener que notifica y verifica los resultados al finalizar el ciclo de vida del trabajo[cite: 423, 441, 452].
* [cite_start]**`MagiaApplication.java`**: Clase principal que marca el punto de entrada de la aplicación Spring Boot[cite: 263, 299].
* [cite_start]**`schema-all.sql`**: Script SQL para la creación de las tablas de datos y de gestión del batch[cite: 462, 470].
* [cite_start]**`application.properties`**: Archivo de configuración para exponer los endpoints de monitorización de Spring Actuator[cite: 620, 658].
* [cite_start]**`hechizos.csv`**: Archivo de recursos con los datos de entrada para el procesamiento[cite: 351, 471].

---

## 5. Resultados y Monitorización
El sistema garantiza un alto rendimiento mediante:
* [cite_start]**Monitoreo**: Acceso en tiempo real a la salud del sistema a través de `/actuator/health`[cite: 657, 658].
* [cite_start]**Logging**: Registro detallado de cada transformación y evento para rastrear posibles errores[cite: 418, 457].
