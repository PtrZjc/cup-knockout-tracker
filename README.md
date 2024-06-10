# World Cup Knockout Stage Tracker

## Overview

The World Cup Knockout Stage Tracker is a simple library
designed to manage and oversee the progression of the Football World Cup from the Round of 16 to the Final.
It is tailored to efficiently follow and document the sequence of matches,
ensuring all results and schedules are captured systematically.

## Key Features

### 1. Initialize the World Cup

- **Functionality**: Starts the knockout stages by setting up the initial bracket of 16 teams.
- **Details**: On initialization, the user inputs the participating teams which are then stored for the entirety of the
  tournament.

### 2. Record Match Results

- **Functionality**: Captures and updates the outcomes of matches.
- **Details**: Each match result updates the tournament records with:
    - The winning team
    - The losing team
    - The final scores of both teams

### 3. Get a Summary of the World Cup Status

- **Functionality**: Provides a dynamic summary of the tournament's current stage.
- **Details**: Includes:
    - The current tournament stage
    - Results of completed matches
    - Schedule of upcoming matches

#### Example Summary Output:

```
**Stage: Round of 16**
- Brazil 2 - Germany 0
- Spain 1 - Argentina 1 (Spain wins on penalties 3-2)
- France 0 - Italy 1
- England 3 - Netherlands 2
- Portugal 2 - Croatia 1
- Belgium 1 - Japan 0
- Uruguay 3 - South Korea 2
- USA 1 - Mexico 1 (USA wins on penalties 4-3)

**Stage: Quarter-finals:**
- Brazil 1 - Italy 0
- Spain 2 - England 1
- Portugal - Belgium (upcoming)
- Uruguay - USA (upcoming)
```

## Operational Protocol

All matches within a given stage must be fully completed before progressing to the next phase.
This protocol ensures clarity and fairness throughout the competition.

## Example Usage

Current system data includes:

