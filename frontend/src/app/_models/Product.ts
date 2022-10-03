export class Product {
  private productName: String;
  private price: String;
  private type: String;
  private description: String;
  private disabled: boolean;


  constructor(productName: String, price: String, type: String, description: String, disabled: boolean) {
    this.productName = productName;
    this.price = price;
    this.type = type;
    this.description = description;
    this.disabled = disabled;
  }


}
