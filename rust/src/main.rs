mod models;
mod patterns;
mod system;

use models::{Category, Client, Technician, State};
use patterns::{BySpecialty, RoundRobin, LoggerObserver, StatsObserver};
use system::TicketSystem;

fn main() {
    println!("╔═══════════════════════════════════════════════════════════════╗");
    println!("║   DEMOSTRACIÓN AVANZADA RUST - FUNCIONAL Y OPTIMIZADA         ║");
    println!("╚═══════════════════════════════════════════════════════════════╝\n");

    let mut system = TicketSystem::new();

    // 1. Configurar Observadores
    let logger = Box::new(LoggerObserver { name: "LOGGER".to_string() });
    let stats = Box::new(StatsObserver::new());
    
    // Necesitamos una referencia a stats para mostrar los resultados al final.
    // En una app real usaríamos RefCell o similar, para el demo lo manejaremos simple.
    // Usaremos un truco: el observer de stats imprimirá al ser destruido o lo llamaremos manual si fuera posible.
    // Para este demo, simplemente creamos un sistema con observadores.
    system.add_observer(logger);
    // system.add_observer(stats); // Stats se mostraría al final si tuviéramos acceso.

    // 2. Agregar Técnicos
    system.add_technician(Technician::new(10, "María Red".to_string(), Category::Red));
    system.add_technician(Technician::new(11, "Pedro Red".to_string(), Category::Red));
    system.add_technician(Technician::new(12, "Luis App".to_string(), Category::Aplicacion));

    // 3. Agregar Clientes
    system.add_client(Client {
        id: 100,
        name: "Juan Pérez".to_string(),
        email: "juan@test.com".to_string(),
        company: None,
        phone: None,
    });

    // 4. Ciclo de Vida de Tickets (Estrategia: Round Robin)
    let strategy = RoundRobin { last_id: std::cell::Cell::new(0) };
    
    println!("=== PROCESANDO TICKETS CON ESTRATEGIA ROUND-ROBIN ===\n");

    let descriptions = vec![
        "Problema red 1",
        "Problema red 2",
        "Problema red 3",
    ];

    for desc in descriptions {
        if let Ok(id) = system.create_ticket(desc.to_string(), Category::Red, 100) {
            if let Some(assigned_id) = system.assign_next_ticket(&strategy) {
                let tech_name = system.get_technician_name(assigned_id).unwrap_or_default();
                println!("  ✓ Ticket #{} asignado a: {}", id, tech_name);
            }
        }
    }

    println!("\n=== RESOLVIENDO TICKETS ===\n");
    // Simulamos la resolución de los tickets
    for id in 1..=3 {
        let _ = system.update_ticket_state(id, State::EnProgreso);
        let _ = system.update_ticket_state(id, State::Resuelto);
    }

    println!("{}", system.get_statistics());

    println!("\n╔═══════════════════════════════════════════════════════════════╗");
    println!("║         DEMOSTRACIÓN RUST COMPLETADA EXITOSAMENTE             ║");
    println!("╚═══════════════════════════════════════════════════════════════╝");
}
