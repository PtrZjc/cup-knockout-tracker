# World Cup Knockout Stage Tracker

## Overview

The World Cup Knockout Stage Tracker is a simple library
designed to manage and oversee the progression of the Football World Cup from the Round of 16 to the Final.
It is tailored to efficiently follow and document the sequence of matches,
ensuring all results and schedules are captured systematically.

## Key Features

### 1. Initialize the World Cup

- Functionality: Starts the knockout stages by setting up the initial bracket of 16 teams.
- Details: On initialization, the user inputs the participating teams which are then stored for the entirety of the
  tournament.

### 2. Record Match Results

- Functionality: Captures and updates the outcomes of matches.
- Details: Each match result updates the tournament records with:
    - The winning team
    - The losing team
    - The final scores of both teams

### 3. Get a Summary of the World Cup Status

- Functionality: Provides a dynamic summary of the tournament's current stage.
- Details: Includes:
    - The current tournament stage
    - Results of completed matches
    - Schedule of upcoming matches

Summary output should be sorted accoring to the bracket position. I.e. relative position between the teams
in the bracket should be maintained.

#### Example Summary Outputs

##### 1. Example summary output of the initialized World Cup:

```
Stage: Round of 16
- Brazil vs Germany (upcoming)
- Italy vs France (upcoming)
- Spain vs Argentina (upcoming)
- England vs Netherlands (upcoming)
- Portugal vs Croatia (upcoming)
- USA vs Mexico (upcoming)
- Belgium vs Japan (upcoming)
- Uruguay vs South Korea (upcoming)
```

##### Example summary output of the ongoing World Cup:

```
Stage: Quarter-finals
- Brazil vs Italy (upcoming)
- Spain 2 vs England 
- Portugal 2 vs USA 1
- Uruguay vs Belgium (upcoming) 

Stage: Round of 16
- Brazil 2 vs Germany 0
- Italy 1 vs France 0
- Spain 1 vs Argentina 1 (Spain wins on penalties 3-2)
- England 3 vs Netherlands 2
- Portugal 2 vs Croatia 1
- USA 1 vs Mexico 1 (USA wins on penalties 4-3)
- Belgium 1 vs Japan 0
- Uruguay 3 vs South Korea 2
```

##### Example summary output of the finished World Cup:

```
Stage: Final
- Brazil 2 vs Portugal 1

Stage: Third place match
- Spain 2 vs Uruguay 1

Stage: Semi-finals
- Brazil 3 vs Spain 2
- Portugal 1 vs Uruguay 0

Stage: Quarter-finals
- Brazil 1 vs Italy 0
- Spain 2 vs England 1
- Portugal 2 vs USA 1
- Uruguay 2 vs Belgium 1

Stage: Round of 16
- Brazil 2 vs Germany 0
- Italy 1 vs France 0
- Spain 1 vs Argentina 1 (Spain wins on penalties 3-2)
- England 3 vs Netherlands 2
- Portugal 2 vs Croatia 1
- USA 1 vs Mexico 1 (USA wins on penalties 4-3)
- Belgium 1 vs Japan 0
- Uruguay 3 vs South Korea 2
```

## Operational Protocol

All matches within a given stage must be fully completed before progressing to the next phase.
This protocol ensures clarity and fairness throughout the competition.

## Example Usage

Current system data includes:

