# Quad Solutions Quiz - Docker Setup

This project consists of a quiz frontend built in Angular and a backend API built with Spring Boot. The following guide explains how to set up both the frontend and backend in Docker containers and how to resolve potential issues with the backend host.

## Prerequisites

Ensure you have the following installed on your system:
- Docker
- Docker Compose

## Setup

### 1. Clone the repository

Clone this repository to your local machine:

```bash
git clone https://github.com/woutervanboekel/Quad-Solutions-Quiz.git
cd Quad-Solutions-Quiz
```

### 2. Build the Docker images
Navigate to the root directory of the project where docker-compose.yml is located.

Use Docker Compose to build the containers:

```bash
docker-compose build
```

### 3. Start the containers
After building the images, you can start the containers with the following command:
```bash
docker-compose up
```
This will start both the backend and frontend services. The backend will run at http://quiz-backend:8080 (inside the Docker network) and the frontend will be available at http://localhost:80 (on your local machine).

### 4. Troubleshooting: Resolving the Backend Host
If the backend is not resolved correctly (e.g., quiz-backend hostname), you may need to add it manually to your /etc/hosts file.

To do so, follow these steps:

1. Open your /etc/hosts file with an editor:
```bash
sudo nano/etc/hosts
```
2. Add the following entry at the end of the file:
```bash
127.0.0.1 quiz-backend
```
3. Save the file and exit the text editor


## Stopping the Containers
To stop the containers use:
```bash
docker-compose down
```

### Notes
- The backend (Spring Boot) runs on port 8080 inside the Docker network.
- The frontend (Angular) runs on port 80 on your local machine.
- Both frontend and backend services are connected through the quiz-network Docker network.
- If you encounter issues with cross-origin requests, make sure the backend allows requests from the frontend.
