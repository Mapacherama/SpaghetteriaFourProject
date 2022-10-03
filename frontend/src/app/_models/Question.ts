export class Question {
  private _name: string;
  private _type: string;
  private _options: string[];

  constructor(name: string, type: string, options: string[]) {
    this._name = name;
    this._type = type;
    this._options = options;
  }

  get name(): string {
    return this._name;
  }

  get type(): string {
    return this._type;
  }

  get options(): string[] {
    return this._options;
  }
}
