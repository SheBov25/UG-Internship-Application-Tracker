UG Internship Application Tracker
A tracker for managing and monitoring internship applications, built as a JavaFX desktop app, a Vue 3 web app, and a Node.js mock REST API.

Prerequisites
1.	Java JDK 21 or a compatible version
2.	Apache Maven
3.	Node.js and npm
4.	A Java IDE (e.g., IntelliJ IDEA)
5.	A web-development editor (e.g., Visual Studio Code)
6.	A modern web browser (e.g., Google Chrome)

Installation and Running

JavaFX desktop application
cd desktop-javafx
mvn clean javafx:run
Mock API
cd mock-api
node server.js
Runs locally on http://localhost:3000. Keep this terminal open.

Vue web application
cd web-vue
npm.cmd run dev

If npm works normally, npm run dev may be used instead. Open http://localhost:5173 once Vite starts.
Start-up Order
1.	Start the mock API: node server.js
2.	Start the Vue application: npm.cmd run dev
3.	Open the Vue application in the browser
Both the mock API and Vue terminals must remain running.

Troubleshooting
1.	Vue shows "Network Error" – the mock API is not running. Run node server.js in the mock-api folder, then refresh the browser.
2.	PowerShell blocks npm run dev – use npm.cmd run dev instead.
3.	JavaFX application does not open – run mvn clean javafx:run from inside the desktop-javafx folder.