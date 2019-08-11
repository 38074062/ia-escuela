**Enunciado**

•	Administración de titulares: se debe registrar al titular que abona las cuotas.

•	Administración de alumnos: se registran los alumnos que asisten a la entidad y se asocia a su titular.

•	Administración de cuotas: se definen los distintos tipos de cuotas y adicionales que ofrece la entidad:

o	Escolaridad:  integrado por la cuota por asistencia a la entidad, puede ser jornada completa o media jornada.
  
o	Adicionales: son actividades extracurriculares que la entidad ofrece a los alumnos (idioma, teatro, deportes, etc)

o	Comedor: almuerzos, viandas media mañana, meriendas.

•	Facturación y cobranza: registrar la facturación mensual y la cobranza de las cuotas.

•	Administración de empleados: el sistema debe registrar los datos personales y laborales de sus empleados y docentes.



**Configuración inicial del proyecto** 

1. Ejecutar el archivo "create_schema.sql" de la carpeta "sql" (va a crear la base de datos y el usuario que se va a usar en la aplicación).

2. Desde Eclipse y con el proyecto abierto, ir a "Run" -> "Run configurations". En el listado lateral, seleccionar "Maven Build" y agregar la configuración seleccionando "New configuration".

3. En "Base Directory" seleccionar "Workspace" y a continuación el proyecto. En "Goals", pegar "spring-boot:run". Por último, seleccionar "Apply" y luego "Run". En las próximas ejecuciones sólo habrá que seleccionar la configuración creada y "Run".
