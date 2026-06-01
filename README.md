# 🏦 Bankify – Full Stack Banking Application (Backend)

A production-ready backend for a full-stack banking system built using Spring Boot.  
Provides secure REST APIs for authentication, transactions, and account management.

---

## 🚀 Live Demo

🔗 Frontend: https://banking-frontend-ghyetuhel-ssinghayu98s-projects.vercel.app


🔗 Backend API: https://banking-app-j821.onrender.com

## 📌 Features

### Authentication
- User Registration
- User Login
- Password Encryption
- Role Based Access

### Banking Operations
- Deposit Money
- Withdraw Money
- View Current Balance
- Transaction History

### Security
- Spring Security
- JWT Authentication
- CORS Configuration
- Secure REST APIs

### Deployment
- Frontend deployed on Vercel
- Backend deployed on Render
- MySQL Database hosted remotely

---

## 🛠️ Tech Stack

### Frontend
- React.js
- React Router
- JavaScript
- CSS

### Backend
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- JWT

### Database
- MySQL

### Deployment
- Vercel
- Render

### Documentation
- Swagger OpenAPI

---

## 📂 Project Structure

### Frontend

```text
src/
├── pages/
├── components/
├── api.js
├── App.js
└── index.js
```

### Backend

```text
src/main/java/com/bank/banking/app
├── controller
├── service
├── repository
├── model
├── dto
├── config
└── security
```

---

# 📸 Screenshots

## Login Page

![Login Screenshot](screenshots/login.png)

<img width="1635" height="883" alt="image" src="https://github.com/user-attachments/assets/140f1218-65fa-4682-b97d-332befc5ad54" />

## Signup Page

![Signup Screenshot](screenshots/signup.png)

<img width="1891" height="909" alt="image" src="https://github.com/user-attachments/assets/9633025e-ef23-40c8-9752-ae0457105e7f" />


## Dashboard

![Dashboard Screenshot](screenshots/dashboard.png)

<img width="1919" height="982" alt="image" src="https://github.com/user-attachments/assets/23531251-8c94-484f-a1d1-67ff4ab2adf3" />


## Deposit Money

![Deposit Screenshot](screenshots/deposit.png)

<img width="1919" height="982" alt="image" src="https://github.com/user-attachments/assets/fe7c3443-8293-4c9c-94aa-d05137f151df" />


## Withdraw Money

![Withdraw Screenshot](screenshots/withdraw.png)

<img width="1919" height="982" alt="image" src="https://github.com/user-attachments/assets/62303047-5193-4059-a5dd-cb9862a8c25f" />


## Transaction History

![Transactions Screenshot](screenshots/transactions.png)

<img width="1916" height="815" alt="image" src="https://github.com/user-attachments/assets/29601fe8-3868-4764-b52f-6ef6d603407a" />


## API Endpoints

### Authentication

| Method | Endpoint |
|----------|----------|
| POST | /api/auth/register |
| POST | /api/auth/login |

### Account

| Method | Endpoint |
|----------|----------|
| GET | /api/account/balance |
| POST | /api/account/deposit |
| POST | /api/account/withdraw |

### Transactions

| Method | Endpoint |
|----------|----------|
| GET | /api/transactions |

---


## Future Improvements

- JWT Token Authentication
- Admin Dashboard
- Fund Transfer Between Accounts
- Email Notifications
- Password Reset
- Profile Management
- Charts & Analytics

---

## Author

**Ayush Singh**
---

## License

This project is developed for educational and portfolio purposes.
