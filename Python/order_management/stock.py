import json
from typing import List
from .product import Product

class Stock:
    def __init__(self, products: List[Product]) -> None:
        self.products = products

    def update(self, id: int, change: int):
        assert id in self.products, 'requested product does not exist'
        assert self.products[id].quantity + change >= 0, "Invalid change. Would set quantity to a negative value"
        self.products[id].quantity += change

    def getProductByID(self, id: int):
        try:
            return self.products[id]
        except KeyError:
            raise ValueError("The requested product does not exist")
        
    def dump(self, outfile: str):
        products = list([product.__dict__ for product in self.products.values()])
        with open(outfile, 'w') as fout:
            json.dump(products, fout)
    
    @classmethod
    def load(cls, infile: str):
        with open(infile, 'r') as fin:
            # data is expected to be an array of products, each with all the specified keys
            data = json.load(fin)
        products = {product["code"]: Product(**product) for product in data}
        return Stock(products=products)
    
    def __str__(self) -> str:
        ## idea for improvement: add a sort by field to be used
        output = '*' * 102 + '\n'
        output += f'| ID   | Name       | Brand      | Description               | Qty. | Price (RWF) | W/O Prescription |\n'
        output += '\n'.join([f'| {product.code[:4]:>4} | {product.name[:10]:<10} | {product.brand[:10]:<10} | {product.description[:25]:<25} | {product.quantity:>4} | {product.price:>11} | {"false" if product.requires_prescription else "true":>16} |' for product in self.products.values()])
        output += '\n' + '*' * 102
        return output