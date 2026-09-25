# Progetto LaTazza
**Progetto a cura di Francesco Giuseppino (Matricola: 6520241)**

Il progetto LaTazza è un'applicazione software sviluppata in Java per la gestione delle operazioni di un sistema di vendita o di un distributore. Partendo da uno scheletro di base fornito a scopo didattico, l'applicazione è stata estesa e refattorizzata seguendo principi di ingegneria del software, con un'architettura basata sul Data Access Object (DAO) pattern per gestire la persistenza dei dati tramite un database H2 embedded.

Il sistema gestisce diverse entità fondamentali del dominio applicativo, separando nettamente la logica di business dall'accesso ai dati:
* **Gestione della Cassa:** Monitoraggio e registrazione delle transazioni economiche (implementato tramite `CassaDAO` e `CassaDAOImpl`).
* **Gestione del Magazzino:** Controllo dell'inventario e delle scorte (gestito tramite `MagazzinoDAO` e `MagazzinoDAOImpl`).
* **Rifornimenti:** Tracciamento delle operazioni di approvvigionamento dei prodotti (tramite `RifornimentoDAO`).
* **Vendite:** Registrazione delle operazioni di acquisto da parte dei clienti (tramite `VenditaDAO`).

Questa modularità garantisce un codice manutenibile e facilmente testabile.

## Tecnologie Utilizzate
* **Linguaggio:** Java
* **Database:** H2 (Embedded)
* **Build Automation & Testing:** Gradle, JUnit
* **Modellazione:** Visual Paradigm (per i diagrammi di classe e di componente)
* **Code Quality:** DesigniteJava (per l'analisi statica del codice e il controllo degli smell)

## Istruzioni per l'Installazione e l'Esecuzione

* **Build del progetto:** 
  ./gradlew build
* **Esecuzione dell'applicazione:**
  ./gradlew run
* **Esecuzione dei test:**
  ./gradlew test


  ____________________________

  # LaTazza Project
**Project by Francesco Giuseppino (Student ID: 6520241)**

The LaTazza project is a software application developed in Java for managing the operations of a sales or vending system[cite: 5]. Starting from a basic skeleton provided for educational purposes, the application has been extended and refactored following software engineering principles, with an architecture based on the Data Access Object (DAO) pattern to manage data persistence through an embedded H2 database.

The system manages several fundamental entities of the application domain, clearly separating business logic from data access:
* **Cash Management:** Monitoring and recording of financial transactions (implemented via `CassaDAO` and `CassaDAOImpl`).
* **Warehouse Management:** Inventory and stock control (managed via `MagazzinoDAO` and `MagazzinoDAOImpl`).
* **Restockings:** Tracking of product supply operations (via `RifornimentoDAO`).
* **Sales:** Recording of purchase operations by customers (via `VenditaDAO`).

This modularity ensures maintainable and easily testable code.

## Technologies Used
* **Language:** Java
* **Database:** H2 (Embedded)
* **Build Automation & Testing:** Gradle, JUnit
* **Modeling:** Visual Paradigm (for class and component diagrams)
* **Code Quality:** DesigniteJava (for static code analysis and code smell checking)

## Installation and Execution Instructions
* **Project build:** 
  ./gradlew build
* **Application execution:** 
  ./gradlew run
* **Test execution:** 
  ./gradlew test
