import {Question} from "./Question";

export class Task {
  _id: object;
  private _assignees: object[];
  private _deadline: Date;
  private _name: string;
  private _description: string;
  private _questions: Question[];
  private _submittedBy: string[];

  constructor(id: object, taskAssignees: object[], taskDeadline: Date, taskName: string, taskDescription: string, taskQuestions: Question[], submittedBy: string[]) {
    this._id = id;
    this._assignees = taskAssignees;
    this._deadline = taskDeadline;
    this._name = taskName;
    this._description = taskDescription;
    this._questions = taskQuestions;
    this._submittedBy = submittedBy;
  }

  get deadline(): Date {
    // @ts-ignore
    return this._deadline;
  }

  get name(): String {
    return this._name;
  }

  get description(): String {
    return this._description;
  }

  get questions(): Question[] {
    return this._questions;
  }

  get submittedBy(): string[] {
    return this._submittedBy;
  }

  get assignees(): object[] {
    return this._assignees;
  }

  static fromJson(json){
    let questions: Question[] = [];
    for(let q of json.questions) {
      questions.push(new Question(q.name, q.type, q.options))
    }
    return new Task(json._id, json.assignees, new Date(Date.parse(json.deadline)), json.name, json.description, questions, json.submittedBy)
  }

}
