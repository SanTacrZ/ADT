use std::fmt;
use std::time::{SystemTime, Duration};

#[derive(Debug, Clone, Copy, PartialEq, Eq, Hash)]
pub enum Category {
    Red,
    Aplicacion,
    Seguridad,
    Hardware,
}

impl fmt::Display for Category {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        let s = match self {
            Category::Red => "RED",
            Category::Aplicacion => "APLICACIÓN",
            Category::Seguridad => "SEGURIDAD",
            Category::Hardware => "HARDWARE",
        };
        write!(f, "{}", s)
    }
}

#[derive(Debug, Clone, Copy, PartialEq, Eq)]
pub enum State {
    Nuevo,
    Asignado,
    EnProgreso,
    Resuelto,
    Cerrado,
}

impl fmt::Display for State {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        let s = match self {
            State::Nuevo => "NUEVO",
            State::Asignado => "ASIGNADO",
            State::EnProgreso => "EN PROGRESO",
            State::Resuelto => "RESUELTO",
            State::Cerrado => "CERRADO",
        };
        write!(f, "{}", s)
    }
}

pub struct Client {
    pub id: u32,
    pub name: String,
    pub email: String,
    pub company: Option<String>,
    pub phone: Option<String>,
}

pub struct Technician {
    pub id: u32,
    pub name: String,
    pub specialty: Category,
    pub assigned_ticket_id: Option<u32>,
    pub is_available: bool,
}

impl Technician {
    pub fn new(id: u32, name: String, specialty: Category) -> Self {
        Self {
            id,
            name,
            specialty,
            assigned_ticket_id: None,
            is_available: true,
        }
    }

    pub fn can_accept_ticket(&self) -> bool {
        self.is_available && self.assigned_ticket_id.is_none()
    }
}

pub struct Ticket {
    pub id: u32,
    pub description: String,
    pub category: Category,
    pub state: State,
    pub client_id: u32,
    pub technician_id: Option<u32>,
    pub created_at: SystemTime,
    pub resolved_at: Option<SystemTime>,
}

impl Ticket {
    pub fn new(id: u32, description: String, category: Category, client_id: u32) -> Self {
        Self {
            id,
            description,
            category,
            state: State::Nuevo,
            client_id,
            technician_id: None,
            created_at: SystemTime::now(),
            resolved_at: None,
        }
    }

    pub fn time_elapsed(&self) -> Duration {
        let end = self.resolved_at.unwrap_or_else(SystemTime::now);
        end.duration_since(self.created_at).unwrap_or(Duration::from_secs(0))
    }
}
