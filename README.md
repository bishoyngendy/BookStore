# BookStore
An online bookstore implemented with java with an admin panel to sell books
#### As a manager I can:
- List all books
![LIST_BOOKS](/screenshots/books_admin.jpg?raw=true)
- Add new books
- Modify existing books
- Place orders for books
![PLACE_ORDER](/screenshots/place_order.jpg?raw=true)
- Confirm orders
- Add Publishers and Authors
![ADD_PUBLISHER](/screenshots/add_publisher.jpg?raw=true)
- Promote registered customers to have managers credentials
- View the following reports on sales
  - The total sales for books in the previous month
  - The top 5 customers who purchase the most purchase amount in descending order for the last
three months
  - The top 10 selling books for the last three months
(All reports are generated in PDF format and can be downloaded from the web app.)
![REPORTS](/screenshots/report_sample.jpg?raw=true)

#### As a customer I can:
- Register and login to the website
- View all books
![HOME](/screenshots/home.jpg?raw=true)
- Filter books by their title, ISBN, Year, Categories, Publishers, Authors and prices. Publishers and Authors are provided by an auto complete feature.
- View book details
![BOOK_DETAILS](/screenshots/book_details.jpg?raw=true)
- Add new payment method and choose from all available payment methods.
![CART](/screenshots/cart.jpg?raw=true)
- Edit and view my profile data

## Technical Prespective
The project is implemented using JavaEE and use MySql as database storage. The application is tested with more than 10 million records to get instant results from autocomplete search or filtrations. The frontend is implemented using JSP. Select2 and DataTable plugin are used through the application, and notification system to tell you your cart status.

### ERD
![ERD](/screenshots/erd.jpg?raw=true)
