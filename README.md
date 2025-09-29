# Hotel Digital Card System

A Spring Boot application for managing digital hotel key cards with QR code generation.

## Features

- Create digital hotel cards with amenities
- QR code generation for easy access
- Card validation and management
- Suspend/activate cards
- Guest-friendly web interface
- RESTful API

### Quick Start
Development

Run the Spring Boot app using Maven:
./mvnw spring-boot:run

or if you have Maven installed globally:
mvn spring-boot:run

###Production Build

Build a jar and run it:

./mvnw clean package -DskipTests
java -jar target/digital-card-0.0.1-SNAPSHOT.jar


## API Endpoints

- `POST /api/cards/create` - Create new cards
- `GET /api/cards/{sessionId}` - Get card details
- `GET /api/cards` - Get all cards
- `PUT /api/cards/{sessionId}/suspend` - Suspend/activate card
- `GET /api/cards/{sessionId}/qrcode` - Generate QR code

## Web Interface

- `/home.html` - Main dashboard
- `/create-card.html` - Create new cards
- `/view-card.html` - View card by session ID
- `/card.html?sessionId=xxx` - Guest card view

## Environment Variables

- `PORT` - Server port (default: 9090)
- `ADMIN_USERNAME` - Admin username (default: admin)
- `ADMIN_PASSWORD` - Admin password (default: admin123)

## Database

- Development: H2 in-memory database
- Production: Configurable via application-prod.properties

## Security

- Basic HTTP authentication for admin endpoints
- Public access for card viewing and API endpoints
- CSRF disabled for API usage
