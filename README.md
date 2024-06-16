# The Cup Knockout Stage Tracker

## Overview

The Cup Knockout Stage Tracker is a simple library
designed to manage and oversee the progression of any Football Cup from the Round of 16 to the Final.
It is tailored to efficiently follow and document the sequence of matches,
ensuring all results and schedules are captured systematically.

## Key Features

### 1. Initialize the Cup

- Functionality: Start the knockout stages by setting up the initial bracket of 16 teams.
- Details: On initialization, the user inputs the participating teams which are then stored for the entirety of the
  tournament.

### 2. Record Match Results

- Functionality: Captures and updates the outcomes of matches.
- Details: Each match result updates the tournament records with:
    - The winning team
    - The losing team
    - The final scores of both teams

### 3. Get a Summary of the Cup Status

- Functionality: Provides a dynamic summary of the tournament's current stage.
- Details: Includes:
    - The current tournament stage
    - Results of completed matches
    - Schedule of upcoming matches

The matches in summary output should be sorted according to the bracket position.
I.e., relative position between the teams in the bracket should be maintained.
The teams itself in match should be sorted alphabetically.

#### Example Summary Outputs

##### 1. Example summary output of the initialized Cup:

```
Stage: Round of 16
- Brazil vs Germany (upcoming)
- France vs Italy (upcoming)
- Argentina vs Spain (upcoming)
- England vs Netherlands (upcoming)
- Croatia vs Portugal (upcoming)
- Mexico vs USA (upcoming)
- Belgium vs Japan (upcoming)
- South Korea vs Uruguay (upcoming)
```

##### Example summary output of the ongoing Cup:

```
Stage: Quarter-finals
- Brazil vs Italy (upcoming)
- England 2 vs Spain
- Portugal 2 vs USA 1
- Belgium vs Uruguay (upcoming)

Stage: Round of 16
- Brazil 2 vs Germany 0
- France 0 vs Italy 1
- Argentina 1 vs Spain 1 (Spain wins on penalties 3-2)
- England 3 vs Netherlands 2
- Croatia 1 vs Portugal 2
- Mexico 1 vs USA 1 (USA wins on penalties 4-3)
- Belgium 1 vs Japan 0
- South Korea 2 vs Uruguay 3
```

##### Example summary output of the finished Cup:

```
Stage: Final
- Brazil 2 vs Portugal 1
- Spain 2 vs Uruguay 1, 3rd place match

Stage: Semi-finals
- Brazil 3 vs Spain 2
- Portugal 1 vs Uruguay 0

Stage: Quarter-finals
- Brazil 1 vs Italy 0
- England 1 vs Spain 2
- Portugal 2 vs USA 1
- Belgium 1 vs Uruguay 2

Stage: Round of 16
- Brazil 2 vs Germany 0
- France 0 vs Italy 1
- Argentina 1 vs Spain 1 (Spain wins on penalties 3-2)
- England 3 vs Netherlands 2
- Croatia 1 vs Portugal 2
- Mexico 1 vs USA 1 (USA wins on penalties 4-3)
- Belgium 1 vs Japan 0
- South Korea 2 vs Uruguay 3
```
