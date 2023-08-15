import json
from datetime import datetime

from typing import List
from .sale import Sale

class BookRecords:
    def __init__(self, transactions: List[Sale]) -> None:
        self.transactions = transactions
    
    def __str__(self) -> str:
        output = ['|      # | Date               | Customer   | Medication | Quantity | Purchase Price | Prescription |']
        output += [f'| {idx:>6} |{datetime.fromtimestamp(transaction.timestamp):%Y-%m-%d %H:%M:%S} | {transaction.customerID[:10]:<10} | {transaction.name[:10]:<10} | {transaction.quantity:>8} | {transaction.purchase_price:>14} | {transaction.prescriptionID if transaction.prescriptionID else "-":>12} |' for idx, transaction in enumerate(self.transactions)]

        return '\n'.join(output)
    
    def reportOnPrescriptions(self) -> str:
        summary = {}
        for transaction in self.transactions:
            if transaction.prescriptionID is not None:
                summary[transaction.prescriptionID] = summary.get(transaction.prescriptionID, 0) + transaction.purchase_price
        output = ['|    # | Prescription ID | Total Price |']
        output += [f'| {idx:>4} | {prescription:<15} | {total:>11}' for idx, prescription in enumerate(sorted(summary, key=lambda x: summary.get(x), reverse=True)) if (total := summary[prescription])]
        return '\n'.join(output)        

    def purchasesByUser(self, customerID: str):
        return BookRecords([transaction for transaction in self.transactions if transaction.customerID == customerID]).__str__()

    def salesByAgent(self, salesperson: str):
        return BookRecords([transaction for transaction in self.transactions if transaction.salesperson == salesperson]).__str__()
    
    def topNSales(self, start: datetime = datetime.strptime('1970-01-01', '%Y-%m-%d'), end: datetime = datetime.now(), n = 10) -> str:
        ## by default, returns the top 10 sales in the books
        transactions = sorted(
            [transaction for transaction in self.transactions if transaction.timestamp >= start.timestamp() and transaction.timestamp <= end.timestamp()],
            key=lambda x: x.purchase_price, reverse=True)[:n]
        return BookRecords(transactions).__str__()


    def totalTransactions(self) -> float:
        return sum([transaction.purchase_price for transaction in self.transactions])
    
    @classmethod
    def load(cls, infile: str):
        with open(infile, 'r') as fin:
            data = json.load(fin)
        return cls([Sale(**el) for el in data])
