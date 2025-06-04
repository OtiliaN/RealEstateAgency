# Real Estate Agency Management System

## Description

The **Real Estate Agency** application is a desktop solution developed in Java for managing a real estate agency. The application implements a layered architecture with the MVC (Model-View-Controller) pattern and offers complete functionality for real estate property management.

## Architecture

The application uses a **layered architecture** with the following tiers:

- **Presentation Layer**: User interface implemented with JavaFX and FXML
- **Business Logic Layer**: Business logic and MVC controllers
- **Data Access Layer**: Data access through Hibernate ORM

### Design Patterns Implemented

- **MVC Pattern**: Model-View-Controller architecture
- **Repository Pattern**: Data access abstraction with interfaces and implementations
- **Observer Pattern**: Real-time updates across multiple client sessions
- **Service Layer Pattern**: Business logic encapsulation

### MVC Pattern Components

- **Model**: Data entities (Admin, Agent, Client, Property)
- **View**: JavaFX interfaces for each user type
- **Controller**: Business logic controllers handling user interactions

## Technologies Used

- **Java**: Core programming language
- **JavaFX**: User interface framework
- **Hibernate ORM**: Object-Relational Mapping for database interaction
- **SQLite**: Relational database 
- **Gradle**: Build tool
- **Observer Pattern**: Real-time synchronization between clients/agents

## User Roles

### 1. Client

**Account Management**
- Account Creation: Sign up with unique username and secure password
- Authentication: Login/logout functionality

**Property Search & Filtering**
- Browse all available properties
- Filter by city (Oraș)
- Filter by neighborhood (Cartier)
- Filter by transaction type (Tip Tranzacție: RENT/SALE)
- Filter by property type (Tip Proprietate: APARTMENT/HOUSE/COMMERCIAL)
- Price range filtering (minimum and maximum)

**Real-time Features**
- Property Viewing: View all properties in the main window
- Real-time Updates: Automatic property list refresh when changes are made by agents

![Client Interface](https://github.com/user-attachments/assets/83f72558-8457-4a67-94bd-e50429fa4a31)

![Client Search](https://github.com/user-attachments/assets/c78798c9-74df-46e1-9d3f-f016b70511d8)

![Client Properties](https://github.com/user-attachments/assets/cfced8d9-af89-4999-9842-b022eaa61141)

### 2. Agent

**Authentication**
- Secure login/logout system

**Property Management**
- **Create**: Add new properties with complete details
- **Read**: View all properties in the system
- **Update**: Modify existing property information
- **Delete**: Remove properties from listings

**Real-time Synchronization**
- Changes instantly reflected across all connected clients

![Agent Interface](https://github.com/user-attachments/assets/d832547f-bb7d-4984-91ab-d047f5f9cf58)

![Agent Management](https://github.com/user-attachments/assets/2f85c524-6b6d-4910-9524-bc63ec4824b4)

### 3. Admin

**System Administration**
- Authentication: Secure login/logout system
- Administrative access through dedicated interface
- User Management: Manage agents and system configuration

![Admin Interface](https://github.com/user-attachments/assets/d3e6d908-97af-48af-8f03-bd8092f026b9)

![Admin Dashboard](https://github.com/user-attachments/assets/dce2a648-9cca-48f5-99c7-8865eeeeeb49)

## Main Features

### Authentication & Security
- **Secure Login System**: Separate authentication and different permissions for Clients, Agents and Admins
- **Password Security**: All passwords are hashed and stored securely in the database
- **User Registration**: New client registration with unique username validation

### Real-time Synchronization
- **Observer Pattern Implementation**: Multiple clients can be connected simultaneously
- **Automatic Updates**: When an agent modifies properties, all connected clients see changes immediately
- **No Manual Refresh Required**: Seamless user experience with live data updates

### Advanced Property Filtering
- **Multi-criteria Search**: Combine multiple filters for precise property matching
- **Dynamic Filtering**: Real-time filter application with immediate results
- **Price Range Selection**: Flexible minimum and maximum price filtering

## Database Structure

The application uses a relational database with the following main entities:

- **Users** (Clients and Agents)
- **Properties** 
- **User Roles and Permissions**

All database interactions are handled through **Hibernate ORM**, ensuring:
- Type-safe database operations
- Automatic SQL generation
- Object-relational mapping
- Transaction management

##  Installation & Setup

### Prerequisites
- Java JDK 11 or higher
- Gradle (or use included Gradle wrapper)
- SQLite (embedded, no separate installation required)

### Running the Application
```bash
# Clone the repository
git clone https://github.com/OtiliaN/RealEstateAgency.git

# Navigate to project directory
cd RealEstateAgency

# Build the project
./gradlew build

# Run the application
./gradlew run
```
### Database Setup
- **Automatic Configuration**: SQLite database is created automatically on first run
- **Schema Generation**: Hibernate handles database schema creation
- **Sample Data**: Application may include sample properties for testing

## Usage Flow

### For Clients
1. Launch application → Main window opens
2. New users: Click "Sign Up" → Create account with unique username
3. Existing users: Login with credentials
4. Browse and search properties
5. Logout when finished

### For Agents
1. Click "Agent" button from main window
2. Login with agent credentials
3. Access property management dashboard
4. Perform CRUD operations on properties
5. Logout when finished

### For Admins
1. Click "Admin" button from main window
2. Login with admin credentials
3. Access administrative functions
4. Manage system configuration
5. Logout when finished




