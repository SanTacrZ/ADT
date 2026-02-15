/*
 * Taller Práctico 01 - Sistema de Gestión de Tickets
 * Estructuras de Datos y Algoritmos - 2026-10
 *
 * Integrantes:
 * - [Nombre Completo 1] - ID: [ID1]
 * - [Nombre Completo 2] - ID: [ID2]
 * - [Nombre Completo 3] - ID: [ID3]
 */

package ticketsystem.adt;

import ticketsystem.interfaces.Identificable;

/**
 * ADT Cliente - Representa un cliente que reporta tickets.
 * 
 * Implementa Identificable para POLIMORFISMO: permite tratar clientes
 * junto con otras entidades identificables de forma uniforme.
 * 
 * Invariantes:
 * - El ID debe ser único
 * - El email debe tener formato válido
 * - El nombre no puede estar vacío
 */
public class Cliente implements Identificable {

    private final int id;
    private String nombre;
    private String email;
    private String empresa;
    private String telefono;

    /**
     * Constructor para crear un nuevo cliente.
     * 
     * @param id     Identificador único del cliente
     * @param nombre Nombre completo del cliente
     * @param email  Correo electrónico del cliente
     * @throws IllegalArgumentException si el nombre está vacío o el email es
     *                                  inválido
     */
    public Cliente(int id, String nombre, String email) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (!esEmailValido(email)) {
            throw new IllegalArgumentException("El email debe tener formato válido");
        }

        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.empresa = null;
        this.telefono = null;
    }

    /**
     * Constructor completo con todos los atributos opcionales.
     * 
     * @param id       Identificador único del cliente
     * @param nombre   Nombre completo del cliente
     * @param email    Correo electrónico del cliente
     * @param empresa  Nombre de la empresa (opcional)
     * @param telefono Número de contacto (opcional)
     */
    public Cliente(int id, String nombre, String email, String empresa, String telefono) {
        this(id, nombre, email);
        this.empresa = empresa;
        this.telefono = telefono;
    }

    /**
     * Valida el formato de un email.
     * 
     * @param email Email a validar
     * @return true si el email tiene formato válido, false en caso contrario
     */
    private boolean esEmailValido(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        // Validación simple: debe contener @ y al menos un punto después del @
        int atIndex = email.indexOf('@');
        if (atIndex <= 0) {
            return false;
        }
        int dotIndex = email.indexOf('.', atIndex);
        return dotIndex > atIndex + 1 && dotIndex < email.length() - 1;
    }

    // Getters

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getEmpresa() {
        return empresa;
    }

    public String getTelefono() {
        return telefono;
    }

    // Setters

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        if (!esEmailValido(email)) {
            throw new IllegalArgumentException("El email debe tener formato válido");
        }
        this.email = email;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cliente{");
        sb.append("id=").append(id);
        sb.append(", nombre='").append(nombre).append('\'');
        sb.append(", email='").append(email).append('\'');
        if (empresa != null) {
            sb.append(", empresa='").append(empresa).append('\'');
        }
        if (telefono != null) {
            sb.append(", telefono='").append(telefono).append('\'');
        }
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Cliente cliente = (Cliente) obj;
        return id == cliente.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    /**
     * Método main con casos de prueba.
     */
    public static void main(String[] args) {
        System.out.println("=== Pruebas del ADT Cliente ===\n");

        // Test 1: Crear cliente válido
        Cliente c1 = new Cliente(1, "Juan Pérez", "juan.perez@example.com");
        assert c1.getId() == 1 : "Test 1 falló: ID incorrecto";
        assert c1.getNombre().equals("Juan Pérez") : "Test 1 falló: Nombre incorrecto";
        System.out.println("✓ Test 1 pasado: Cliente creado correctamente");

        // Test 2: Crear cliente con todos los atributos
        Cliente c2 = new Cliente(2, "María García", "maria@company.com", "TechCorp", "555-1234");
        assert c2.getEmpresa().equals("TechCorp") : "Test 2 falló: Empresa incorrecta";
        assert c2.getTelefono().equals("555-1234") : "Test 2 falló: Teléfono incorrecto";
        System.out.println("✓ Test 2 pasado: Cliente con atributos opcionales creado");

        // Test 3: Validación de email inválido
        try {
            new Cliente(3, "Pedro López", "email-invalido");
            assert false : "Test 3 falló: Debería lanzar excepción por email inválido";
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Test 3 pasado: Email inválido rechazado");
        }

        // Test 4: Validación de nombre vacío
        try {
            new Cliente(4, "", "test@example.com");
            assert false : "Test 4 falló: Debería lanzar excepción por nombre vacío";
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Test 4 pasado: Nombre vacío rechazado");
        }

        // Test 5: Modificar email válido
        c1.setEmail("nuevo.email@example.com");
        assert c1.getEmail().equals("nuevo.email@example.com") : "Test 5 falló: Email no actualizado";
        System.out.println("✓ Test 5 pasado: Email actualizado correctamente");

        // Test 6: toString
        String str = c1.toString();
        assert str.contains("Juan Pérez") : "Test 6 falló: toString no contiene nombre";
        assert str.contains("nuevo.email@example.com") : "Test 6 falló: toString no contiene email";
        System.out.println("✓ Test 6 pasado: toString funciona correctamente");

        System.out.println("\n=== Todas las pruebas de Cliente pasaron ===");
    }
}
