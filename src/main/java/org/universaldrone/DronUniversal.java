package org.universaldrone;

/**
 * Una interfaz universal para controlar diferentes tipos de drones.
 * 
 * <p>Esta interfaz proporciona una forma común de controlar cualquier dron, ya sea un
 * dron Tello, un CoDrone EDU, o cualquier otro tipo de dron. Piensa en esto como un
 * control remoto universal que funciona con muchos dispositivos diferentes.</p>
 * 
 * <p>La interfaz incluye métodos para:</p>
 * <ul>
 *   <li>Conectarse y desconectarse del dron</li>
 *   <li>Despegar y aterrizar</li>
 *   <li>Mover el dron en diferentes direcciones</li>
 *   <li>Rotar el dron</li>
 *   <li>Obtener información sobre el dron (batería, altura, temperatura)</li>
 *   <li>Controlar la cámara y el video del dron</li>
 * </ul>
 * 
 * <h2>Ejemplo de Uso</h2>
 * <pre>{@code
 * // Crear un objeto dron (la implementación real depende del tipo de dron)
 * DronUniversal dron = new AdaptadorDronTello();
 * 
 * try {
 *     // Conectarse al dron
 *     dron.conectar();
 *     
 *     // Despegar y volar en un patrón cuadrado
 *     dron.despegar();
 *     for (int i = 0; i < 4; i++) {
 *         dron.moverAdelante(50, "cm", 20, "cm/s");
 *         dron.rotarDerecha(90);
 *     }
 *     
 *     // Revisar el nivel de batería
 *     int bateria = dron.obtenerBateria();
 *     System.out.println("Batería: " + bateria + "%");
 *     
 *     // Aterrizar y desconectarse
 *     dron.aterrizar();
 *     dron.desconectar();
 * } catch (Exception e) {
 *     System.err.println("Error: " + e.getMessage());
 * }
 * }</pre>
 * 
 * @author Universal Drone Interface
 * @version 1.0
 */
public interface DronUniversal {
    
    // ========== Métodos de Conexión ==========
    
    /**
     * Se conecta al dron.
     * 
     * <p>Este método establece una conexión con el dron para que puedas enviarle
     * comandos. Debes llamar a este método antes de usar cualquier otro método.
     * Piénsalo como encender el Bluetooth de tu teléfono para conectarte a una bocina.</p>
     * 
     * @throws Exception si la conexión falla (por ejemplo, el dron está apagado, fuera de alcance,
     *                   o ya está conectado a otro dispositivo)
     */
    void conectar() throws Exception;
    
    /**
     * Se desconecta del dron.
     * 
     * <p>Este método cierra la conexión con el dron. Siempre llama a este método cuando
     * termines de volar para liberar recursos y permitir que otros dispositivos se conecten.</p>
     */
    void desconectar();
    
    // ========== Despegue y Aterrizaje ==========
    
    /**
     * Hace que el dron despegue del suelo.
     * 
     * <p>El dron se elevará a una altura segura de vuelo (generalmente alrededor de 1 metro o
     * 3 pies). Asegúrate de que haya suficiente espacio arriba del dron antes de despegar.</p>
     * 
     * @throws Exception si el despegue falla (por ejemplo, batería baja, motores bloqueados,
     *                   o el dron ya está volando)
     */
    void despegar() throws Exception;
    
    /**
     * Hace que el dron aterrice de forma segura en el suelo.
     * 
     * <p>El dron descenderá lentamente hasta que toque el suelo y luego apagará
     * sus motores. Asegúrate de que el área de aterrizaje esté libre de obstáculos.</p>
     * 
     * @throws Exception si el aterrizaje falla o el dron no está volando
     */
    void aterrizar() throws Exception;
    
    // ========== Métodos de Movimiento Absoluto ==========
    
    /**
     * Mueve el dron a una posición exacta en el espacio 3D.
     * 
     * <p>Este método le dice al dron que vuele a una coordenada x, y, z específica.
     * No todos los drones admiten esta función - algunos solo pueden moverse en relación
     * a su posición actual.</p>
     * 
     * @param x la coordenada x de destino (horizontal, izquierda-derecha)
     * @param y la coordenada y de destino (horizontal, adelante-atrás)
     * @param z la coordenada z de destino (vertical, altura)
     * @param unidad la unidad de medida ("m" para metros, "cm" para centímetros, etc.)
     * @throws Exception si el dron no admite posicionamiento absoluto o
     *                   si la posición de destino no es alcanzable
     */
    void moverA(double x, double y, double z, String unidad) throws Exception;
    
    /**
     * Obtiene la posición actual del dron en el espacio 3D.
     * 
     * <p>Este método devuelve dónde está el dron en este momento. No todos los drones pueden
     * rastrear su posición - algunos solo saben qué tan lejos se han movido desde su
     * punto de partida.</p>
     * 
     * @param unidad la unidad de medida que deseas ("m" para metros, "cm" para centímetros, etc.)
     * @return un objeto Position que contiene las coordenadas x, y, z y la unidad
     * @throws Exception si el dron no admite rastreo de posición
     */
    Position obtenerPosicionActual(String unidad) throws Exception;
    
    // ========== Métodos de Movimiento Relativo (Navegación por Estima) ==========
    
    /**
     * Mueve el dron hacia adelante (en la dirección que está mirando).
     * 
     * <p>El dron se moverá directamente hacia adelante por la distancia especificada. Si el
     * dron está mirando al norte y llamas a moverAdelante(100, "cm", 20, "cm/s"),
     * se moverá 100 centímetros al norte a una velocidad de 20 centímetros por segundo.</p>
     * 
     * @param distancia qué tan lejos moverse
     * @param unidad la unidad para la distancia ("m", "cm", "in", etc.)
     * @param velocidad qué tan rápido moverse
     * @param unidadVelocidad la unidad para la velocidad ("m/s", "cm/s", etc.)
     * @throws Exception si el movimiento falla o los parámetros no son válidos
     */
    void moverAdelante(double distancia, String unidad, double velocidad, String unidadVelocidad) throws Exception;
    
    /**
     * Mueve el dron hacia atrás (opuesto a la dirección que está mirando).
     * 
     * <p>El dron se moverá directamente hacia atrás por la distancia especificada. Esto es
     * como moverse hacia adelante, pero en reversa. El dron no gira.</p>
     * 
     * @param distancia qué tan lejos moverse
     * @param unidad la unidad para la distancia ("m", "cm", "in", etc.)
     * @param velocidad qué tan rápido moverse
     * @param unidadVelocidad la unidad para la velocidad ("m/s", "cm/s", etc.)
     * @throws Exception si el movimiento falla o los parámetros no son válidos
     */
    void moverAtras(double distancia, String unidad, double velocidad, String unidadVelocidad) throws Exception;
    
    /**
     * Mueve el dron a la izquierda (desde su perspectiva).
     * 
     * <p>El dron se deslizará hacia la izquierda sin rotar. Imagina que
     * el dron está mirando al norte - llamar a moverIzquierda hará que se mueva al oeste mientras
     * sigue mirando al norte.</p>
     * 
     * @param distancia qué tan lejos moverse
     * @param unidad la unidad para la distancia ("m", "cm", "in", etc.)
     * @param velocidad qué tan rápido moverse
     * @param unidadVelocidad la unidad para la velocidad ("m/s", "cm/s", etc.)
     * @throws Exception si el movimiento falla o los parámetros no son válidos
     */
    void moverIzquierda(double distancia, String unidad, double velocidad, String unidadVelocidad) throws Exception;
    
    /**
     * Mueve el dron a la derecha (desde su perspectiva).
     * 
     * <p>El dron se deslizará hacia la derecha sin rotar. Esto es
     * lo opuesto a moverIzquierda.</p>
     * 
     * @param distancia qué tan lejos moverse
     * @param unidad la unidad para la distancia ("m", "cm", "in", etc.)
     * @param velocidad qué tan rápido moverse
     * @param unidadVelocidad la unidad para la velocidad ("m/s", "cm/s", etc.)
     * @throws Exception si el movimiento falla o los parámetros no son válidos
     */
    void moverDerecha(double distancia, String unidad, double velocidad, String unidadVelocidad) throws Exception;
    
    /**
     * Mueve el dron hacia arriba (aumenta la altitud).
     * 
     * <p>El dron ascenderá directamente hacia arriba por la distancia especificada. Ten cuidado
     * de no volar demasiado alto, especialmente en interiores o cerca de obstáculos.</p>
     * 
     * @param distancia qué tan lejos moverse hacia arriba
     * @param unidad la unidad para la distancia ("m", "cm", "in", etc.)
     * @throws Exception si el movimiento falla o el dron alcanza su altura máxima
     */
    void moverArriba(double distancia, String unidad) throws Exception;
    
    /**
     * Mueve el dron hacia abajo (disminuye la altitud).
     * 
     * <p>El dron descenderá directamente hacia abajo por la distancia especificada. Asegúrate
     * de que no haya nada debajo del dron antes de moverse hacia abajo.</p>
     * 
     * @param distancia qué tan lejos moverse hacia abajo
     * @param unidad la unidad para la distancia ("m", "cm", "in", etc.)
     * @throws Exception si el movimiento falla o el dron alcanza el suelo
     */
    void moverAbajo(double distancia, String unidad) throws Exception;
    
    /**
     * Rota el dron a la izquierda (en sentido antihorario).
     * 
     * <p>El dron girará en su lugar hacia la izquierda. Si el dron está mirando al norte,
     * llamar a rotarIzquierda(90) hará que mire al oeste. El dron permanece en la misma
     * posición - solo cambia de dirección.</p>
     * 
     * @param grados cuántos grados rotar (0-360)
     * @throws Exception si la rotación falla o el ángulo no es válido
     */
    void rotarIzquierda(int grados) throws Exception;
    
    /**
     * Rota el dron a la derecha (en sentido horario).
     * 
     * <p>El dron girará en su lugar hacia la derecha. Si el dron está mirando al norte,
     * llamar a rotarDerecha(90) hará que mire al este.</p>
     * 
     * @param grados cuántos grados rotar (0-360)
     * @throws Exception si la rotación falla o el ángulo no es válido
     */
    void rotarDerecha(int grados) throws Exception;
    
    /**
     * Hace que el dron se mantenga en su lugar durante cierto tiempo.
     * 
     * <p>El dron permanecerá en su posición actual sin moverse. Esto es útil
     * cuando quieres pausar entre movimientos o esperar a que algo suceda.</p>
     * 
     * @param duracionSegundos cuánto tiempo mantener, en segundos
     * @throws Exception si el mantenimiento falla o la duración no es válida
     */
    void mantener(double duracionSegundos) throws Exception;
    
    // ========== Métodos de Control de Velocidad ==========
    
    /**
     * Establece la velocidad de movimiento del dron a un valor exacto.
     * 
     * <p>Este método establece qué tan rápido se mueve el dron. Algunos drones te permiten establecer una
     * velocidad exacta en metros por segundo o centímetros por segundo. Después de llamar
     * a este método, todos los comandos de movimiento usarán esta velocidad.</p>
     * 
     * <p><strong>Nota:</strong> No todos los drones admiten establecer velocidades exactas.
     * Algunos drones solo admiten niveles de velocidad (ver establecerNivelVelocidad).</p>
     * 
     * @param velocidad el valor de velocidad deseado
     * @param unidad la unidad para la velocidad ("m/s", "cm/s", etc.)
     * @throws Exception si el dron no admite control de velocidad exacta o
     *                   el valor de velocidad no es válido
     */
    void establecerVelocidad(double velocidad, String unidad) throws Exception;
    
    /**
     * Establece la velocidad de movimiento del dron usando un sistema de niveles.
     * 
     * <p>Este método establece qué tan rápido se mueve el dron usando niveles como 1, 2, 3, etc.
     * Algunos drones usan niveles en lugar de velocidades exactas. El nivel 1 podría ser lento, el nivel 5
     * podría ser medio, y el nivel 10 podría ser rápido. Consulta la documentación de tu dron
     * para saber qué significan los niveles.</p>
     * 
     * <p><strong>Nota:</strong> No todos los drones admiten niveles de velocidad.
     * Algunos drones solo admiten velocidades exactas (ver establecerVelocidad).</p>
     * 
     * @param nivel el nivel de velocidad (el rango depende del dron, a menudo 1-10)
     * @throws Exception si el dron no admite niveles de velocidad o
     *                   el nivel está fuera de rango
     */
    void establecerNivelVelocidad(int nivel) throws Exception;
    
    /**
     * Obtiene la velocidad de movimiento actual del dron.
     * 
     * <p>Este método devuelve qué tan rápido está configurado actualmente el dron para moverse.</p>
     * 
     * @param unidad la unidad en la que quieres la velocidad ("m/s", "cm/s", etc.)
     * @return la velocidad actual en la unidad solicitada
     * @throws Exception si el dron no admite reporte de velocidad o no
     *                   usa valores de velocidad exactos
     */
    double obtenerVelocidad(String unidad) throws Exception;
    
    /**
     * Obtiene el nivel de velocidad actual del dron.
     * 
     * <p>Este método devuelve el nivel de velocidad actual si el dron usa un sistema
     * de niveles para el control de velocidad.</p>
     * 
     * @return el nivel de velocidad actual
     * @throws Exception si el dron no admite niveles de velocidad
     */
    int obtenerNivelVelocidad() throws Exception;
    
    // ========== Métodos de Telemetría ==========
    
    /**
     * Obtiene el nivel de batería actual del dron.
     * 
     * <p>Devuelve el porcentaje de batería restante. ¡Siempre revisa la batería antes
     * de volar! Si la batería se agota demasiado, el dron podría aterrizar automáticamente o
     * perder la conexión.</p>
     * 
     * @return el porcentaje de batería (0-100)
     * @throws Exception si no se puede leer el nivel de batería
     */
    int obtenerBateria() throws Exception;
    
    /**
     * Obtiene la altura actual del dron sobre el suelo.
     * 
     * <p>Este método devuelve qué tan alto está volando el dron. La mayoría de los drones miden
     * la altura desde donde despegaron, no desde el nivel del mar.</p>
     * 
     * @param unidad la unidad en la que quieres la altura ("m", "cm", "in", etc.)
     * @return la altura actual en la unidad solicitada
     * @throws Exception si no se puede leer la altura
     */
    double obtenerAltura(String unidad) throws Exception;
    
    /**
     * Obtiene la temperatura actual del dron.
     * 
     * <p>Devuelve la temperatura de los componentes internos del dron. Si el dron
     * se calienta demasiado, podría apagarse para protegerse.</p>
     * 
     * @return la temperatura en grados Celsius
     * @throws Exception si no se puede leer la temperatura
     */
    double obtenerTemperatura() throws Exception;
    
    /**
     * Obtiene el estado de vuelo actual del dron.
     * 
     * <p>Devuelve una descripción de lo que el dron está haciendo en este momento. Ejemplos:
     * "manteniéndose", "volando", "aterrizando", "en el suelo".</p>
     * 
     * @return una cadena que describe el estado de vuelo
     * @throws Exception si no se puede leer el estado de vuelo
     */
    String obtenerEstadoVuelo() throws Exception;
    
    /**
     * Obtiene el estado de movimiento actual del dron.
     * 
     * <p>Devuelve una descripción de cómo se está moviendo el dron. Ejemplos: "adelante",
     * "rotando", "estacionario", "atrás".</p>
     * 
     * @return una cadena que describe el estado de movimiento
     * @throws Exception si no se puede leer el estado de movimiento
     */
    String obtenerEstadoMovimiento() throws Exception;
    
    // ========== Métodos de Video y Cámara ==========
    
    /**
     * Inicia la transmisión de video del dron.
     * 
     * <p>Si el dron tiene una cámara, este método enciende la transmisión de video para que puedas
     * ver lo que el dron ve. No todos los drones tienen cámaras.</p>
     * 
     * @throws Exception si el dron no tiene una cámara o no se puede iniciar
     *                   la transmisión de video
     */
    void iniciarVideo() throws Exception;
    
    /**
     * Detiene la transmisión de video del dron.
     * 
     * <p>Apaga la transmisión de video de la cámara del dron. Esto puede ayudar a ahorrar
     * batería y ancho de banda.</p>
     * 
     * @throws Exception si no se puede detener la transmisión de video
     */
    void detenerVideo() throws Exception;
}
