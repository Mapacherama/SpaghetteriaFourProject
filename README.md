# spaghetteria-4

https://spaghetteria.samtoxie.nl/

## How to configure a development environment

Clone the repo.

### backend

Open the backend folder using IntelliJ IDEA and make sure openJDK 15 is installed.

Once opened make sure to sync all depedencies via gradle. The project is already connected to a development database.

Once done hit shift+f10 to start the server.

### frontend

Open the frontend folder using Webstorm and run 'npm i' in the terminal to install all dependecies.

After this you can run ng serve to start the live environment.


### documentation

Can be found in "documentation/Project EWA System Documentation(1).pdf" inside the repo.

## Teamleden

* Sam Toxopeus
* Jerôme Tesselaar


## Definition of Done

Hier specificeren we de voorwaarden van een kaart die naar done verplaatst mag worden.

|ID| Definitie |
|--|--|
| 0x00 | De gemaakte functionaliteit voldoet aan de opgestelde requirements die staan beschreven in de user story. |
| 0x01 | De code is gereviewed, er is minimaal door een ander teamlid naar de code gekeken en de programmeercode is van de benodigde feedback voorzien. |
| 0x02 | Er zijn voldoende unit tests waar de code aan voldoet (werkt de code?). |
| 0x03 | De bijbehorende systeem documentatie is bijgewerkt. |
| 0x04 | De code voldoet aan de coding standards. |
| 0x05 | Alle code is gepushed naar GitLab. |

<br>

### Coding Standards

| ID | Definitie |
|--|--|
| 0x00 | De code is in ES6 geschreven. |
| 0x01 | De back-end is in Node.js geschreven, en eventueel Python voor access point functies. |
| 0x02 | De code is geschreven in de code conventies opgesteld door de Hogeschool van Amsterdam.¹ |
| 0x03 | Elke functie Jsdoc (Javadoc) erboven. Met `@author` tag erbij. |
| 0x04 | Alle code moet gepusht worden naar GitLab. |
| 0x05 | Functienamen en variabelen zijn **camelCase**. |
| 0x06 | Classes zijn **PascalCase** |
| 0x07 | Constanten zijn **MACRO_CASE**. |
| 0x08 | Iedereen maakt gebruik van een universele stylesheet. |
| 0x09 | CSS staat los van HTML; in een eigen bestand. |
| 0x0A | JS staat los van HTML; in een eigen bestand. |
| 0x0B | Standaard op een feature branche werken, en pas naar de main brance pushen als er door één andere persoon naar is gekeken en feedback op is gegeven. Pas als de feedback is verwerkt en een andere persoon akoord is, kan die persoon die feedback heeft gegeven het naar de main branche pushen.

1: [Code Conventions](https://dlo.mijnhva.nl/d2l/le/content/41278/viewContent/193906/View)
