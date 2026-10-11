# ZingFoodie Campus — Full-Stack Project

Campus food ordering web app: HTML/CSS/JavaScript frontend, Java 21 + Spring Boot backend, SQLite database, Firebase Authentication for student/teacher/faculty accounts, server-generated QR receipts, staff dashboard, stock management, combos, and rewards.

## Included features
- Firebase email/password registration and login for students, teachers, and faculty.
- Backend verifies Firebase ID tokens using Firebase Admin SDK before creating an app session. Firebase client configuration is public web configuration; service-account credentials must remain secret.
- Separate staff login managed by the administrator; public registration cannot create a staff account.
- Menu search/categories, campus stalls, combo meals, stock counts, cart and checkout.
- Orders saved in SQLite, status tracking, QR receipts, and incoming staff orders refresh every 5 seconds while the dashboard is open.
- Staff can update order status, manage menu items, replenish stock, and review sales reports.
- Rewards: 1 point per ₱10 spent (rounded down); redeem 100 points for ₱10 off.

## Firebase setup (required for Firebase login)
The project is configured to use the Firebase project `zingfoodie-18e4e` for the web client. In the Firebase Console:
1. Open **Authentication → Sign-in method** and enable **Email/Password**.
2. In **Authentication → Settings → Authorized domains**, add your Render hostname (for example `your-service.onrender.com`) if it is not already listed.
3. Open **Project settings → Service accounts** and generate a private key JSON for the Firebase Admin SDK. Keep this file private. Never upload it to GitHub or put it in frontend code.
4. In Render → your service → **Environment**, add `FIREBASE_SERVICE_ACCOUNT_JSON` and paste the complete service-account JSON as its value. Alternatively, set `FIREBASE_SERVICE_ACCOUNT_BASE64` to the base64 encoding of the JSON. Set only one of these variables.
5. Redeploy the service and check `/api/health`. If Firebase server credentials are missing, the regular menu may load, but Firebase registration/login will show a setup error.

If you use a different Firebase project, update `firebaseConfig` in `index.html` and ensure the service-account JSON belongs to that same project.

## Run locally
Requirements: Java 21 and Maven 3.9+.

```bash
export STAFF_USERNAME=campusadmin
export STAFF_PASSWORD='Use-A-Long-Unique-Password'
export FIREBASE_SERVICE_ACCOUNT_JSON="$(cat /secure/path/service-account.json)"
mvn spring-boot:run
```

Open `http://localhost:8080`. Do not open `index.html` directly; use the Spring Boot server.

## Staff account
Set `STAFF_USERNAME` and a strong `STAFF_PASSWORD` in the environment before first startup. The app creates the staff account on startup if it does not exist and the password is set. Staff sign in using the staff username (not an email) and password. Do not register staff through the public account form.

## GitHub + Render deployment
1. Upload the contents of this folder to the root of your GitHub repository. Keep `pom.xml`, `Dockerfile`, `render.yaml`, `src/`, `index.html`, and `zingfoodie.css` at the repository root.
2. In Render, create a Blueprint from the repository.
3. Set `STAFF_PASSWORD` to a strong unique password.
4. Set `FIREBASE_SERVICE_ACCOUNT_JSON` using the private JSON from Firebase Console. Never commit it to GitHub.
5. Deploy, then open `/api/health` to check server status.
6. The Render blueprint uses a persistent disk for SQLite and a paid Starter service. Review Render's current pricing before deploying.

## Notes
- SQLite stores user profiles, orders, stock, and loyalty points; Firebase Authentication handles email/password identity. Firestore is not required.
- Existing SQLite user tables are migrated to include Firebase UID/email columns. Existing staff login remains separate.
- The staff incoming-order notification is a dashboard notification while the staff dashboard is open; it is not a push notification when the dashboard is closed.
- Test checkout, cancellation, staff role restrictions, and rewards in a test deployment before using real campus data.
