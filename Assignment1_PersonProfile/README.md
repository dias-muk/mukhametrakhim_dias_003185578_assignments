# Assignment 1 – Person Profile

**INFO 5100 Application Engineering and Development**, Fall 2026
Dias Mukhametrakhim, NUID 003185578

A small Java Swing application that captures one person's profile: their personal
details, a home address, a local address and a bank account. It is the first
exercise in separating a model from the screens that edit it.

## How to run

1. Open the `Assignment1_PersonProfile` folder in Apache NetBeans 16 (File → Open Project), with JDK 19.
2. Run the project. The main class is `UI.MainJFrame`.

## What it does

The window has a button panel on the left and a card area on the right. Each
button opens one screen:

| Button | Screen |
|---|---|
| Create Person | Enter name, email, phone, date of birth and gender |
| View Person | Show what was entered |
| Create Home Address | Street, unit, city, state and postal code |
| View Home Address | Show the home address |
| Create Local Address | The same fields, stored separately |
| View Local Address | Show the local address |
| Create Bank Account | Bank name, account number, type, branch code and balance |
| View Bank Account | Show the bank account |

The two addresses are separate objects of the same class, which is the point of
the exercise: one `Person` holds two `Address` objects in different roles.

## How the code is organized

- `Module.Person` — the person, holding a home `Address`, a local `Address` and a
  `BankAccount`. All fields are private with getters and setters.
- `Module.Address` and `Module.BankAccount` — each keeps a reference back to its
  `Person`, so the relationship can be followed in both directions.
- `UI.MainJFrame` — the window and the navigation.
- `UI.Create*JPanel` and `UI.View*JPanel` — one pair of screens per part of the
  profile.
