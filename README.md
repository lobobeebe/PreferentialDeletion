NOTE: This program was compiled, tested, and run using the following:
* Java 1.6
* Windows 10
* Windows Command Prompt
* Microsoft Office 365 ProPlus - Excel 


# Recreating Results

## Create Data
* Navigate a command prompt/terminal to the location of the JAR file.
* Run the command: java -jar PreferentialDeletion.jar

## Nodes
* Open nodes.xlsx
* Paste the output in nodes0.6.txt into Sheet1!B2:C6 of nodes.xlsx
* Paste the output in nodes0.75.txt into Sheet1!B7:C11 of nodes.xlsx
* Paste the output in nodes0.9.txt into Sheet1!B12:C16 of nodes.xlsx
* The contained chart should update to match the data. Compare to Fig.2 of the paper.

## Edges
* Open edges.xlsx
* Paste the output in edges0.6.txt into Sheet1!B2:C6 of edges.xlsx
* Paste the output in edges0.75.txt into Sheet1!B7:C11 of edges.xlsx
* Paste the output in edges0.9.txt into Sheet1!B12:C16 of edges.xlsx
* The contained chart should update to match the data. Compare to Fig.3 of the paper.

## Degree Distribution
* Open k.xslx
* Paste the output in k0.8.txt into rows A and B (starting at row 2) of edges.xlsx
* The contained chart should update to match the data. Compare to Fig.5 of the paper.
