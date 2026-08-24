# Setup and Run Guide

## Required software
- OpenJDK 21 or later
- Maven 3.9 or later
- Node.js 20 or later and npm
- IntelliJ IDEA Community or another Java IDE
- Visual Studio Code (recommended for Vue)

## 1. Run the mock API
```bash
cd mock-api
node server.js
```
The API starts at http://localhost:3000. Test http://localhost:3000/requests in a browser.

## 2. Run the Vue application
Open a second terminal:
```bash
cd web-vue
npm install
npm run dev
```
Open the local URL shown by Vite, normally http://localhost:5173.

## 3. Run the JavaFX application
Open a third terminal:
```bash
cd desktop-javafx
mvn clean javafx:run
```
The JavaFX client begins with in-memory sample data. Students may extend it to call the API or persist data locally.

## Common problems
- Port 3000 busy: stop the other service or change PORT in `mock-api/server.js` and update the Axios base URL.
- `mvn` not found: install Maven and add its `bin` folder to PATH.
- Blank Vue page: check the terminal and browser console for import errors.
- CORS errors: ensure the supplied mock API is running; it already returns permissive CORS headers.
