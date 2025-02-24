# Rubrica Telefonica Giuffrè

Progetto Java che permette la gestione di una rubrica telefonica con autenticazione e database MySQL.

## Tecnologie Usate

- Java 11
- Swing (GUI)
- MySQL (Database)
- JDBC (Connessione al DB)
- Maven (Gestione delle dipendenze e build)

## Funzionalità Principali

- Login utente (autenticazione semplice)
- Gestione contatti (CRUD completo)
- Interfaccia grafica intuitiva
- Validazioni chiare degli input utente
- Configurazione DB parametrizzabile esternamente



## Come avviare il progetto

### Prerequisiti
- Java JDK 11+
- MySQL (o Docker)

### Configurazione del Database
Crea il DB e importa lo script fornito (`schema_database.sql`)

Modifica il file `credenziali_database.properties`:

- db.url=jdbc:mysql://localhost:tua_porta/rubrica_db
- db.username=tuo_utente
- db.password=tua_password

