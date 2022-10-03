export class ImportantContacts {
  private _companyName: String;
  private _contact: String;
  private _phone: String;
  private _email: String;

  constructor(companyName: String, contact: String, phone: String, email: String) {
    this._companyName = companyName;
    this._contact = contact;
    this._phone = phone;
    this._email = email;
  }


  get companyName(): String {
    return this._companyName;
  }

  get contact(): String {
    return this._contact;
  }

  get phone(): String {
    return this._phone;
  }

  get email(): String {
    return this._email;
  }

  public static createRandomContact(): ImportantContacts{
    return new ImportantContacts(
      'Bol.com',
      'J. Duinrade',
      '0224-552264',
      'J.Duinrade@hotmail.com'
    );
  }
}
