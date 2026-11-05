# Assignment 2 – Coffee Shop POS

**INFO 5100 Application Engineering and Development**, Fall 2026
Dias Mukhametrakhim, NUID 003185578

A Java Swing point-of-sale application for a coffee shop manager: manage the
product catalog, register customers, take orders and search back through them.

## How to run

1. Open the `Assignment2_CoffeeShopPOS` folder in Apache NetBeans 16 (File → Open Project), with JDK 19.
2. Run the project. The main class is `UI.MainJFrame`.

`Model.ConfigureABusiness` loads the demo data at start-up: 7 products,
7 customers and 5 orders. Three of the customers are deliberately called
John Smith, so that searching by name returns several matches.

## What it does

**Manage Products** — add, update and delete products. A product that appears on
an existing order cannot be deleted, so order history stays intact.

**New Customer Order** — register a customer and place an order with one product.
*Find Existing* places an order for a customer who is already registered. A
customer may only have one open order at a time.

**Search Customers** — by ID, which opens that customer's profile, or by name,
which lists every match. Deleting a customer deletes their orders as well.

**List Orders** — every order in the shop. Select one to view or edit it, or to
delete it.

## How the code is organized

- `Model.Business` owns a `ProductCatalog`, a `CustomerDirectory` and an
  `OrderDirectory`. Objects are created through factory methods on the
  directories rather than with `new` in the screens, so the model stays in
  charge of its own contents.
- `Model.OrderType`, `Model.PaymentMethod` and `Model.OrderStatus` are enums, so
  a screen cannot store a value the model does not recognise.
- `UI.MainJFrame` holds a CardLayout container and pushes and pops screens on it.
- `UI.Validator` holds the input checks shared by the screens, so the same rule
  is not written twice.
