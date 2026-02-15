use crate::models::{Ticket, Technician, Client, Category, State};
use crate::patterns::{AssignmentStrategy, TicketObserver};
use std::collections::{HashMap, VecDeque};

pub struct TicketSystem {
    tickets: HashMap<u32, Ticket>,
    technicians: HashMap<u32, Technician>,
    clients: HashMap<u32, Client>,
    pending_queue: VecDeque<u32>,
    next_ticket_id: u32,
    observers: Vec<Box<dyn TicketObserver>>,
}

impl TicketSystem {
    pub fn new() -> Self {
        Self {
            tickets: HashMap::new(),
            technicians: HashMap::new(),
            clients: HashMap::new(),
            pending_queue: VecDeque::new(),
            next_ticket_id: 1,
            observers: Vec::new(),
        }
    }

    pub fn add_technician(&mut self, tech: Technician) {
        self.technicians.insert(tech.id, tech);
    }

    pub fn add_client(&mut self, client: Client) {
        self.clients.insert(client.id, client);
    }

    pub fn add_observer(&mut self, observer: Box<dyn TicketObserver>) {
        self.observers.push(observer);
    }

    pub fn create_ticket(
        &mut self,
        description: String,
        category: Category,
        client_id: u32,
    ) -> Result<u32, String> {
        if !self.clients.contains_key(&client_id) {
            return Err(format!("Cliente no encontrado: {}", client_id));
        }

        let id = self.next_ticket_id;
        self.next_ticket_id += 1;

        let ticket = Ticket::new(id, description, category, client_id);
        
        // Notify observers
        for obs in &self.observers {
            obs.on_ticket_created(&ticket);
        }

        self.tickets.insert(id, ticket);
        self.pending_queue.push_back(id);
        
        Ok(id)
    }

    pub fn assign_next_ticket(&mut self, strategy: &dyn AssignmentStrategy) -> Option<u32> {
        let ticket_id = *self.pending_queue.front()?;
        let ticket = self.tickets.get(&ticket_id)?;

        if let Some(tech_id) = strategy.assign_technician(ticket, &self.technicians) {
            self.pending_queue.pop_front();
            
            // Link technician to ticket
            let ticket = self.tickets.get_mut(&ticket_id)?;
            ticket.technician_id = Some(tech_id);
            ticket.state = State::Asignado;
            
            // Link ticket to technician
            let tech = self.technicians.get_mut(&tech_id)?;
            tech.assigned_ticket_id = Some(ticket_id);
            tech.is_available = false;
            let tech_name = tech.name.clone();

            // Notify observers
            for obs in &self.observers {
                obs.on_ticket_assigned(ticket, &tech_name);
            }

            Some(ticket_id)
        } else {
            None
        }
    }

    pub fn update_ticket_state(&mut self, ticket_id: u32, new_state: State) -> Result<(), String> {
        let ticket = self.tickets.get_mut(&ticket_id)
            .ok_or_else(|| format!("Ticket no encontrado: {}", ticket_id))?;
        
        // Simple state machine validation
        match (ticket.state, new_state) {
            (State::Asignado, State::EnProgreso) => ticket.state = new_state,
            (State::EnProgreso, State::Resuelto) => {
                ticket.state = new_state;
                ticket.resolved_at = Some(std::time::SystemTime::now());
                
                // Free the technician
                if let Some(tech_id) = ticket.technician_id {
                    if let Some(tech) = self.technicians.get_mut(&tech_id) {
                        tech.assigned_ticket_id = None;
                        tech.is_available = true;
                    }
                }

                // Notify observers
                for obs in &self.observers {
                    obs.on_ticket_resolved(ticket);
                }
            },
            (State::Resuelto, State::Cerrado) => ticket.state = new_state,
            _ => return Err(format!("Transición de estado inválida: {} -> {}", ticket.state, new_state)),
        }
        
        Ok(())
    }

    pub fn get_statistics(&self) -> String {
        let total = self.tickets.len();
        let pending = self.pending_queue.len();
        let en_progreso = self.tickets.values().filter(|t| t.state == State::EnProgreso).count();
        let resueltos = self.tickets.values().filter(|t| t.state == State::Resuelto).count();
        let disponibles = self.technicians.values().filter(|t| t.is_available).count();

        format!(
            "=== ESTADÍSTICAS DEL SISTEMA (RUST) ===\n\
             Total de tickets:      {}\n\
             Tickets pendientes:    {}\n\
             Tickets en progreso:   {}\n\
             Tickets resueltos:     {}\n\
             Técnicos disponibles:  {}\n\
             =======================================",
            total, pending, en_progreso, resueltos, disponibles
        )
    }

    // Helper for demo
    pub fn get_technician_name(&self, id: u32) -> Option<String> {
        self.technicians.get(&id).map(|t| t.name.clone())
    }
}
