# Football Cup Knockout Stage Tracker

## Instructions

Please provide the implementation of the Football Cup Knockout Stage Tracker as a simple Java
library.

## Guidelines

- **Keep it simple.** Stick to the requirements and try to implement the simplest solution you can
  possibly think of that works, and don't forget about edge cases.
- **Use an in-memory store solution.** For example, just use collections to store the information
  you might require.
- **Do not implement a REST API, a Web Service, or a Microservice.** Just a simple implementation.
- **Focus on Quality.** Pay attention to OO design, Clean Code, and adherence to SOLID principles.
- **Add a README.md file** where you can make notes of any assumption or things you would like to
  mention about your solution.

## Football Cup Knockout Stage Tracker

You are working on a sports data company. We would like you to develop a new Cup Knockout Stage
Tracker that manages and tracks the progression of a Football Cup tournament from the Round of 16
through to the Final.

### The tracker supports the following operations:

### 1. Initialize the Cup

Start the knockout stages by setting up the initial bracket with exactly 16 teams.

- Teams are paired sequentially (1st with 2nd, 3rd with 4th, etc.)
- The winner of the first match in a round (e.g., teams 1 vs. 2) will play the winner of the second
  match (e.g., teams 3 vs. 4) in the subsequent round, and so on
- Initial bracket positions must be maintained throughout the tournament
- All teams must be unique

### 2. Record Match Result

Capture the outcome of a match with:
- The two teams involved (order independent)
- Final scores for both teams
- Penalty shootout scores (if applicable, when the match ends in a draw)

This operation must:
- Automatically advance winners to the next stage according to the bracket structure
- When both semi-final matches are complete, automatically generate a '3rd place match' between the two losing teams

### 3. Get Cup Summary

Return a Summary object that contains a collection of tournament data.

The Summary object must provide a method that returns a preformatted string according to these
rules:

1. **Top Line:** Display the tournament's most advanced stage (e.g., "Stage: Quarter-finals" if at
   least one quarter-final match is scheduled).
2. **Stage Grouping:** Group all matches by their stage. The stages must be ordered from most recent
   to oldest: `Final`, `3rd Place Match`, `Semi-finals`, `Quarter-finals`, `Round of 16`.
3. **Match Sorting:** Within each stage, sort the matches as follows:
    * First, by status: **Completed** matches must be listed before **Upcoming** matches.
    * Then, by their original **bracket position** (e.g., the match derived from the first two Round
      of 16 games comes first in the Quarter-finals).
4. **Match Formatting:**
    * For each match, display the teams sorted **alphabetically**.
    * Show final scores for completed matches (e.g., `England 2 vs Spain 1`).
    * Show `(upcoming)` for matches not yet played.
    * Include penalty shootout details where applicable (e.g., `Spain wins on penalties 3-2`).

## Example Summary Outputs

### After initialization with 16 teams:

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

### During Quarter-finals:

```
Stage: Quarter-finals
- England 2 vs Spain 1
- Portugal 2 vs USA 1
- Brazil vs Italy (upcoming)
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

### After tournament completion:

```
Stage: Final
- Brazil 2 vs Portugal 1

Stage: 3rd Place Match
- Spain 2 vs Uruguay 1

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

## Technical Requirements

- Handle invalid inputs appropriately (wrong number of teams, duplicate teams, non-existent matches)
- Prevent recording results for already completed matches
- Ensure penalty shootouts are only valid for drawn matches
- Maintain tournament bracket structure throughout progression
- Teams advance according to standard knockout rules
