Parcial 1 - Aplicaciones Móviles
Integrantes: Santiago Iriarte  
Comisión: ACN4AV  
Profesor: Sergio Medina  
Decidi encarar una app de finanzas personales. La idea es que sea algo simple pero realista para llevar el control de lo que gasto en el día a día, con un diseño oscuro y moderno.  

Lo primero en canva realice algunas animaciones simples para que se vea de fondo en la app y cada vez que la abris.  
<img width="841" height="883" alt="Screenshot 2026-05-07 051825" src="https://github.com/user-attachments/assets/95abc319-4668-45d6-97b9-335c1c971c75" />
<img width="1232" height="813" alt="Screenshot 2026-05-07 051913" src="https://github.com/user-attachments/assets/b116f8cd-809b-4ac7-89f9-6f558d334070" />
<img width="1727" height="774" alt="Screenshot 2026-05-07 051857" src="https://github.com/user-attachments/assets/d00fa999-6ab7-434a-bf97-77dea9f6449e" />
Implemente el diseño usando principalmente ConstraintLayout y LinearLayout para que todo quede bien acomodado.    
MainActivity: Es el panel principal donde se ve el gasto total y una lista de los últimos movimientos.  
FormularioGastoActivity: Acá es donde el usuario carga la info. Usamos EditText para el monto y la categoría, y un botón para confirmar.  
ResumenActivity: Una pantalla intermedia que te confirma lo que acabas de anotar.  
EstadisticasActivity: Agrupe los gastos por categoría y mostramos barras de progreso dinámicas.  
Arme una clase para manejar la información en toda la app y use SharedPreferences para que, si cerrás la app, los gastos no se borren.  
La lista de transacciones y las barras de las estadísticas se crean desde el código Java.  
Configure los Intents para moverse entre pantallas pasando los datos de los gastos de un lado al otro  
Agregamos un video de fondo en todas las pantallas para que tenga más onda y usamos colores específicos para identificar cada categoría de gasto en el resumen estadístico.   
Flujo de uso   
Abrís la app y ves tu gasto total de la semana. Tocás en "+ Agregar" para ir al formulario. Cargás cuánto gastaste y en qué, y le das a "Confirmar". Pasás por una pantalla de Resumen que te confirma el éxito de la carga. Volvés al inicio y podés entrar a "Ver estadísticas" para ver el detalle de tus finanzas por categorías
