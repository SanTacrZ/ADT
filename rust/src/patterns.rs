use crate::models::{Ticket, Technician, Category};
use std::collections::HashMap;

/// Strategy Pattern: Define a trait for assignment algorithms.
pub trait AssignmentStrategy {
    fn assign_technician(
        &self,
        ticket: &Ticket,
        technicians: &HashMap<u32, Technician>,
    ) -> Option<u32>;
}

/// Concrete Strategy: Assign by specialty (first match).
pub struct BySpecialty;
impl AssignmentStrategy for BySpecialty {
    fn assign_technician(
        &self,
        ticket: &Ticket,
        technicians: &HashMap<u32, Technician>,
    ) -> Option<u32> {
        technicians
            .values()
            .find(|t| t.specialty == ticket.category && t.can_accept_ticket())
            .map(|t| t.id)
    }
}

/// Concrete Strategy: Round-Robin assignment.
/// In a real scenario, this would track the last assigned technician.
pub struct RoundRobin {
    pub last_id: std::cell::Cell<u32>,
}
impl AssignmentStrategy for RoundRobin {
    fn assign_technician(
        &self,
        ticket: &Ticket,
        technicians: &HashMap<u32, Technician>,
    ) -> Option<u32> {
        let mut sorted_techs: Vec<_> = technicians.values().collect();
        sorted_techs.sort_by_key(|t| t.id);

        if sorted_techs.is_empty() {
            return None;
        }

        let last = self.last_id.get();
        let next_tech = sorted_techs
            .iter()
            .find(|t| t.id > last && t.can_accept_ticket())
            .or_else(|| sorted_techs.iter().find(|t| t.can_accept_ticket()));

        if let Some(tech) = next_tech {
            self.last_id.set(tech.id);
            Some(tech.id)
        } else {
            None
        }
    }
}

/// Observer Pattern: Define a trait for system event listeners.
pub trait TicketObserver {
    fn on_ticket_created(&self, ticket: &Ticket);
    fn on_ticket_assigned(&self, ticket: &Ticket, tech_name: &str);
    fn on_ticket_resolved(&self, ticket: &Ticket);
}

/// Concrete Observer: Logs events to the console.
pub struct LoggerObserver {
    pub name: String,
}
impl TicketObserver for LoggerObserver {
    fn on_ticket_created(&self, ticket: &Ticket) {
        println!(
            "[{}] [EVENTO CREACIÓN] Ticket #{} creado: {}",
            self.name, ticket.id, ticket.description
        );
    }

    fn on_ticket_assigned(&self, ticket: &Ticket, tech_name: &str) {
        println!(
            "[{}] [EVENTO ASIGNACIÓN] Ticket #{} asignado a: {}",
            self.name, ticket.id, tech_name
        );
    }

    fn on_ticket_resolved(&self, ticket: &Ticket) {
        println!(
            "[{}] [EVENTO RESOLUCIÓN] Ticket #{} resuelto en {} minutos",
            self.name, ticket.id, ticket.time_elapsed().as_secs() / 60
        );
    }
}

/// Concrete Observer: Collects statistics.
pub struct StatsObserver {
    pub created_count: std::cell::Cell<u32>,
    pub assigned_count: std::cell::Cell<u32>,
    pub resolved_count: std::cell::Cell<u32>,
}
impl TicketObserver for StatsObserver {
    fn on_ticket_created(&self, _ticket: &Ticket) {
        self.created_count.set(self.created_count.get() + 1);
    }

    fn on_ticket_assigned(&self, _ticket: &Ticket, _tech_name: &str) {
        self.assigned_count.set(self.assigned_count.get() + 1);
    }

    fn on_ticket_resolved(&self, _ticket: &Ticket) {
        self.resolved_count.set(self.resolved_count.get() + 1);
    }
}

impl StatsObserver {
    pub fn new() -> Self {
        Self {
            created_count: std::cell::Cell::new(0),
            assigned_count: std::cell::Cell::new(0),
            resolved_count: std::cell::Cell::new(0),
        }
    }

    pub fn display(&self) {
        println!("\n=== ESTADÍSTICAS DEL OBSERVADOR ===");
        println!("  • Tickets creados:  {}", self.created_count.get());
        println!("  • Tickets asignados: {}", self.assigned_count.get());
        println!("  • Tickets resueltos: {}", self.resolved_count.get());
        println!("===================================\n");
    }
}
