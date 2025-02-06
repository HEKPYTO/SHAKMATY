# Shakmaty - Chess Engine

## Overview
Shakmaty is a Java-based chess implementation that provides a complete chess gaming experience with standard chess rules and special moves. The project implements the core chess logic including piece movements, special rules like castling and en passant, and game state management.

## Features
- Complete chess piece movement implementation
- Special moves support:
    - Castling (both kingside and queenside)
    - En passant captures
    - Pawn promotion
- FEN (Forsyth–Edwards Notation) support for board position setup
- PGN (Portable Game Notation) support for game moves
- Game state management (check, checkmate, stalemate)
- Command-line interface for gameplay

## Project Structure
```
src/
├── font/                  # Custom fonts for display
├── game/
│   ├── board/            # Board implementation
│   ├── control/          # Game control and event handling
│   ├── piece/            # Chess piece implementations
│   ├── position/         # Position and status handling
│   └── util/             # Utility classes and parsers
└── test/                 # Test cases
    ├── PieceTest/        # Piece movement tests
    └── PositionTest/     # Position handling tests
```

## Key Components

### Pieces
- King: One square movement in any direction, castling
- Queen: Unlimited movement in any direction (diagonal, horizontal, vertical)
- Rook: Unlimited horizontal and vertical movement
- Bishop: Unlimited diagonal movement
- Knight: L-shaped movement (2 squares in one direction, 1 square perpendicular)
- Pawn: Forward movement, diagonal captures, en passant, promotion

### Game States
- WELCOME: Initial game state
- INPUTFGN: FEN string input state
- INPUTPGN: PGN string input state
- INITPOS: Board initialization
- INMATE: Check for checkmate/stalemate
- TOPLAY: Active gameplay
- CHECKLEGAL: Move validation
- WIN/DRAW: Game end states
- CLEANUP: Game reset
- EXIT: Game termination

## How to Play

1. Run the game
2. Choose from available options:
    - SETUP BOARD: Input FEN string for custom board setup
    - SETUP POSITION: Input PGN moves for game progression
    - EXIT: Close the game
    - ANY: Start playing

### Special Commands
- [CLEAR]: Clear memorized PGN
- [OK]: Save PGN and proceed to game

## Implementation Details

### Position Handling
- Zero-based indexing for internal representation
- Conversion between chess notation (e.g., "e4") and internal coordinates
- Validation of move legality

### Movement Rules
- Implementation of standard chess movements
- Special move validation
- Check and checkmate detection
- Legal move generation for each piece type

### Game Flow
1. Initialize board
2. Alternate between players
3. Validate moves
4. Check for game end conditions
5. Update game state
6. Repeat until game end

## Testing
The project includes comprehensive test cases covering:
- Piece movements
- Position handling
- Special moves
- Game state transitions
- Edge cases

## Technical Requirements
- Java Development Kit (JDK)
- Custom font support (Nerd Fonts)

## Font Requirements
The game requires specific fonts for proper display:
```
- MesloLGSNerdFontMono-Bold.ttf
- MesloLGSNerdFontMono-BoldItalic.ttf
- MesloLGSNerdFontMono-Italic.ttf
- MesloLGSNerdFontMono-Regular.ttf
```

## Notes
- The game uses standard chess rules with traditional piece movements
- All special moves (castling, en passant, promotion) are implemented according to official chess rules
- The board is represented in standard 8x8 format
- Move validation ensures all moves follow chess rules and game state remains valid