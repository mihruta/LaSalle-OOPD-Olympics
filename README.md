# 🏅 LA28 Games Lab

**Olympic Games Simulator | Object-Oriented Programming and Design**

A university Java project developed for the Object-Oriented Programming and Design course at **La Salle – Universitat Ramon Llull**.

LA28 Games Lab is a console application designed to manage delegations, athletes, and sports competitions for the Los Angeles 2028 Olympic Games.

## 📋 Project Overview

The application is designed to provide the following functionality:

- Manage country delegations and their medal counts.
- Register, search, and delete athletes.
- Load and manage sports competitions.
- Simulate competition results based on athlete attributes and a luck factor.
- Calculate Olympic medal standings.
- Store and retrieve data using local JSON files.

## 🛠️ Technologies

- **Language:** Java 21
- **Build Tool:** Maven
- **JSON Library:** Gson
- **IDE:** IntelliJ IDEA
- **Design:** UML Class Diagram
- **Version Control:** Git & GitHub

## 📂 Project Structure

```text
src/
├── business/
│   ├── model/
│   └── *Manager.java
├── persistence/
│   ├── exceptions/
│   └── *FileManager.java
├── presentation/
└── Main.java

data/
├── delegations.json
├── athletes.json
└── competitions.json
```

The project is organized into business, persistence, and presentation layers.

## 🚀 Project Phases

| Phase | Description |
|---|---|
| Phase 1 | UML design and software architecture |
| Phase 2 | Initial Java implementation and local persistence |
| Phase 3 | Design of extended system functionality |
| Phase 4 | Implementation of the extended functionality |

**Current development stage:** Phase 2 — Implementation

## 🔀 Development Workflow

The project uses separate branches for development:

- `main` — main project branch.
- `dev` — integration branch for approved changes.
- `feature/*` — individual development tasks.

All feature changes are submitted through Pull Requests and reviewed before merging into `dev`.

## 📊 Project Management

Development tasks, Pull Requests, and progress across all phases are tracked using GitHub Projects.

**[View Project Management Board](https://github.com/users/mihruta/projects/1)**

Each project phase has its own board and milestone.

## 👥 Contributors

Developed as a university team project by:

- [mihruta](https://github.com/mihruta)
- [sviatoslavadamiv](https://github.com/sviatoslavadamiv)

## 📌 Development Status

The project is currently under development. Features are being implemented and integrated according to the project statement.

The final implementation will be completed during Phase 4.
