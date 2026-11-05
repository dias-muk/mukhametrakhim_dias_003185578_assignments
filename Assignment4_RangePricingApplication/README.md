# Assignment 4 – Range Pricing Application

**INFO 5100 Application Engineering and Development**, Fall 2026
Dias Mukhametrakhim, NUID 003185578

A Java Swing application built on the course's Range Pricing skeleton. Range
pricing means every product carries three prices — a **floor** it should not be
sold below, a **ceiling** it should not exceed and a **target** the business aims
for — so a sale can be measured against what it was supposed to earn. The
application generates a full business, lets a sales person build orders inside
that range, and reports on the result.

## How to run

1. Open the `Assignment4_RangePricingApplication` folder in Apache NetBeans 16 (File → Open Project), with JDK 19.
2. Run the project. The main class is `TheBusiness.RangePricingApplication`.

| User name | Password | Opens |
|---|---|---|
| `sales` | `XXXX` | Sales Work Area |
| `marketing` | `XXXX` | Marketing Work Area |

The user name is matched ignoring case; the password is case sensitive.

## Part 1 – The data

`TheBusiness.DataGenerator` builds the business at start-up:

| | |
|---|---|
| Business | 1 |
| Suppliers | 50 |
| Suppliers holding a catalog | the first 30, with 50 products each (1500 products) |
| Customers | 300, each name a company plus an industry, so all 300 are unique |
| Orders | 1 to 3 per customer, about 605 in total |
| Items per order | 1 to 10, drawn from a single supplier's catalog |

Each item is sold at a price drawn between the product's floor and its ceiling,
so some sales land above target and some below. Without that spread every
range-pricing figure in the reports would be zero.

The generator uses a **fixed random seed**, so every run produces exactly the
same business. That makes the reports reproducible, which is what allows a
wrong number to be told apart from a different number.

## Part 2 – The reports

**Report #1 — Most expensive products.** Product name, supplier name and price,
highest first. The price shown is the **target** price: floor and ceiling are the
negotiating range, not what the business sells at. Built by
`ExpensiveProductsReport` from `ProductPriceSummary` rows.

**Report #2 — Most valuable customers.** Customer name, number of orders and
total sales, sorted from the highest total down. Built by `CustomersReport` from
`CustomerSummary` rows, which read `CustomerProfile.getTotalSales()`.

**Report #3 — Supplier report.** `SupplierReport` builds one `SupplierSummary`
per supplier:

| Column | How it is worked out |
|---|---|
| Supplier Name | — |
| Total Sales | every order item ever sold out of that supplier's catalog |
| Loyalty Score | different customers who bought from the supplier ÷ all 300 customers |
| Avg Spending / Customer | total sales ÷ different customers of that supplier |
| Top 5 Sales Score | sales to the supplier's five biggest customers ÷ total sales |

A high top-five score means the supplier leans on a handful of customers. The
twenty suppliers without a catalog correctly report zeros.

To attribute a sale to a customer the report walks supplier → product → order
item → order → customer. The skeleton had no link from an order item back to its
order, so `OrderItem` now keeps one. The check that this walk is complete is
that the per-supplier totals sum to exactly `Business.getSalesVolume()`.

## Part 3 – The report viewer

`UserInterface.Reports.ReportViewerJPanel` is reached from **Performance
Reports** in either work area. Choose a report in the drop-down, press **Run
Report**, and the rows appear in the table. Column headers sort; no cell can be
edited.

## Using the application

**Sales Work Area**
- **Serve Customers** — type a customer name, for example `Dell Systems`, to open
  Prepare Order. Pick a supplier, select a product, press **Add** and enter the
  agreed price (the target is offered, with the floor and ceiling shown) and a
  quantity. **Submit** books the order; **X Cancel** abandons it.
- **Review Order Status History** — every order with its status, total and customer.
- **Performance Reports** — the report viewer.

**Marketing Work Area** — Manage Suppliers and the report viewer.

Buttons marked *Not part of Assignment 4* are disabled. They belong to screens
the skeleton never implemented, and in the original code they cleared the work
area and left a blank window.

## How the code is organized

- `TheBusiness.Business` is the root of the model and owns every directory and
  catalog. `DataGenerator` fills it; `UserInterface.Main.ConfigureABusiness` is
  the single place the user interface asks for it.
- `Supplier` → `ProductCatalog` → `Product`, and `MasterOrderList` → `Order` →
  `OrderItem`. A `Product` keeps the order items that sold it, which is how the
  reports measure performance.
- Report classes sit beside the things they report on: `ExpensiveProductsReport`
  in `ProductManagement`, `CustomersReport` in `CustomerManagement`,
  `SupplierReport` in `Supplier`.
- `UserInterface.Main.PricingMainFrame` is the window: login on the left, a
  CardLayout stack of screens on the right. Logging in picks the work area from
  the profile behind the account.
- `UserInterface.TableStyle` and `UserInterface.ReadOnlyField` hold the shared
  look of the tables and of the fields that only display a calculated value, so
  the styling lives in one place instead of in twelve forms.

Built on the Range Pricing skeleton by Kal Bugrara.
