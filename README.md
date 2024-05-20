# CyBooks 📚 - Library Management Application

CyBooks is an application designed to manage a library, providing functionalities such as user registration, book management, stock tracking, and handling of book loans. The main objective is to create a user-friendly graphical application for librarians, accessible via mouse and keyboard inputs. Users should be able to browse through users, books, and their properties, filtering results based on various criteria.

## Features 🌟

- **User Management**: Register users, modify user information, search for specific users.
- **Book Management**: Search for books using multiple criteria, view details of books retrieved from the Bibliothèque Nationale de France (BNF) API.
- **Loan Management**: Track loans, set maximum loan duration per book, limit the number of books borrowed by a user at once, display alerts for current loan issues, and show a list of overdue loans.
- **Loan History**: Display loan history including dates, durations, and any overdue instances for specific users.
- **Search and Filtering**: Utilize various filtering criteria for book searches, with the ability to paginate through large result sets.
- **Local Storage**: Store loan information locally, either using a database or files.
- **Popular Books**: Display a list of the most borrowed books in the last 30 days.
- **Logical Constraints**: Ensure logical actions such as not allowing loans with past dates, restricting borrowing of books already borrowed, based on a predefined constant representing the number of available copies of each book.

## Development Guidelines 🛠️

- **Code Comments**: All elements (variables, functions, comments) are in English.
- **JavaDoc**: Automatic JavaDoc generation. With a folder containing the generated documentation.
- **User Interface**: The application is usable via both keyboard and mouse inputs.
- **Error Handling**: Errors and crashes are handled via exceptions.
- **Version Control**.

## Delivery 📦

This application is intended solely for use by librarians, enabling them to manage users (including registrations, information modifications, and user searches), manage books (multi-criteria searches), and handle loans.

## Technical Considerations 🖥️

- **Local Database**: The program will start the server if it's not already running, through a system call.

## Creators 👨‍💻

- [Abdellatif EL-MAHDAOUI](https://github.com/abdemeh)
- [Mohamed Lamine Koné](https://github.com/mohamedLamine949)
- [Morkos Azmy](https://github.com/Morkosazmy)
- [Clément Zhang](https://github.com/ZepZepPristine)

Feel free to adapt and expand upon these guidelines as needed for your project. Good luck with your development!
