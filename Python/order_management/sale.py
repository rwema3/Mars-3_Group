import datetime

class Sale:
    def __init__(self, id: str, name: str, quantity: int, 
                 price: float, purchase_price: float, timestamp: float, 
                 customerID: str, salesperson: str, prescriptionID: str) -> None:
        self.id = id
        self.name = name
        self.quantity = quantity
        self.price = price
        self.purchase_price = purchase_price
        self.timestamp = timestamp
        self.customerID = customerID
        self.salesperson = salesperson
        self.prescriptionID = prescriptionID

    def __str__(self) -> str:
        pass

