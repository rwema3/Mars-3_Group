import json

class Sales:
     def __init__(self, sales: ) -> None:
          pass

class SalesReport:
    def __init__(self, salesfile: str) -> None:
        self.salesfile = salesfile

    def generate_sales_report(self):
            try:
                with open(self.salesfile, 'r') as fin:
                    sales_data = json.load(fin)

                for sale in sales_data:
                    print(f"Product: {sale['name']}")
                    print(f"Quantity: {sale['quantity']}")
                    print(f"Unit Purchase Price: {sale['unit_price']}")
                    print(f"Purchase Price: {sale['purchase_price']}")
                
                   
            except FileNotFoundError:
                print("Sales data file not found.")
