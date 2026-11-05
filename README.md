# INFO 5100 — Assignments

Dias Mukhametrakhim · NUID 003185578 · Fall 2026

Each assignment is a standalone Apache NetBeans (Ant) project in its own folder,
built with JDK 19 and NetBeans 16. Each one is pushed to its own branch
(`Assignment2`, `Assignment3`, `Assignment4`, …), and because every branch
continues from the one before it, the latest branch holds all of them.

| Assignment | Folder | Main class | README |
|---|---|---|---|
| 1 – Person Profile | `Assignment1_PersonProfile` | `UI.MainJFrame` | [README](Assignment1_PersonProfile/README.md) |
| 2 – Coffee Shop POS | `Assignment2_CoffeeShopPOS` | `UI.MainJFrame` | [README](Assignment2_CoffeeShopPOS/README.md) |
| 3 – Profiles and Work Areas | `Assignment3_ProfileWorkArea` | `ProfileWorkAreaMainFrame` | [README](Assignment3_ProfileWorkArea/README.md) |
| 4 – Range Pricing Application | `Assignment4_RangePricingApplication` | `TheBusiness.RangePricingApplication` | [README](Assignment4_RangePricingApplication/README.md) |

## Opening a project

File → Open Project, pick the assignment folder, then Run. Each project keeps the
libraries it needs under its own `lib/` folder, so a fresh clone builds without
any further setup.

## What each one covers

- **Assignment 1** separates a model from the screens that edit it: one `Person`
  with two `Address` objects in different roles and a `BankAccount`.
- **Assignment 2** adds directories that own their objects and create them
  through factory methods, enums for the fixed value sets, and CardLayout
  navigation.
- **Assignment 3** adds people with several roles, user accounts and a login that
  opens a different work area per role.
- **Assignment 4** generates a business large enough to report on — 50 suppliers,
  1500 products, 300 customers and around 605 orders — and reports on it.

Assignments 3 and 4 are built on skeleton projects by Kal Bugrara.
