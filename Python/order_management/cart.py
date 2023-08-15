## A cart is which is a group of products with a quantity to purchase.

from .product import Product
from .stock import Stock

class Cart:
    def __init__(self, stock: Stock) -> None:
        self.products = {}
        self.stock = stock

    def empty(self):
        self.products = {}

    # we can add and also increase the size when there is more element
    def add(self, productCode: str, quantity: int, prescriptionID: str = None):
        assert quantity > 0, 'ordered quantity is less than 1.'

        # retrieve the previous value
        quantity += self.products.get(productCode, 0)
        product = self.stock.getProductByID(productCode)
        assert product.quantity >= quantity, 'quantity requested is higher than quantity available'
        # set the value in the dictionary
        self.products[product.code] = quantity

    def remove(self, code):
        try:
            self.products.pop(code)
        except KeyError:
            pass

    def clear(self):
        self.products.clear()

    def cost(self):
        return sum([product.price * quantity for code, quantity in self.products.items() if (product := self.stock.getProductByID(code))])

    