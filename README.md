OntologyProv

The aim of the project is to provide an explanation for the properties of an entity.
It is mainly designed for drones to show warning messages to the user such as a risk of physical damage of crashing into something but still it can be used for other purposes.

The project uses different tools such as:
  - Clipper: to convert a provided ontology to an equivalent Datalog
  - Provsql: to provide provenance(explanation) for the properties of some entity
  - Postgres: as a database to store data

Generally, the project works on a provided CSV file which contains data in addition to an ontology file. The csv file is then converted to tables in a postgres database, and the ontology is converted to Datalog rules using Clipper. Then the datalog rules are parsed and converted into sql commands that create new postgres tables. Each table(property provided by the ontology) contains the data that satisfies the corresponding datalog rule(s). Then a provenance(explanation) can be provided using the provsql tool on a specific table. The provenance shows all the different explanations for why an entity is in a specific table, i.e., why an entity has a specific property.

The project is a maven project. Intellij Idea is recommended because it was the editor used to create the project.