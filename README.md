# INFO 5100 / 5101 — Assignments

Dias Mukhametrakhim · NUID 003185578 · Fall 2026

Each assignment lives on its own branch (`Assignment1`, `Assignment2`, …)
in a folder named after it, as a standalone Apache NetBeans (Ant) project.

## Assignment 2 - Coffee Shop POS

Java Swing point-of-sale app for a coffee shop manager (NetBeans 16, JDK 19).
Run `UI.MainJFrame`. Demo data is loaded at start-up by `ConfigureABusiness`:
7 products, 7 customers (three named John Smith for the search test) and 5 orders.

- **Manage Products** - add, update, delete (products used by orders are protected)
- **New Customer Order** - register a customer and place an order with one product;
  "Find Existing" places an order for a returning customer (one open order at a time)
- **Search Customers** - by ID (opens the profile) or by name (all matches);
  deleting a customer deletes their orders too
- **List Orders** - all orders; view/edit or delete the selected order

Design: `Business` owns `ProductCatalog`, `CustomerDirectory` and `OrderDirectory`;
directories create objects through factory methods. The UI uses a CardLayout
container with push/pop navigation. Order type, payment method and status are enums.
